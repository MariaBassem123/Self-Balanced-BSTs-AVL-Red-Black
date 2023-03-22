import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
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
        Point inserted2 = dict2.batchInsert(rootPath + "\\insert1.txt");
        Assertions.assertEquals(inserted2.getX(), 17);
        Assertions.assertEquals(inserted2.getY(), 3);
        Point deleted2 = dict2.batchDelete(rootPath + "\\delete1.txt");
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

        ArrayList<String> searchWords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = rand.nextInt(count - 1);
            searchWords.add(insertWords.get(index));
        }

        writeFile("insert" + fileName, insertWords);
        writeFile("delete" + fileName, deleteWords);
        //  writeFile("search" + fileName, searchWords);
    }

    private void writeFile(String fileName, ArrayList<String> words) {
        try {
            String path = rootPath + "\\" + fileName;
            clearFile(path);
            FileWriter writer = new FileWriter(path, true);
            for (String word : words) {
                writer.write(word + "\n");

            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFile(String fileName) {
        PrintWriter writer;
        {
            try {
                writer = new PrintWriter(fileName);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
        Dictionary avlDictionary;
        Dictionary rbDictionary;
        String[] filenames = {"1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt"};
        // d1 = operation(inert/delete)
        // d2 = size(10, 100, 1000, 10000, 100000)
        // d3 = type(AVL, RB)
        long[][][] avgTime = new long[2][5][2];
        int[][][] avgHeight = new int[2][5][2];
        int cnt = 10;
        for (int i = 1; i <= 4; i++) {
            int totInsertHeight1 = 0, totInsertHeight2 = 0, totDeleteHeight1 = 0, totDeleteHeight2 = 0;
            long totInsertTime1 = 0, totInsertTime2 = 0, totDeleteTime1 = 0, totDeleteTime2 = 0;
            for (int j = 0; j < 10; j++) {// do test for 10 times and get the average
                avlDictionary = new Dictionary(new AVLTree<>());
                rbDictionary = new Dictionary(new RedBlackTree<>());
                test.generateRandomWords(filenames[i], cnt);
                String path = test.rootPath + "\\insert" + filenames[i];
                long start = System.currentTimeMillis();
                avlDictionary.batchInsert(path);
                long end = System.currentTimeMillis();
                totInsertTime1 += (end - start);
                totInsertHeight1 += avlDictionary.height();
                //  System.out.println("Height AVL = " + avlDictionary.height());
                start = System.currentTimeMillis();
                rbDictionary.batchInsert(path);
                end = System.currentTimeMillis();
                totInsertTime2 += end - start;
                totInsertHeight2 += rbDictionary.height();
                //System.out.println("Height RB = " + rbDictionary.height());
                path = test.rootPath + "\\delete" + filenames[i];
                start = System.currentTimeMillis();
                avlDictionary.batchDelete(path);
                end = System.currentTimeMillis();
                totDeleteTime1 += (end - start);
                totDeleteHeight1 += avlDictionary.height();
                //   System.out.println("Height AVL = " + avlDictionary.height());
                start = System.currentTimeMillis();
                rbDictionary.batchDelete(path);
                end = System.currentTimeMillis();
                totDeleteTime2 += end - start;
                totDeleteHeight2 += rbDictionary.height();
                // System.out.println("Height AVL = " + rbDictionary.height());
            }
            avgTime[0][i - 1][0] = totInsertTime1 / 10;
            avgTime[0][i - 1][1] = totInsertTime2 / 10;
            avgHeight[0][i - 1][0] = totInsertHeight1 / 10;
            avgHeight[0][i - 1][1] = totInsertHeight2 / 10;
            avgTime[1][i - 1][0] = totDeleteTime1 / 10;
            avgTime[1][i - 1][1] = totDeleteTime2 / 10;
            avgHeight[1][i - 1][0] = totDeleteHeight1 / 10;
            avgHeight[1][i - 1][1] = totDeleteHeight2 / 10;
            cnt *= 10;
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 2; k++) {
                    System.out.println((i + 1) + " " + "size = " + 10 * Math.pow(10, j) + "Tree " + (k + 1));
                    System.out.println("Avg. time = " + avgTime[i][j][k] + ", Avg. height = " + avgHeight[i][j][k]);
                }
            }
        }
    }
}
