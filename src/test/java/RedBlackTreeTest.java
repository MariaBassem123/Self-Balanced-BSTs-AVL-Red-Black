import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RedBlackTreeTest {
    @Test
    public void testInsertInRBTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
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
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(7);
        expected.add(10);
        expected.add(15);
        expected.add(16);
        expected.add(18);
        expected.add(25);
        expected.add(30);
        expected.add(40);
        expected.add(60);
        tree.printTree(tree.getRoot());
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
    }

    @Test
    public void testInsertDuplicate() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        Assert.assertTrue(tree.insert(10));
        Assert.assertTrue(tree.insert(18));
        Assert.assertFalse(tree.insert(10));
        Assert.assertEquals(2, tree.size());
    }

    @Test
    public void testSize() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(18);
        tree.insert(7);
        tree.insert(15);
        tree.insert(16);
        tree.printTree(tree.getRoot());
        Assert.assertEquals(5, tree.size());
    }

    @Test
    public void testHeight() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("Hello");
        tree.insert("I");
        tree.insert("am");
        tree.insert("a");
        tree.insert("CSED");
        tree.insert("student");
        tree.printTree(tree.getRoot());
        Assert.assertEquals(3, tree.height());
    }

    @Test
    public void testEmpty() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        Assert.assertEquals(0, tree.height());
        Assert.assertEquals(0, tree.size());
        Assert.assertFalse(tree.delete(30));
        Assert.assertFalse(tree.search(3));
    }

    @Test
    public void testSearch() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("Hello");
        tree.insert("I");
        tree.insert("am");
        tree.insert("a");
        tree.insert("CSED");
        tree.insert("student");

        boolean case1 = tree.search("hello");
        Assert.assertFalse(case1);

        boolean case2 = tree.search("Hello");
        Assert.assertTrue(case2);

        boolean case3 = tree.search("Hi");
        Assert.assertFalse(case3);
        tree.printTree(tree.getRoot());
    }

    @Test
    public void testDeleteRoot() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("Hello");
        tree.insert("I");
        tree.insert("am");
        tree.insert("a");
        tree.insert("CSED");
        tree.insert("student");
        tree.printTree(tree.getRoot());
        System.out.println("----------");
        tree.delete("I");
        ArrayList<String> inorderTraversal = tree.inorder();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("CSED");
        expected.add("Hello");
        expected.add("a");
        expected.add("am");
        expected.add("student");
        tree.printTree(tree.getRoot());
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(5, tree.size());
        Assert.assertEquals(3, tree.height());
    }

    @Test
    public void testDeleteWithOneRedChild() {
        // delete black with one red child
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(50);
        tree.insert(55);
        tree.insert(20);
        tree.insert(52);
        tree.insert(25);
        tree.insert(15);
        tree.insert(70);
        tree.insert(68);
        tree.insert(80);
        tree.insert(90);
        tree.printTree(tree.getRoot());
        System.out.println("-----------------");
        tree.delete(70);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(15);
        expected.add(20);
        expected.add(25);
        expected.add(50);
        expected.add(52);
        expected.add(55);
        expected.add(68);
        expected.add(80);
        expected.add(90);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.height());
        tree.printTree(tree.getRoot());
    }

    @Test
    public void testDeleteLeftLeft() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(50);
        tree.insert(55);
        tree.insert(20);
        tree.insert(52);
        tree.insert(25);
        tree.insert(15);
        tree.insert(70);
        tree.insert(68);
        tree.insert(80);
        tree.insert(90);
        tree.printTree(tree.getRoot());
        System.out.println("-----------------");
        tree.delete(50);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(15);
        expected.add(20);
        expected.add(25);
        expected.add(52);
        expected.add(55);
        expected.add(68);
        expected.add(70);
        expected.add(80);
        expected.add(90);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.height());
        tree.printTree(tree.getRoot());
    }

    @Test
    public void testDeleteRightRight() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(50);
        tree.insert(55);
        tree.insert(20);
        tree.insert(52);
        tree.insert(25);
        tree.insert(15);
        tree.insert(70);
        tree.insert(68);
        tree.insert(80);
        tree.insert(90);
        tree.printTree(tree.getRoot());
        System.out.println("-----------------");
        tree.delete(55);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(15);
        expected.add(20);
        expected.add(25);
        expected.add(50);
        expected.add(52);
        expected.add(68);
        expected.add(70);
        expected.add(80);
        expected.add(90);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        assertFalse(tree.search(55));
        assertFalse(tree.delete(55));
        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.height());
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
    }

    @Test
    public void testDeleteRightLeft() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(35);
        tree.insert(10);

        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        tree.delete(20);
        System.out.println("====================");
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(30);
        expected.add(35);
        expected.add(40);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(4, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(3, tree.height());
    }

    @Test
    public void testDeleteSiblingBlackWith2BlackCousinsAndParentBlack() {
//    testDeleteSiblingBlackWith2BlackCousinsAndParentBlack
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(1);
        tree.insert(3);
        tree.insert(7);
        tree.insert(30);
        tree.insert(25);
        tree.insert(20);
        tree.insert(28);
        tree.insert(40);
        tree.insert(26);
        tree.delete(26);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        System.out.println("====================");
        tree.delete(1);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);

        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(7);
        expected.add(10);
        expected.add(20);
        expected.add(25);
        expected.add(28);
        expected.add(30);
        expected.add(40);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(8, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(4, tree.height());

    }

    @Test
    public void testDelete3() {
//    testDeleteSiblingBlackWith2BlackCousinsAndParentBlack and RL
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(1);
        tree.insert(3);
        tree.insert(7);
        tree.insert(30);
        tree.insert(25);
        tree.insert(20);
        tree.insert(28);
        tree.insert(40);
        tree.insert(26);
        tree.delete(26);
        tree.insert(19);
        tree.insert(23);
        tree.insert(15);
        tree.delete(15);
        tree.delete(30);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        System.out.println("====================");
        tree.delete(1);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(7);
        expected.add(10);
        expected.add(19);
        expected.add(20);
        expected.add(23);
        expected.add(25);
        expected.add(28);
        expected.add(40);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(9, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(4, tree.height());

    }

    @Test
    public void testDeleteLeftRight() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(38);
        tree.insert(19);
        tree.insert(41);
        tree.insert(31);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        System.out.println("====================");
        tree.delete(41);

        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(19);
        expected.add(31);
        expected.add(38);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(3, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(2, tree.height());
    }


    @Test
    public void testDelete2BlackCousins() {
        // DB's Sibling Black With 2 Back Children
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);
        tree.insert(11);
        tree.insert(30);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        System.out.println("-----------------");
        tree.delete(30);
        tree.delete(15);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);

        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(10);
        expected.add(11);
        expected.add(20);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(4, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(3, tree.height());

    }

    @Test
    public void testDeleteSiblingRed() {
        // DB's Sibling is red
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(20);
        tree.insert(11);
        tree.insert(30);

        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);
        System.out.println("-----------------");
        tree.delete(30);
        System.out.println("-----------------");
        tree.delete(5);
        tree.printTree(tree.getRoot());
        System.out.println("root = " + tree.getRoot().key);

        ArrayList<Integer> inorderTraversal = tree.inorder();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(11);
        expected.add(15);
        expected.add(20);
        assertArrayEquals(expected.toArray(), inorderTraversal.toArray());
        Assert.assertEquals(4, tree.size());
//    System.out.println("height ="+ tree.height());
        Assert.assertEquals(3, tree.height());
    }

    @Test
    public void testDeleteDuplicate() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.insert(10));
        assertTrue(tree.insert(18));
        assertTrue(tree.delete(10));
        assertFalse(tree.delete(10));
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(1, tree.height());
    }
}
