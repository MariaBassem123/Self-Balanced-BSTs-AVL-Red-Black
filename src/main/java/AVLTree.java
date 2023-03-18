public class AVLTree implements SelfBalancedBST{
    private AVLNode root;
    private int size = 0;
    @Override
    public boolean insert(Object key) {
        root = BSTInsert(root, (int)key);
        size += 1;
        return false;
    }

    @Override
    public boolean delete(Object key) {
        return false;
    }

    @Override
    public boolean search(Object key) {
        return searchRecursive(root, (int)key);
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
        return 0;
    }

    private AVLNode Rotation(AVLNode node){
        int balance = checkBalance(node);
        if(balance > 1){
            //right rotate
            System.out.println("right rotate");
        }
        else if (balance < -1){
            //left rotate
            System.out.println("left rotate");
        }
        return node;
    }
    private int checkBalance(AVLNode node){
        int balance = 0;
        if (node != null){
            int leftHeight = (node.left != null) ? node.left.height : 0;
            int rightHeight = (node.right != null) ? node.right.height : 0;
            balance = leftHeight - rightHeight;
        }
        return balance;
    }
    private void updateHeight(AVLNode node){
        int leftHeight = node.left != null ? node.left.height : 0;
        int rightHeight = node.right != null ? node.right.height : 0;
        int height = Math.max(leftHeight, rightHeight) + 1;
        node.setHeight(height);
        System.out.println("key = " + node.key + " - its height = " + height);
    }
    private AVLNode BSTInsert(AVLNode node, int key){
        if(node == null){
            return new AVLNode(key);
        }
        if(key > node.key){//insertion to the right
            node.right = BSTInsert(node.right, key);
            updateHeight(node.right);
        }
        else if (key < node.key){//insertion to the left
            node.left = BSTInsert(node.left, key);
            updateHeight(node.left);
        }
        updateHeight(node);
        Rotation(node);
        return node;
    }
    void traverse(AVLNode root)//inorder traversal
    {
        if (root == null){
            return;
        }
        // Recurse on left subtree
        traverse(root.left);
        // Add node data to sum
        System.out.println(root.key +" ");
        // Recurse on right subtree
        traverse(root.right);
    }
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(3);
        tree.insert(2);
        tree.insert(4);
        tree.insert(8);
        tree.insert(7);
        tree.insert(9);
        tree.insert(15);
        tree.insert(14);
        tree.insert(16);
        tree.traverse(tree.root);
        if(tree.search(7)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
        System.out.println(tree.size);

    }
}
