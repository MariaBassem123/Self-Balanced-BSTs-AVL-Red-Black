import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private SelfBalancedBST<String> tree;

    public Dictionary(SelfBalancedBST<String> tree) {
        this.tree = tree;
    }

    public Dictionary() {
    }

    public boolean insert(String key) {
        return tree.insert(key);
    }

    public boolean delete(String key) {
        return tree.delete(key);
    }

    public boolean search(String key) {
        return tree.search(key);
    }

    // O(m log(n+m))
    public Point batchInsert(String fileName) {
        List<String> words = readFile(fileName);
        int inserted = 0;
        for (String word : words)
            if (this.tree.insert(word))
                inserted = inserted + 1;
        return new Point(inserted, words.size() - inserted);
    }

    public Point batchDelete(String fileName) {
        List<String> words = readFile(fileName);
        int deleted = 0;
        for (String word : words)
            if (this.tree.delete(word))
                deleted = deleted + 1;
        return new Point(deleted, words.size() - deleted);
    }

    public int size() {
        return tree.size();
    }

    public int height() {
        return tree.height();
    }

    private List<String> readFile(String fileName) {
        List<String> words = Collections.emptyList();
        try {
            words =
                    Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void main(String[] args) {
        Dictionary d = new Dictionary(new AVLTree<>());
        d.insert("this");
        d.insert("me");
        d.insert("hi");
        Point p = d.batchDelete("C:\\Users\\cyber\\Desktop\\Self-Balanced-BSTs-AVL-Red-Black\\src\\test\\delete.txt");
        //  ArrayList<Object> order = d.inorder();
        System.out.println(p.getX() + " " + p.getY());
    }
}
