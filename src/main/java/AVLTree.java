import java.util.ArrayList;

public class AVLTree<T extends Comparable<T>> implements SelfBalancedBST<T> {
    private AVLNode<T> root;
    private int size;

    public AVLTree() {
        this.size = 0;
    }

    public AVLNode getRoot(){return root;}
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
        boolean check = search(key);
        if (!check) {//the element is not found
            System.out.println("element not found");
            return false;
        } else {
            root = deleteBST(root, key);
            System.out.println("element found");
            size -= 1;
            return true;
        }
    }

    private AVLNode<T> getSuccessor(AVLNode<T> node) {
        return (node.right == null) ? node : getSuccessor(node.right);
    }

    private AVLNode<T> deleteBST(AVLNode<T> root, T key) {
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) > 0) {
            root.right = deleteBST(root.right, key);
        } else if (key.compareTo(root.key) < 0) {
            root.left = deleteBST(root.left, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                root = root.right;
                return root;
            } else if (root.right == null) {
                root = root.left;
                return root;
            } else {
                AVLNode<T> successor = getSuccessor(root.left);
                root.key = successor.key;
                root.left = deleteBST(root.left, successor.key);
            }

        }
        updateHeight(root);
        return root;
    }

    @Override
    public boolean search(T key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(AVLNode<T> node, T key) {
        if (node == null) {
            return false;
        }
        if (node.key.compareTo(key) == 0) {
            return true;
        }
        if (key.compareTo(node.key) > 0) {
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
        if(root == null){
            return 0;
        }
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
        list.add(node.key);
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

    public static void main(String[] args) {
        AVLTree tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(8);
        tree.insert(12);
        tree.traverse(tree.root);
        tree.delete(10);
        tree.traverse(tree.root);
        System.out.println("the height of the tree " + tree.height());
        System.out.println("size of tree " + tree.size());
    }
}

