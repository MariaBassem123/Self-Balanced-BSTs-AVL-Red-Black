import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private SelfBalancedBST tree;

    public Dictionary(SelfBalancedBST tree) {
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

    // O(mlogm)
    public Point batchInsert(String fileName) {
        List<String> words = readFile(fileName);
        int inserted = 0;
        for (String word : words)
            if (this.tree.insert(word))
                inserted = inserted + 1;
        /*ArrayList<Object> list1 = newTree.inorder();
        ArrayList<Object> list2 = this.tree.inorder();
        this.tree = merge(list1, list2);*/

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

    private SelfBalancedBST merge(ArrayList<Object> list1, ArrayList<Object> list2) {
        ArrayList<Object> list3 = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            String s1 = list1.get(i).toString();
            String s2 = list2.get(i).toString();
            if (s1.compareTo(s2) <= 0) {
                list3.add(s1);
                i++;
            } else {
                list3.add(s2);
                j++;
            }
            while (i < list1.size())
                list3.add(list1.get(i++).toString());
            while (j < list2.size())
                list3.add(list2.get(j++).toString());
        }
        return new RedBlackTree();
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
        Dictionary d = new Dictionary();
        d.batchDelete("C:\\Users\\cyber\\Desktop\\Self-Balanced-BSTs-AVL-Red-Black\\src\\main\\java\\delete.txt");
    }
}
