import java.util.ArrayList;

public class RedBlackTree<T extends Comparable<T>> implements SelfBalancedBST<T> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;
    private RBNode<T> root;
    private int size;
    private int height;

    public RedBlackTree() {
        this.size = 0;
        this.height = 0;
    }

    public RBNode<T> getRoot() {
        return root;
    }
//
//    public void setRoot(RBNode<T> root) {
//        this.root = root;
//    }

    @Override
    public boolean insert(T key) {
        boolean exist = search(key);
        if (exist) {
            return false; // the key is already found. No insertion
        } else {
            RBNode<T> insertedNode = insertBST(root, key);
            if (size == 1) {
                insertedNode.color = BLACK; // root node with color = black
            } else if (size > 1) {
                // more than one node are found
                insertedNode.color = RED;
                RBNode<T> parent = insertedNode.parent;
                RBNode<T> grandParent = parent.parent;
                if (grandParent == null) {
                    // parent is the root node which is BLACK so no problem
                    return true;
                }
                if (parent.color == BLACK) {
                    return true;
                } else {
//                    insertedNode.parent.color == RED
                    recheckRedRed(insertedNode);
                }
            }
            return true;
        }
    }

    private void recheckRedRed(RBNode<T> insertedNode) {
        RBNode<T> parent = insertedNode.parent;
        RBNode<T> grandParent = parent.parent;
        if (grandParent == null) {
            // parent is the root node which is BLACK so no problem
            return;
        }
        if (parent.color == BLACK) {
            return;
        }
//            insertedNode.parent.color == RED Red red conflict
        else {
            RBNode<T> uncle;
            if (isLeftChild(parent)) {
                uncle = grandParent.right;
            } else {
                uncle = grandParent.left;
            }
            if (uncle == null || uncle.color == BLACK) {
                boolean LR_RL_Flag = false;
                // do suitable rotation and recolor new parent and new grandparent
                if (isLeftChild(insertedNode) && isLeftChild(parent)) { // LL
                    rotateRight(grandParent);
                } else if (isRightChild(insertedNode) && isRightChild(parent)) { // RR
                    rotateLeft(grandParent);
                } else if (isLeftChild(parent) && isRightChild(insertedNode)) { // LR
                    LR_RL_Flag = true;
                    rotateLeft(parent);
                    rotateRight(grandParent);
                } else if (isRightChild(parent) && isLeftChild(insertedNode)) { // RL
                    LR_RL_Flag = true;
                    rotateRight(parent);
                    rotateLeft(grandParent);
                }
                // recolor
                if (grandParent.color == RED) {
                    grandParent.color = BLACK;
                } else {
                    grandParent.color = RED;
                }
                if (LR_RL_Flag == true) {
                    if (insertedNode.color == RED) {
                        insertedNode.color = BLACK;
                    } else {
                        insertedNode.color = RED;
                    }
                } else {
                    if (parent.color == RED) {
                        parent.color = BLACK;
                    } else {
                        parent.color = RED;
                    }
                }

            } else {
                // uncle.color == RED
                // recolor parent and uncle and recheck if the grandparent is the root node or not {recursion}
                if (parent.color == RED) {
                    parent.color = BLACK;
                } else {
                    parent.color = RED;
                }
                if (uncle.color == RED) {
                    uncle.color = BLACK;
                } else {
                    uncle.color = RED;
                }
                // recheck if the grandparent is not the root
                if (root != grandParent) {
                    // if not root node then recolor it
                    if (grandParent.color == RED) {
                        grandParent.color = BLACK;
                    } else {
                        grandParent.color = RED;
                    }
//                  recheck red red conflicts
                    recheckRedRed(grandParent);
                }
            }
        }
    }

    private boolean isLeftChild(RBNode<T> node) {
        return node.parent.left == node;
    }

    private boolean isRightChild(RBNode<T> node) {
        return node.parent.right == node;
    }

    private RBNode<T> getSuccessor(RBNode<T> node) {
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private boolean hasNoChildren(RBNode<T> node) {
        return node.left == null && node.right == null;
    }

    private boolean hasOneChild(RBNode<T> node) {
        return (node.left == null && node.right != null) || (node.left != null && node.right == null);
    }

    private boolean hasTwoChildren(RBNode<T> node) {
        return node.left != null && node.right != null;
    }

    private RBNode<T> sibling(RBNode<T> x) {
        if (x.parent == null) return null;

        if (isLeftChild(x)) return x.parent.right;
        else return x.parent.left;
    }

    private boolean hasRedChild(RBNode<T> node) {

        return (node.left != null && node.left.color == RED) || (node.right != null && node.right.color == RED);
    }

    private RBNode<T> insertBST(RBNode<T> node, T key) {
        RBNode<T> nodeToBeInserted = new RBNode<>(key);
        if (size == 0) {
            root = nodeToBeInserted;
            size++;
            return root;
        }
        if (node.key.compareTo(key) > 0) {
            // node key > key
            // go left
            if (node.left == null) {
                // insert Here
                node.left = nodeToBeInserted;
                nodeToBeInserted.parent = node;
                size++;
                return nodeToBeInserted;
            }
            return insertBST(node.left, key);
        } else {
            // node key < key
            // go right
            if (node.right == null) {
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
    public boolean delete(T key) {
        RBNode<T> nodeToDelete = searchHelper(root, key);
        if (nodeToDelete != null) {
            size--;
            deleteHelper(nodeToDelete);
            return true;
        }
        return false;
    }


    private void deleteHelper(RBNode<T> node) {
        boolean deletedNodeColor = node.color;
        RBNode<T> successor = node;
        RBNode<T> replace = node;
        if (hasNoChildren(node)) {//remove leaf node
            if (node.parent != null) {//not a root
                replace = null;
                deleteBalance(node, replace);
                if (isRightChild(node)) {
                    node.parent.right = null;
                } else {
                    node.parent.left = null;
                }
            } else {
                root = null;
                return;
            }

        }

        else if (hasOneChild(node)) {
            RBNode<T> child = ((node.right != null) ? node.right : node.left);
            child.parent = node.parent;
            if (node == root) {
                root = child;
            } else {
                if (isRightChild(node)) {
                    node.parent.right = child;
                } else {
                    node.parent.left = child;
                }
            }
            replace = child;
            deleteBalance(node, replace);
        }

        else if (hasTwoChildren(node)) {
            successor = getSuccessor(node);
            node.key = successor.key;
            deleteHelper(successor);
            return;
        }
    }


    private void deleteBalance(RBNode<T> deleted, RBNode<T> replace) {
        if (root == null) return;//no tree

        if (deleted.color == RED) {
            return;
        } else if (deleted.color == BLACK && (replace != null && replace.color == RED)) {
            replace.color = BLACK;
//            System.out.println("u->black, v->red");
        } else {//black black
//            System.out.println("black black");
            if (replace != null) doubleBlack(replace);
            else{
//                System.out.println("in black black, replace = null");
                doubleBlack(deleted);
            }
        }
    }

    private void doubleBlack(RBNode<T> x) {

        if (x == root) return;

        RBNode<T> parent = null;
        if (x != null) {//not nil = deleted was not a leaf
            parent = x.parent;//node.parent
        }
        RBNode<T> sibling = sibling(x);

        if (sibling == null) {
            doubleBlack(parent);
        } else {//sibling exist
//            System.out.println("sibling exist");
//            System.out.println("sibling = "+ sibling.key);
            if (sibling.color == BLACK) {
//                System.out.println("sibling = black");
//                System.out.println("sibling "+ sibling.key);
                if (hasRedChild(sibling)) {//red cousins
//                    System.out.println("red cousin");
                    if (isLeftChild(sibling)) {//L
                        if (sibling.left != null && sibling.left.color == RED) {//LL
//                            System.out.println("Before LL");
//                            printTree(root);
//                            System.out.println("--------------------------------------");

                            sibling.left.color = BLACK;
                            sibling.color = parent.color;
                            rotateRight(parent);

                        } else {//LR
//                            System.out.println("Before LR");
//                            printTree(root);
//                            System.out.println("--------------------------------------");

                            sibling.right.color = parent.color;
                            rotateLeft(sibling);
                            rotateRight(parent);
                        }

                    } else {//R
//                        System.out.println("In R in double Black");
                        if (sibling.left != null && sibling.left.color == RED) {//RL
//                            System.out.println("Before RL");
//                            printTree(root);
//                            System.out.println("--------------------------------------");

                            sibling.left.color = parent.color;
                            rotateRight(sibling);
                            rotateLeft(parent);
                        } else {//RR
//                            System.out.println("Before RR");
//                            printTree(root);
//                            System.out.println("--------------------------------------");

                            sibling.right.color = BLACK;
                            sibling.color = parent.color;
                            rotateLeft(parent);
                        }

                    }
                    parent.color = BLACK;
//                    System.out.println("Print Tree");
//                    printTree(root);
//                    System.out.println("--------------------------------------");
                } else {// 2black cousins
//                    System.out.println("2 black cousin");
                    sibling.color = RED;
                    if (parent.color == RED) {
                        parent.color = BLACK;
                    } else {
//                        System.out.println("double black parent");
                        doubleBlack(parent);
                    }

//                    System.out.println("Print Tree after 2 black cousins");
//                    printTree(root);
//                    System.out.println("--------------------------------------");
                }

            } else if (sibling.color == RED) {
//                System.out.println("sibling.color == RED");
                parent.color = RED;
                sibling.color = BLACK;
                if (isRightChild(sibling)) {
                    rotateLeft(parent);
                } else {
                    rotateRight(parent);
                }
//                System.out.println("Print Tree if sibling.color = RED");
//                printTree(root);
//                System.out.println("--------------------------------------");

                doubleBlack(x);
            }
        }
    }

    @Override
    public boolean search(T key) {
        if (size != 0) {
            RBNode<T> requiredNode = searchHelper(root, key);
            return requiredNode != null;
        }
        return false;
    }

    private RBNode<T> searchHelper(RBNode<T> node, T key) {
        if(node == null){
            return null;
        }
        if (node.key.equals(key)) {
            return node;
        } else if (node.key.compareTo(key) > 0) {
            // node key > key
            // go left
            if (node.left == null) {
                return null;
            }
            return searchHelper(node.left, key);
        } else {
            // node key < key
            // go right
            if (node.right == null) {
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
        return getHeight(root);
    }

    private int getHeight(RBNode<T> node) {
        if (node == null)
            return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public ArrayList<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(RBNode<T> node, ArrayList<T> list) {
        if (node == null)
            return;

        //recur on the left child
        inorder(node.left, list);
        // Adds data to the list
        list.add(node.key);
        //recur on the right child
        inorder(node.right, list);
    }

    private void rotateLeft(RBNode<T> node) {
        RBNode<T> temp = node.right;
        if (temp == null) {
            return;
        }
        temp.parent = node.parent;
        if (node.parent != null) {
            //if node.parent == null then temp is new root
            if (isLeftChild(node)) node.parent.left = temp;
            else node.parent.right = temp;
        }
        node.parent = temp;
        node.right = temp.left;
        if (temp.left != null) {
            temp.left.parent = node;
        }
        temp.left = node;

        if (temp.parent == null) root = temp;
    }

    private void rotateRight(RBNode<T> node) {
        RBNode<T> temp = node.left;
        if (temp == null) {
            return;
        }
        temp.parent = node.parent;
        if (node.parent != null) {
            //if node.parent==null then temp is new root
            if (isLeftChild(node)) node.parent.left = temp;
            else node.parent.right = temp;
        }
        node.parent = temp;
        node.left = temp.right;
        if (temp.right != null) {
            temp.right.parent = node;
        }
        temp.right = node;
        if (temp.parent == null) root = temp;
    }

    public void printTree(RBNode<T> node) {
        if (node != null) {
            String color = node.color == RED ? "RED" : "BLACK";
            printTree(node.left);
            System.out.println(node.key +" --> "+ color);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {

//        RedBlackTree<String> tree = new RedBlackTree<>();
//        tree.insert("hi");
//        tree.insert("bye");
//        tree.insert("yyy");
        //tree.insert("tree");
        //tree.insert("code");
        //tree.insert("dye");
        //tree.insert("g");
        //tree.insert("e");
        //tree.insert("zb");
        // trial 1:
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
//        tree.insert(2);
//        tree.insert(1);
//        tree.insert(70);
//        tree.insert("hi");
//        tree.insert("bye");
//        tree.insert("yyy");
//        tree.insert("tree");
//        tree.insert("code");
//        tree.insert("dye");
//        tree.insert("g");
//        tree.insert("e");
//        tree.insert("zb");

//        tree.insert("za");
//        tree.insert("zz");
//        tree.insert("s");
//        tree.insert("u");
//        tree.printTree(tree.root);
//        tree.rotateRight(tree.root.right);
//        System.out.println(" ");


//        tree.insert("bye");
//        tree.insert("hi");
//        tree.insert("a");
        tree.printTree(tree.root);
        System.out.println("Height = " + tree.height());
//        tree.delete("bye");
//        tree.printTree(tree.root);
//        String key = "bye";
//        boolean found = tree.search(key);
//        System.out.println("The key you entered is: " + key + ". The key was found = " + found);
    }
}
