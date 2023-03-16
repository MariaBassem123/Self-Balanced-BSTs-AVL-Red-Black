public class RedBlackNode {
    int key;
    RedBlackNode left;
    RedBlackNode right;
    RedBlackNode parent;
    boolean color; // red = false, black = true

    //enum Color {RED, BLACK}
    // TODO: move constants to another class
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RedBlackNode(int key) {
        this.key = key;
        color = RED;
        left = right = parent = null;
    }
}
