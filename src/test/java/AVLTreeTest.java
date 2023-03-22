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
    public void InsertTtest() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.traverse(tree.getRoot());
        ArrayList<Integer> InorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertArrayEquals(expected.toArray(), InorderTraversal.toArray());

    }
}
