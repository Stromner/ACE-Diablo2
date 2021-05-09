package ace.diablo.two.data.file.handling.reader;

import ace.diablo.two.database.entities.Variable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/*
    Variables are laid out in a fixed order with special case for lists (items)
    which can be of varied length but always shows up inside the fixed order.
 */
@Component
public class VariablesGlossary {
    private static List<Variable> glossaryList;
    private static int index = 0;

    public static Variable getNextVariable() {
        return glossaryList.get(index++);
    }

    public static boolean hasNextVariable() {
        return glossaryList.size() > index;
    }

    @PostConstruct
    private void init() {
        glossaryList = new ArrayList<>();
        fillGlossary();
    }

    private void fillGlossary() {
        // Header
        glossaryList.add(new Variable("fileIdentifier", false, 4));
        glossaryList.add(new Variable("fileVersion", false, 4));
        glossaryList.add(new Variable("fileSize", false, 4));
        glossaryList.add(new Variable("checksum", false, 4));
        glossaryList.add(new Variable("activeWeapon", false, 4));
        glossaryList.add(new Variable("characterName", false, 16));
        glossaryList.add(new Variable("characterStatus", false, 1));
        glossaryList.add(new Variable("actsDone", false, 1));
        glossaryList.add(new Variable("hardCoded1", false, 1)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("hardCoded2", false, 2)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("characterClass", false, 1));
        glossaryList.add(new Variable("hardCoded3", false, 1)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("hardCoded4", false, 1)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("characterLevel", false, 1));
        glossaryList.add(new Variable("hardCoded5", false, 5)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("time", false, 4));
        glossaryList.add(new Variable("hardCoded6", false, 4)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("mouseLeftHotkeys", false, 4));
        glossaryList.add(new Variable("mouseRightHotkeys", false, 4));
        glossaryList.add(new Variable("backupMouseLeftHotkeys", false, 4));
        glossaryList.add(new Variable("backupMouseRightHotkeys", false, 4));
        glossaryList.add(new Variable("menuAppearance", false, 32));
        glossaryList.add(new Variable("normalDifficulty", false, 1));
        glossaryList.add(new Variable("nightmareDifficulty", false, 1));
        glossaryList.add(new Variable("hellDifficulty", false, 1));
        glossaryList.add(new Variable("mapSeed", false, 4));
        glossaryList.add(new Variable("mercBlock1", false, 2)); // TODO Find what this block actually represents
        glossaryList.add(new Variable("mercIsAlive", false, 2));
        glossaryList.add(new Variable("mercSeed", false, 4));
        glossaryList.add(new Variable("mercNameId", false, 2));
        glossaryList.add(new Variable("mercClass", false, 2));
        glossaryList.add(new Variable("mercExp", false, 4));
        glossaryList.add(new Variable("hardCoded7", false, 144)); // TODO Find what this block actually represents
        //
    }
}
