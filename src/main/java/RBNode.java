public class RBNode<T> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;
    T key;
    RBNode<T> left;
    RBNode<T> right;
    RBNode<T> parent;
    boolean color; // red = false, black = true

    public RBNode(T key) {
        this.key = key;
        color = RED;
        left = right = parent = null;
    }
}
