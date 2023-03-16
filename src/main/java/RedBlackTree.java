public class RedBlackTree implements SelfBalancedBST {
    private RedBlackNode root;
    private int size = 0;
    public static final boolean RED = false;
    public static final boolean BLACK = true;

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
            RedBlackNode insertedNode = insertBST(root, key);
            if(size == 1){
                insertedNode.color = BLACK; // root node with color = black
            }else if(size > 1){
                // more than one node are found
                insertedNode.color = RED;
                RedBlackNode parent = insertedNode.parent;
                RedBlackNode grandParent = parent.parent;
                if(grandParent == null){
                    // parent is the root node which is BLACK so no problem
                    return true;
                }
                if(parent.color == BLACK){
                    return true;
                }else {
//                    insertedNode.parent.color == RED
                    RedBlackNode uncle = null;
                    if(isLeftChild(parent)){
                        uncle = grandParent.right;
                    }else{
                        uncle = grandParent.left;
                    }
                    if(uncle == null || uncle.color == BLACK){
                        // do suitable rotation and recolor child and grandparent
                    }else{
                        // uncle.color == RED
                        // recolor and recheck if the grandparent is the root node or not {recursion}

                    }
                }
            }
            return true;
        }
    }

    private boolean isLeftChild(RedBlackNode node){
        return node.parent.left == node;
    }
    private boolean isRightChild(RedBlackNode node){
        return node.parent.right == node;
    }

    private RedBlackNode insertBST(RedBlackNode node, Object key) {
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
            return insertBST(node.left, key);
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
            return insertBST(node.right, key);
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
        return size;
    }

    @Override
    public int height() {
        return 0;
    }

    private void rotateLeft(RedBlackNode node) {
        RedBlackNode temp = node.right;
        if(temp == null) {
            return;
        }
        temp.parent = node.parent;
        if(node.parent != null) {
            //if node.parent==null then temp is new root
            if(node.parent.left == node) node.parent.left=temp;
            else node.parent.right = temp;
        }
        node.parent = temp;
        node.right = temp.left;
        if(temp.left != null) {
            temp.left.parent = node;
        }
        temp.left = node;

        if(temp.parent == null) root=temp;
    }

    public void rotateRight(RedBlackNode node){
        RedBlackNode temp = node.left;
        if(temp == null) {
            return;
        }
        temp.parent=node.parent;
        if(node.parent != null) {
            //if node.parent==null then temp is new root
            if(node.parent.left == node) node.parent.left = temp;
            else node.parent.right = temp;
        }
        node.parent = temp;
        node.left = temp.right;
        if(temp.right != null) {
            temp.right.parent = node;
        }
        temp.right = node;
        if(temp.parent == null) root=temp;

    }

    private void printTree(RedBlackNode node){
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