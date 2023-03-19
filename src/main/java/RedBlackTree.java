import java.util.ArrayList;

public class RedBlackTree<T extends Comparable<T>> implements SelfBalancedBST<T> {
    private RBNode<T> root;
    private int size = 0;
    int height = 0;
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RedBlackTree() {
    }

    public RBNode<T> getRoot() {
        return root;
    }

    public void setRoot(RBNode<T> root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

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
                    RBNode<T> uncle = null;
                    if (isLeftChild(parent)) {
                        uncle = grandParent.right;
                    } else {
                        uncle = grandParent.left;
                    }
                    if (uncle == null || uncle.color == BLACK) {
                        // do suitable rotation and recolor child and grandparent
                        if (isLeftChild(insertedNode) && isLeftChild(parent)) { // LL
                            rotateRight(parent);
                        } else if (isRightChild(insertedNode) && isRightChild(parent)) { // RR
                            rotateLeft(parent);
                        } else if (isLeftChild(parent) && isRightChild(insertedNode)) { // LR
                            rotateLeft(parent);
                            rotateRight(parent);
                        } else if (isRightChild(parent) && isLeftChild(insertedNode)) { // RL
                            rotateRight(parent);
                            rotateLeft(parent);
                        }
                    } else {
                        // uncle.color == RED
                        // recolor and recheck if the grandparent is the root node or not {recursion}

                    }
                }
            }
            return true;
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
        if (hasNoChildren(node)) {//remove leafe node
            if (node.parent != null) {//not a root
                if (isRightChild(node)) {
                    node.parent.right = null;
                } else {
                    node.parent.left = null;
                }
            } else {
                root = null;
            }
            replace = null;
        }

        if (hasOneChild(node)) {
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
        }

        if (hasTwoChildren(node)) {
            successor = getSuccessor(node);
            node.key = successor.key;
            deleteHelper(successor);
        }
        deleteBalance(node, replace);
    }

    private void deleteBalance(RBNode<T> deleted, RBNode<T> replace) {
        if (root == null) return;//no tree

        if (deleted.color == RED) {
            return;
        } else if (deleted.color == BLACK && (replace != null && replace.color == RED)) {
            replace.color = BLACK;
            System.out.println("u->black, v->red");
        } else {//black black
            if (replace != null) doubleBlack(replace);
            else doubleBlack(deleted);
        }
    }

    private void doubleBlack(RBNode<T> x) {
        if (x == root) return;

        RBNode<T> parent = null;
        if (x != null) {//not nil =deleted was not a leaf
            parent = x.parent;//node.parent
        }
        RBNode<T> sibling = sibling(x);

        if (sibling == null) {
            doubleBlack(parent);
        } else {//sibling exist
            if (sibling.color == BLACK) {
                if (hasRedChild(sibling)) {//red cousins
                    if (isLeftChild(sibling)) {//L
                        if (sibling.left != null && sibling.left.color == RED) {//LL
                            sibling.left.color = BLACK;
                            sibling.color = parent.color;
                            rotateRight(parent);
                        } else {//LR
                            sibling.right.color = parent.color;
                            rotateLeft(sibling);
                            rotateRight(parent);
                        }

                    } else {//R
                        if (sibling.left != null && sibling.left.color == RED) {//RL
                            sibling.left.color = parent.color;
                            rotateRight(sibling);
                            rotateLeft(parent);
                        } else {//RR
                            sibling.right.color = BLACK;
                            sibling.color = parent.color;
                            rotateLeft(parent);
                        }

                    }
                    parent.color = BLACK;
                } else {// 2black cousins
                    sibling.color = RED;
                    if (parent.color == RED) {
                        parent.color = BLACK;
                    } else {
                        doubleBlack(parent);
                    }
                }

            } else if (sibling.color == RED) {
                parent.color = RED;
                sibling.color = BLACK;
                if (isRightChild(sibling)) {
                    rotateLeft(parent);
                } else {
                    rotateRight(parent);
                }
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
        height = 0;
        height = getHeight(root);
        return 0;
    }

    private int getHeight(RBNode<T> node) {
        if (node != null) {
            height++;
            printTree(node.left);
            printTree(node.right);
        }
        return height;
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

    public void rotateRight(RBNode<T> node) {
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

    private void printTree(RBNode<T> node) {
        if (node != null) {
            System.out.println(node.key);
            System.out.println(node.color ? "black" : "red");
            printTree(node.left);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree<String> tree = new RedBlackTree<>();
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

        tree.insert("bye");
        tree.insert("hi");
        tree.insert("a");
        tree.printTree(tree.root);
        tree.delete("bye");
        tree.printTree(tree.root);
        String key = "bye";
        boolean found = tree.search(key);
        System.out.println("The key you entered is: " + key + ". The key was found = " + found);
    }
}
