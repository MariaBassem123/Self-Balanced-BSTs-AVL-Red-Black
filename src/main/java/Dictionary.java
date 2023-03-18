import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public int batchInsert(String fileName) {
        List<String> words = readFile(fileName);
        long start = System.currentTimeMillis();
        
        return 0;
    }

    public int batchDelete(String fileName) {
        List<String> words = readFile(fileName);
        for (String word : words) System.out.print(word + " ");
        return 0;
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


