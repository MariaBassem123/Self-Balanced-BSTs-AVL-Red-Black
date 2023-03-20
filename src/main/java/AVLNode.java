public class AVLNode<T> {
    T key;
    AVLNode<T> left;
    AVLNode<T> right;
    int height;

    public AVLNode(T key) {
        this.key = key;
        left = right = null;
        height = 1;
    }
}
