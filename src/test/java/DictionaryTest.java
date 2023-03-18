import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class DictionaryTest {
    private final String rootPath = "C:\\Users\\cyber\\Desktop\\Self-Balanced-BSTs-AVL-Red-Black\\src\\test";
    Random rand = new Random();

    @Test
    public void testSingleInsert() {

    }

    @Test
    public void testSingleDelete() {

    }

    @Test
    public void testBatchInsert() throws IOException {
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            try (Stream<String> lines = Files.lines(Paths.get(rootPath + "\\" + "dictionary.txt"))) {
                int toSkip = rand.nextInt(127142);
                String line = lines.skip(toSkip).findFirst().get();
                words.add(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeFile(words);
    }

    public void writeFile(ArrayList<String> words) {
        try {
            FileWriter writer = new FileWriter(rootPath + "\\" + "insert.txt", true);
            for (String word : words) {
                writer.write(word + "\n");

            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchDelete() {

    }


    @Test
    public void testSize() {

    }

    @Test
    public void testHeight() {

    }

    @Test
    public void testInsertWithDelete() {

    }
}
