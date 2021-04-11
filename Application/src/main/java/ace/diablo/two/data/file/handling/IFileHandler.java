package ace.diablo.two.data.file.handling;

import ace.diablo.two.data.file.handling.reader.IFileReader;
import ace.diablo.two.data.file.handling.writer.IFileWriter;

/**
 * Interface for file management
 *
 * @param <V> Format for the raw data
 */
public interface IFileHandler<V> extends IFileReader<V>, IFileWriter {
}
