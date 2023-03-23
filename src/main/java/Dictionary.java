import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Dictionary implements IDictionary {
    private SelfBalancedBST<String> tree;
    long start, end;

    public Dictionary(SelfBalancedBST<String> tree) {
        this.tree = tree;
    }

    public Dictionary() {
    }

    @Override
    public boolean insert(String key) {
        return tree.insert(key);
    }

    @Override
    public boolean delete(String key) {
        return tree.delete(key);
    }

    @Override
    public boolean search(String key) {
        return tree.search(key);
    }

    // O(m log(n+m))
    @Override
    public Point batchInsert(String fileName) {
        List<String> words = readFile(fileName);
        int inserted = 0;
        start = System.nanoTime();
        for (String word : words)
            if (this.tree.insert(word))
                inserted = inserted + 1;
        end = System.nanoTime();
        return new Point(inserted, words.size() - inserted);
    }

    @Override
    public Point batchDelete(String fileName) {
        List<String> words = readFile(fileName);
        int deleted = 0;
        start = System.nanoTime();
        for (String word : words)
            if (this.tree.delete(word))
                deleted = deleted + 1;
        end = System.nanoTime();
        return new Point(deleted, words.size() - deleted);
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
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

    public long getTime() {
        return end - start;
    }
}
