public class RBNode<T> {
    T key;
    RBNode<T> left;
    RBNode<T> right;
    RBNode<T> parent;
    boolean color; // red = false, black = true
    //enum Color {RED, BLACK}
    // TODO: move constants to another class
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RBNode(T key) {
        this.key = key;
        color = RED;
        left = right = parent = null;
    }
}
