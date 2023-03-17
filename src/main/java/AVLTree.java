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

    private AVLNode BSTInsert(AVLNode root, int key){
        if(root == null){
            return new AVLNode(key);
        }
        if(key > root.key){//insertion to the right
            root.right = BSTInsert(root.right, key);
        }
        else if (key < root.key){//insertion to the left
            root.left = BSTInsert(root.left, key);
        }
        return root;
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
        tree.insert(4);
        tree.insert(5);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
        tree.insert(1);;
        tree.traverse(tree.root);
        if(tree.search(7)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
        System.out.println(tree.size);

    }
}
