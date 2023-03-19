import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> implements SelfBalancedBST<T> {
    private AVLNode<T> root;
    private int size = 0;

    public AVLTree() {
    }

    public AVLTree(List<T> list) {
        this.root = ALToBST(list, 0, list.size() - 1);
    }

    @Override
    public boolean insert(T key) {
        boolean check = search(key);
        if (check) {
            return false;
        } else {
            root = BSTInsert(root, key);//insertion using BST
            size += 1;
            return true;
        }
    }

    @Override
    public boolean delete(T key) {
        return false;
    }

    @Override
    public boolean search(T key) {
        return searchRecursive(root, key, false);
    }

    public boolean searchAndMark(T key) {
        return searchRecursive(root, key, true);
    }

    private boolean searchRecursive(AVLNode<T> node, T key, boolean mark) {
        if (node == null) {
            return false;
        }
        if (node.key.compareTo(key) == 0) {
            node.marked = mark;
            return true;
        }
        if (key.compareTo(node.key) > 0) {
            return searchRecursive(node.right, key, mark);
        } else {
            return searchRecursive(node.left, key, mark);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return root.height;
    }

    public ArrayList<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(AVLNode<T> node, ArrayList<T> list) {
        if (node == null)
            return;

        //recur on the left child
        inorder(node.left, list);
        // Adds data to the list if it is not marked as deleted
        if (!node.marked) list.add(node.key);
        //recur on the right child
        inorder(node.right, list);
    }

    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> rightNode = node.right;
        AVLNode<T> centerNode = rightNode.left;
        rightNode.left = node;
        node.right = centerNode;
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }

    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> leftNode = node.left;
        AVLNode<T> centerNode = leftNode.right;
        leftNode.right = node;
        node.left = centerNode;
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }

    private AVLNode<T> Rotation(AVLNode<T> node) {
        int balance = checkBalance(node);
        if (balance > 1) {//heavy left case
            if (checkBalance(node.left) >= 0) {// Rotate right LL
                node = rightRotate(node);
            } else {// Rotate left-right case LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
            }
        } else if (balance < -1) { //heavy right
            if (checkBalance(node.right) <= 0) {// Rotate left RR
                node = leftRotate(node);
            } else {// Rotate right-left RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
            }
        }
        return node;
    }

    private int checkBalance(AVLNode<T> node) {
        int balance = 0;
        if (node != null) {
            int leftHeight = (node.left != null) ? node.left.height : 0;
            int rightHeight = (node.right != null) ? node.right.height : 0;
            balance = leftHeight - rightHeight;
        }
        return balance;
    }

    private void updateHeight(AVLNode<T> node) {
        int leftHeight = node.left != null ? node.left.height : 0;
        int rightHeight = node.right != null ? node.right.height : 0;
        int height = Math.max(leftHeight, rightHeight) + 1;
        node.height = height;
        System.out.println("key = " + node.key + " - its height = " + height);
    }

    private AVLNode<T> BSTInsert(AVLNode<T> node, T key) {
        if (node == null) {
            return new AVLNode<>(key);
        }
        if (key.compareTo(node.key) > 0) {//insertion to the right
            node.right = BSTInsert(node.right, key);
            updateHeight(node.right);
        } else if (key.compareTo(node.key) < 0) {//insertion to the left
            node.left = BSTInsert(node.left, key);
            updateHeight(node.left);
        }
        updateHeight(node);
        node = Rotation(node);
        return node;
    }

    void traverse(AVLNode<T> root)//inorder traversal
    {
        if (root == null) {
            return;
        }
        // Recurse on left subtree
        traverse(root.left);
        // Add node data to sum
        System.out.println(root.key + " ");
        // Recurse on right subtree
        traverse(root.right);
    }

    private AVLNode<T> ALToBST(List<T> list, int start, int end) {
        if (start > end)
            return null;
        int mid = (end + start) / 2;
        AVLNode<T> node = new AVLNode<>(list.get(mid));// mid -> list.get(mid)
        node.left = ALToBST(list, start, mid - 1);
        node.right = ALToBST(list, mid + 1, end);
        return node;
    }

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(41);
        tree.insert(38);
        tree.insert(31);
        tree.insert(12);
        tree.insert(19);
        tree.insert(8);
        tree.traverse(tree.root);
//        if(tree.search(7)){
//            System.out.println("YES");
//        }else{
//            System.out.println("NO");
//        }
        System.out.println("the height of the tree " + tree.height());

    }
}
