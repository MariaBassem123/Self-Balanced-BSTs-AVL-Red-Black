public class AVLNode<T> {
    T key;
    AVLNode<T> left;
    AVLNode<T> right;
    int height;
    boolean marked;

    public AVLNode(T key) {
        this.key = key;
        left = right = null;
        height = 1;
        marked = false;
    }
}
