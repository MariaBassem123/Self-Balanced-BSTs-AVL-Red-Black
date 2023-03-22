import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;

public class AVLTreeTest {
    @Test
    public void InsertTest() {
        AVLTree tree = new AVLTree<>();
        tree.insert("hello");
        tree.insert("this");
        tree.insert("is");
        tree.insert("test");
        tree.traverse(tree.getRoot());
        ArrayList<String> InorderTraversal = tree.inorder();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("hello");
        expected.add("is");
        expected.add("test");
        expected.add("this");

        assertArrayEquals(expected.toArray(), InorderTraversal.toArray());

    }
    @Test
    public void DeleteCasesTest(){
        AVLTree tree = new AVLTree<>();
        tree.insert("musicological");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");
        tree.insert("hello");


    }
}
