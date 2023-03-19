import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    // O(mlogm)
    public Point batchInsert(String fileName) {
        List<String> words = readFile(fileName);
        int inserted = 0;
        if (this.tree instanceof RedBlackTree) {
            for (String word : words)
                if (this.tree.insert(word))
                    inserted = inserted + 1;
        } else {
            inserted = batchInsertAVL(words);
        }
        List<String> newTree = this.tree.inorder();
        for (String t : newTree)
            System.out.println(t + " ");
        return new Point(inserted, words.size() - inserted);
    }

    private int batchInsertAVL(List<String> words) {
        Collections.sort(words);
        List<String> merged = new ArrayList<>();
        List<String> oldTree = this.tree.inorder();

        int repeated = merge(oldTree, words, merged);
        for (String t : merged)
            System.out.println(t + " ");
        System.out.println("STOP");
        this.tree = new AVLTree<>(merged);
        return words.size() - repeated;
    }

    public Point batchDelete(String fileName) {
        List<String> words = readFile(fileName);
        int deleted = 0;
        if (this.tree instanceof RedBlackTree) {
            for (String word : words)
                if (this.tree.delete(word))
                    deleted = deleted + 1;
        } else {
            for (String word : words)
                if (((AVLTree<String>) this.tree).searchAndMark(word))
                    deleted = deleted + 1;
            List<String> newTree = this.tree.inorder();
            this.tree = new AVLTree<>(newTree);
        }
        List<String> newTree = this.tree.inorder();
        for (String t : newTree)
            System.out.println(t + " ");
        return new Point(deleted, words.size() - deleted);
    }

    private int merge(List<String> list1, List<String> list2, List<String> list3) {
        int repeated = 0;
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            String s1 = list1.get(i);
            String s2 = list2.get(j);
            int sz = list3.size();
            if (sz > 0 && s1.equals(list3.get(sz - 1))) {// ignore repeated words in list1
                i++;
                repeated++;
                continue;
            }
            if (sz > 0 && s2.equals(list3.get(sz - 1))) {
                j++;
                repeated++;
                continue;
            }
            if (s1.compareTo(s2) < 0) {
                list3.add(s1);
                System.out.println("S1" + s1);
                i++;
            } else if (s1.compareTo(s2) > 0) {
                list3.add(s2);
                System.out.println("S2" + s2);
                j++;
            } else { // if equal
                list3.add(s1);
                i++;
                j++;
                System.out.println("rep" + s1);
                repeated++;
            }
        }
        while (i < list1.size())
            list3.add(list1.get(i++));
        while (j < list2.size())
            list3.add(list2.get(j++));

        return repeated;
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
