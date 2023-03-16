public class RedBlackTree implements SelfBalancedBST {
    private RedBlackNode root;
    private int size = 0;

    public int getSize() {
        return size;
    }

    @Override
    public boolean insert(Object key) {
        boolean exist = search(key);
        if(exist){
            return false; // the key is already found. No insertion
        }else{
            RedBlackNode insertedNode = insertHelper(root, key);
            return true;
        }
    }

    private RedBlackNode insertHelper(RedBlackNode node, Object key) {
        RedBlackNode nodeToBeInserted = new RedBlackNode(key);
        if(size == 0){
            root = nodeToBeInserted;
            size++;
            return root;
        }
        String nodeKey = (String) node.key;
        String givenKey = (String) key;

        if(nodeKey.compareTo(givenKey) > 0){
            // node key > key
            // go left
            if(node.left == null){
                // insert Here
                node.left = nodeToBeInserted;
                nodeToBeInserted.parent = node;
                size++;
                return nodeToBeInserted;
            }
            return insertHelper(node.left, key);
        }
        else{
            // node key < key
            // go right
            if(node.right == null){
                // insert Here
                node.right = nodeToBeInserted;
                nodeToBeInserted.parent = node;
                size++;
                return nodeToBeInserted;
            }
            return insertHelper(node.right, key);
        }

    }

    @Override
    public boolean delete(Object key) {
        return false;
    }

    @Override
    public boolean search(Object key) {
        if(size != 0){
            RedBlackNode requiredNode = searchHelper(root, key);
            return requiredNode != null;
        }
           return false;
    }

    private RedBlackNode searchHelper(RedBlackNode node, Object key) {

        String nodeKey = (String) node.key;
        String givenKey = (String) key;
        if(node.key.equals(key)){
            return node;
        }
        else if(nodeKey.compareTo(givenKey) > 0){
            // node key > key
            // go left
            if(node.left == null){
                return null;
            }
            return searchHelper(node.left, key);
        }
        else{
            // node key < key
            // go right
            if(node.right == null){
                return null;
            }
            return searchHelper(node.right, key);
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    private void printTree(RedBlackNode node){
//        if(node.right!=null){
//            printTree(root.right);
//        }
//        System.out.println(node.key);
//        if(root.left!=null){
//            printTree(root.left);
//        }
//        return;
        if(node != null)
        {
            printTree(node.left);
            System.out.println(node.key);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("hi");
        tree.insert("bye");
        tree.insert("yyy");
        tree.printTree(tree.root);

        Object key = "bye";
        boolean found = tree.search(key);
        System.out.println("The key you entered is: " + key +". The key was found = "+ found);
    }
}
