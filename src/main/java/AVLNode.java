public class AVLNode{
    int key;
    AVLNode left;
    AVLNode right;
    int height;

    public AVLNode(int key) {
        this.key = key;
        left = right = null;
        height = 0;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
