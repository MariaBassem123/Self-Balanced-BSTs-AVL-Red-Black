import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
        // test with AVL
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        boolean insert1 = dict1.insert("Hello");
        boolean insert2 = dict1.insert("Hello");
        Assertions.assertTrue(insert1);
        Assertions.assertFalse(insert2);
        // test with Red Black
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        boolean insert3 = dict2.insert("Hello");
        boolean insert4 = dict2.insert("Hello");
        Assertions.assertTrue(insert3);
        Assertions.assertFalse(insert4);
    }

    @Test
    public void testSingleDelete() {
        // test with AVL
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        testSingleDelete(dict1);
        // test with Red Black
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        testSingleDelete(dict2);
    }

    public void testSingleDelete(Dictionary dict) {
        dict.insert("Hello");
        dict.insert("World");
        dict.insert("This");
        dict.insert("is");
        dict.insert("me");

        boolean delete1 = dict.delete("me");
        boolean delete2 = dict.delete("me");
        boolean delete3 = dict.delete("this");// to show case sensitivity
        Assertions.assertTrue(delete1);
        Assertions.assertFalse(delete2);
        Assertions.assertFalse(delete3);
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
        //AVL dictionary
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        testSize(dict1);
        //RB dictionary
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        testSize(dict2);
    }

    private void testSize(Dictionary dict) {
        dict.insert("a");
        dict.insert("b");
        dict.insert("c");
        Assertions.assertEquals(dict.size(), 3);
        dict.delete("b");
        Assertions.assertEquals(dict.size(), 2);
    }

    @Test
    public void testHeight() {
        //AVL dictionary
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        testHeight(dict1);
        //RB dictionary
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        testHeight(dict2);
    }

    private void testHeight(Dictionary dict) {
        dict.insert("a");
        Assertions.assertEquals(dict.height(), 1);
        dict.insert("b");
        Assertions.assertEquals(dict.height(), 2);
        // multiple right insertions
        dict.insert("c");
        dict.insert("d");
        dict.insert("e");
        Assertions.assertEquals(dict.height(), 3);
    }

    @Test
    public void testCombinations() {

    }
}
