package ace.diablo.two.data.file.handling.reader;

import ace.diablo.two.core.events.DatabaseInitiatedEvent;
import ace.diablo.two.core.events.FailedToLoadFileEvent;
import ace.diablo.two.database.IDataContentRepository;
import ace.diablo.two.database.entities.Variable;
import ace.diablo.two.database.entities.content.CustomSizeContent;
import ace.diablo.two.database.entities.content.DataContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Component
public class DatabaseFileReader implements IFileReader<ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseFileReader.class);
    private ByteBuffer byteBuffer;
    @Autowired
    private IDataContentRepository contentRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public ByteBuffer loadFile(String filePath) {
        log.info("Started parsing file...");
        contentRepository.deleteAll();
        readFile(filePath);
        loadFileIntoDatabase(getRawData());

        log.info("Database initiated");
        eventPublisher.publishEvent(new DatabaseInitiatedEvent(this));
        log.info("Finished parsing file");

        return getRawData();
    }

    @Override
    public ByteBuffer getRawData() {
        return byteBuffer;
    }

    private void readFile(String filePath) {
        File f;
        FileInputStream fis;
        try {
            f = new File(filePath);
            fis = new FileInputStream(f);

            prepareByteBuffer((int) f.length());
            readData(fis);
            fis.close();
        } catch (IOException e) {
            log.error("Error while reading file {}", e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }

    private void loadFileIntoDatabase(ByteBuffer rawData) {
        while (rawData.remaining() > 0 && VariablesGlossary.hasNextVariable()) {
            Variable variable = VariablesGlossary.getNextVariable();
            if (!variable.isDynamic()) {
                contentRepository.saveAndFlush(parseHardcodedVariable(rawData, variable));
            } else {
                contentRepository.saveAndFlush(parseDynamicVariable(rawData, variable));
            }
        }
        if (rawData.remaining() != 0 && !VariablesGlossary.hasNextVariable()) {
            log.error("File is incomplete! Data left to process '{}', glossary  '{}'"
                    , rawData.remaining(), !VariablesGlossary.hasNextVariable());
            eventPublisher.publishEvent(new FailedToLoadFileEvent(this));
            throw new RuntimeException("File is incomplete!");
        }
    }

    private DataContent<?> parseHardcodedVariable(ByteBuffer data, Variable variable) {
        return parseDataContent(data, variable);
    }

    private DataContent<?> parseDynamicVariable(ByteBuffer data, Variable variable) {
        return null; // TODO
    }

    private void prepareByteBuffer(int size) {
        byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private <E extends InputStream> void readData(E dataStream) {
        try {
            byteBuffer
                    .put(dataStream.readAllBytes())
                    .rewind();
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private DataContent<?> parseDataContent(ByteBuffer data, Variable variable) {
        byte[] bytes = new byte[variable.getByteSize()];
        data.get(bytes, 0, variable.getByteSize());
        return new CustomSizeContent(variable, bytes);
    }
}
