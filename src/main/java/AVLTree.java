import java.util.ArrayList;
import java.util.List;

public class AVLTree implements SelfBalancedBST {
    private AVLNode root;
    private int size = 0;

    public AVLTree() {
    }

    public AVLTree(List<Object> list) {
        this.root = ALToBST(list, 0, list.size());
    }

    @Override
    public boolean insert(Object key) {
        boolean check = search(key);
        if(check){
            return false;
        }
        else {
            root = BSTInsert(root, (int) key);//insertion using BST
            size += 1;
            return true;
        }
    }

    @Override
    public boolean delete(Object key) {
        boolean check = search(key);
        if(!check){//the element is not found
            System.out.println("element not found");
            return false;
        }
        else {
            root = deleteBST(root, key);
            System.out.println("element found");
            return true;
        }
    }
    private AVLNode deleteBST(AVLNode root, Object key){
        if(root == null){
            return root;
        }
        if(root.key < (int)key){
            root.right = deleteBST(root.right ,key);
        }
        else if(root.key > (int)key){
            root.left = deleteBST(root.left ,key);
        }
        else{
            if( root.left == null && root.right == null){
                return null;
            }
            else if(root.left == null){
                root = root.right;
                return  root;
            }
            else if(root.right == null){
                root = root.left;
                return  root;
            }

        }
        updateHeight(root);
        return root;
    }
    @Override
    public boolean search(Object key) {
        return searchRecursive(root, (int) key);
    }

    private boolean searchRecursive(AVLNode node, int key) {
        if (node == null) {
            return false;
        }
        if (node.key == key) {
            return true;
        }
        if (key > node.key) {
            return searchRecursive(node.right, key);
        } else {
            return searchRecursive(node.left, key);
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

    public ArrayList<Object> inorder() {
        return null;
    }

    private AVLNode leftRotate(AVLNode node){
        AVLNode rightNode = node.right;
        AVLNode centerNode = rightNode.left;
        rightNode.left = node;
        node.right = centerNode;
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }
    private AVLNode rightRotate(AVLNode node){
        AVLNode leftNode = node.left;
        AVLNode centerNode = leftNode.right;
        leftNode.right = node;
        node.left=centerNode;
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }
    private AVLNode Rotation(AVLNode node){
        int balance = checkBalance(node);
        if(balance > 1){//heavy left case
            if (checkBalance(node.left) >= 0) {// Rotate right LL
                node = rightRotate(node);
            } else {// Rotate left-right case LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
            }
        }
        else if (balance < -1){ //heavy right
            if (checkBalance(node.right) <= 0) {// Rotate left RR
                node = leftRotate(node);
            } else {// Rotate right-left RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
            }
        }
        return node;
    }

    private int checkBalance(AVLNode node) {
        int balance = 0;
        if (node != null) {
            int leftHeight = (node.left != null) ? node.left.height : 0;
            int rightHeight = (node.right != null) ? node.right.height : 0;
            balance = leftHeight - rightHeight;
        }
        return balance;
    }

    private void updateHeight(AVLNode node) {
        int leftHeight = node.left != null ? node.left.height : 0;
        int rightHeight = node.right != null ? node.right.height : 0;
        int height = Math.max(leftHeight, rightHeight) + 1;
        node.height = height;
        System.out.println("key = " + node.key + " - its height = " + height);
    }

    private AVLNode BSTInsert(AVLNode node, int key) {
        if (node == null) {
            return new AVLNode(key);
        }
        if (key > node.key) {//insertion to the right
            node.right = BSTInsert(node.right, key);
        } else if (key < node.key) {//insertion to the left
            node.left = BSTInsert(node.left, key);
        }
        updateHeight(node);
        node = Rotation(node);
        return node;
    }

    void traverse(AVLNode root)//inorder traversal
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

    private AVLNode ALToBST(List<Object> list, int start, int end) {
        if (start > end)
            return null;
        int mid = (end - start) / 2;
        AVLNode node = new AVLNode(mid);// mid -> list.get(mid)
        node.left = ALToBST(list, start, mid - 1);
        node.right = ALToBST(list, mid + 1, end);
        return node;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
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
        tree.delete(12);
        tree.delete(41);
        tree.delete(31);
        tree.traverse(tree.root);
         System.out.println("the height of the tree "+tree.height());

    }
}
