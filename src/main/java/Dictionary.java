import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        while(true){
            Dictionary dic = null;
            Scanner sc= new Scanner(System.in);
            System.out.println("Choose tree type\n"+"1) AVL\n" +"2) RB\n"+"3) Exit");
            int input= sc.nextInt();
            int operations = 0;
            if(input == 1){
                dic = new Dictionary(new AVLTree<>());
                System.out.println("Choose operation \n"+"1) Insert\n" +"2) Delete\n"+"3) Search\n"+"4) Batch insert\n"+"5) Batch delete\n"+"6) Size\n"+"7) Height\n"+"8) Return to tree selection");
                operations = sc.nextInt();
            }else if(input == 2) {
               dic = new Dictionary(new RedBlackTree<>());
                System.out.println("Choose operation \n"+"1) Insert\n" +"2) Delete\n"+"3) Search\n"+"4) Batch insert\n"+"5) Batch delete\n"+"6) Size\n"+"7) Height\n"+"8) Return to tree selection");
                operations = sc.nextInt();
            }else if(input == 3){
                break;
            } else{
                System.out.println("Invalid input");
            }
            sc.nextLine();
            while (true){
                if(operations == 1){
                String str = sc.nextLine();
                if(dic.insert(str)){
                    System.out.println("Added successfully");
                } else {
                    System.out.println("Already exists");
                }
            } else if(operations == 2) {
                String str = sc.nextLine();
                if(dic.delete(str)){
                    System.out.println("Deleted successfully");
                } else {
                    System.out.println("Do not exist");
                }
            } else if(operations == 3) {
                String str = sc.nextLine();
                if (dic.search(str)) {
                    System.out.println("Word found");
                } else {
                    System.out.println("Do not exist");
                }
             }else if(operations == 4) {
              //batch insert
            } else if(operations == 5) {
                //batch delete
            } else if(operations == 6) {
                System.out.println("Size of the tree : "+ dic.size());
            } else if(operations == 7) {
                System.out.println("Size of the tree : "+ dic.height());
            } else if(operations == 8) {
                break;
            } else{
                    System.out.println("Invalid input");
                }
            System.out.println("Choose operation \n"+"1) Insert\n" +"2) Delete\n"+"3) Search\n"+"4) Batch insert\n"+"5) Batch delete\n"+"6) Size\n"+"7) Height\n"+"8) Return to tree selection");
             operations = sc.nextInt();
             sc.nextLine();
            }
        }
    }
}
