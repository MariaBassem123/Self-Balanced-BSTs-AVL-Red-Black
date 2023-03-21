import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.io.FileWriter;
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
        Assertions.assertTrue(dict1.insert("Hello"));
        Assertions.assertFalse(dict1.insert("Hello"));
        // test with Red Black
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        Assertions.assertTrue(dict2.insert("Hello"));
        Assertions.assertFalse(dict2.insert("Hello"));
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

    private void testSingleDelete(Dictionary dict) {
        dict.insert("Hello");
        dict.insert("World");
        dict.insert("This");
        dict.insert("is");
        dict.insert("me");
        Assertions.assertTrue(dict.delete("me"));
        Assertions.assertFalse(dict.delete("me"));
        Assertions.assertFalse(dict.delete("this"));// to show case sensitivity
    }

    @Test
    public void testBatchInsertAndDeleteAVL() {
        //test with AVL
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        Point inserted1 = dict1.batchInsert(rootPath + "\\insert1");
        Assertions.assertEquals(inserted1.getX(), 17);
        Assertions.assertEquals(inserted1.getY(), 3);
        Point deleted1 = dict1.batchDelete(rootPath + "\\delete1");
        Assertions.assertEquals(deleted1.getX(), 10);
        Assertions.assertEquals(deleted1.getY(), 10);
    }

    @Test
    public void testBatchInsertAndDeleteRB() {
        // test with Red Black
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        Point inserted2 = dict2.batchInsert(rootPath + "\\insert1");
        Assertions.assertEquals(inserted2.getX(), 17);
        Assertions.assertEquals(inserted2.getY(), 3);
        Point deleted2 = dict2.batchDelete(rootPath + "\\delete1");
        Assertions.assertEquals(deleted2.getX(), 10);
        Assertions.assertEquals(deleted2.getY(), 10);
    }

    private void generateRandomWords(String fileName, int count) {
        ArrayList<String> insertWords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            try (Stream<String> lines = Files.lines(Paths.get(rootPath + "\\" + "dictionary.txt"))) {
                int toSkip = rand.nextInt(127142);
                String line = lines.skip(toSkip).findFirst().get();
                insertWords.add(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> deleteWords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = rand.nextInt(count - 1);
            deleteWords.add(insertWords.get(index));
        }
        writeFile("insert" + fileName, insertWords);
        writeFile("delete" + fileName, deleteWords);
    }

    public void writeFile(String fileName, ArrayList<String> words) {
        try {
            FileWriter writer = new FileWriter(rootPath + "\\" + fileName, true);
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
    public void testSearch() {
        // test with AVL
        Dictionary dict1 = new Dictionary(new AVLTree<>());
        testSearch(dict1);
        // test with Red Black
        Dictionary dict2 = new Dictionary(new RedBlackTree<>());
        testSearch(dict2);
    }

    private void testSearch(Dictionary dict) {
        dict.insert("mariam");
        dict.insert("nancy");
        dict.insert("eman");
        dict.insert("maria");
        dict.insert("sara");
        Assertions.assertTrue(dict.search("nancy"));
        Assertions.assertFalse(dict.search("nisreen"));
        dict.delete("nancy");
        Assertions.assertFalse(dict.search("nancy"));
        dict.insert("nisreen");
        Assertions.assertTrue(dict.search("nisreen"));
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

    public static void main(String[] args) {
        DictionaryTest test = new DictionaryTest();
        test.generateRandomWords("1.txt", 20);
        test.generateRandomWords("2.txt", 100);
    }
}
