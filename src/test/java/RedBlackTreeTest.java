import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RedBlackTreeTest {
    @Test
      public void testInsertInRBTree(){
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
    public void testInsertDuplicate(){
      RedBlackTree tree = new RedBlackTree<Integer>();
      Assert.assertEquals(true, tree.insert(10));
      Assert.assertEquals(true, tree.insert(18));
      Assert.assertEquals(false, tree.insert(10));
      Assert.assertEquals(2, tree.size());
    }

    @Test
    public void testSize(){
      RedBlackTree tree = new RedBlackTree<Integer>();
      tree.insert(10);
      tree.insert(18);
      tree.insert(7);
      tree.insert(15);
      tree.insert(16);
//      ArrayList<Integer> inorderTraversal = tree.inorder();
//      ArrayList<Integer> expected = new ArrayList<Integer>();
//      expected.add(7);
//      expected.add(10);
//      expected.add(15);
//      expected.add(16);
//      expected.add(18);
      tree.printTree(tree.getRoot());
      Assert.assertEquals(5, tree.size());
    }
  @Test
  public void testHeight(){
    RedBlackTree tree = new RedBlackTree<String>();
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
  public void testEmpty(){
    RedBlackTree tree = new RedBlackTree<String>();
    Assert.assertEquals(0, tree.height());
    Assert.assertEquals(0, tree.size());
  }

  @Test
  public void testSearch(){
    RedBlackTree tree = new RedBlackTree<>();
    tree.insert("Hello");
    tree.insert("I");
    tree.insert("am");
    tree.insert("a");
    tree.insert("CSED");
    tree.insert("student");

    boolean case1 = tree.search("hello");
    Assert.assertEquals(false, case1);

    boolean case2 = tree.search("Hello");
    Assert.assertEquals(true, case2);

    boolean case3 = tree.search("Hi");
    Assert.assertEquals(false, case3);
    tree.printTree(tree.getRoot());
  }

  @Test
  public void testDeleteRoot(){
    RedBlackTree tree = new RedBlackTree<>();
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
    ArrayList<String> expected = new ArrayList<String>();
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
  public void testDeleteWithOneRedChild(){
      // delete black with one red child
    RedBlackTree tree = new RedBlackTree<Integer>();
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
    ArrayList<Integer> expected = new ArrayList<Integer>();
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
  public void testDeleteBlackWithBlackChild(){
    RedBlackTree tree = new RedBlackTree<Integer>();
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
    ArrayList<Integer> expected = new ArrayList<Integer>();
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
  public void testDeleteRightRight(){
    RedBlackTree tree = new RedBlackTree<Integer>();
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
    ArrayList<Integer> expected = new ArrayList<Integer>();
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
    assertEquals(false, tree.search(55));
    assertEquals(false, tree.delete(55));
    Assert.assertEquals(9, tree.size());
    Assert.assertEquals(4, tree.height());
    tree.printTree(tree.getRoot());
    System.out.println("root = "+tree.getRoot().key);
  }

}