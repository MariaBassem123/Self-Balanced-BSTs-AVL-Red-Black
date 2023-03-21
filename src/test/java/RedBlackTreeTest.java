import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;

public class RedBlackTreeTest extends TestCase {
    @Test
      public void testInsertInRBTree(){
        RedBlackTree tree = new RedBlackTree<Integer>();
        tree.insert(10);
        tree.insert(18);
        tree.insert(7);
        tree.insert(15);
        tree.insert(16);
        tree.insert(30);
        tree.insert(25);
        tree.insert(40);
        tree.insert(60);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(7);
        expected.add(10);
        expected.add(15);
        expected.add(16);
        expected.add(18);
        expected.add(25);
        expected.add(30);
        expected.add(40);
        expected.add(60);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
    }
}