public class RedBlackTree implements SelfBalancedBST {
    private RedBlackNode root;
    private int size = 0;

    public RedBlackNode getRoot() {
        return root;
    }

    public void setRoot(RedBlackNode root) {
        this.root = root;
    }

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

    private void rotateLeft(RedBlackNode node) {
        RedBlackNode temp=node.right;
        if(temp==null) {
            return;
        }
        temp.parent=node.parent;
        if(node.parent!=null) {
            //if node.parent==null then temp is new root
            if(node.parent.left==node) node.parent.left=temp;
            else node.parent.right=temp;
        }
        node.parent=temp;
        node.right=temp.left;
        if(temp.left!=null) {
            temp.left.parent=node;
        }
        temp.left=node;

        if(temp.parent==null) root=temp;
    }
    public void rotateRight(RedBlackNode node){
        RedBlackNode temp = node.left;
        if(temp==null) {
            return;
        }
        temp.parent=node.parent;
        if(node.parent!=null) {
            //if node.parent==null then temp is new root
            if(node.parent.left==node) node.parent.left=temp;
            else node.parent.right=temp;
        }
        node.parent=temp;
        node.left = temp.right;
        if(temp.right!=null) {
            temp.right.parent=node;
        }
        temp.right=node;
        if(temp.parent==null) root=temp;

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
            System.out.println(node.key);
            printTree(node.left);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("hi");
        tree.insert("bye");
        tree.insert("yyy");
        tree.insert("tree");
        tree.insert("code");
        tree.insert("dye");
        tree.insert("g");
        tree.insert("e");
        tree.insert("zb");
        tree.insert("za");
        tree.insert("zz");
        tree.insert("s");
        tree.insert("u");
        tree.printTree(tree.root);
        tree.rotateRight(tree.root.right);
        System.out.println(" ");
        tree.printTree(tree.root);

        Object key = "bye";
        boolean found = tree.search(key);
        System.out.println("The key you entered is: " + key +". The key was found = "+ found);
    }
}
