public class AVLTree implements SelfBalancedBST{
    private AVLNode root;

    @Override
    public boolean insert(Object key) {
        root = BSTInsert(root, (int)key);
        return false;
    }

    @Override
    public boolean delete(Object key) {
        return false;
    }

    @Override
    public boolean search(Object key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
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
//        tree.root = tree.BSTInsert(tree.root,5);
//        tree.BSTInsert(tree.root,4);
//        tree.BSTInsert(tree.root,7);
//        tree.BSTInsert(tree.root,6);
//        tree.BSTInsert(tree.root,8);
//        tree.traverse(tree.root);
        tree.insert(5);
        tree.insert(4);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
        tree.insert(1);;
        tree.traverse(tree.root);


    }
}
