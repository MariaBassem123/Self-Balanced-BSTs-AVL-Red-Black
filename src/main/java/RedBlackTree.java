import java.util.ArrayList;

public class RedBlackTree implements SelfBalancedBST {
    private RBNode root;
    private int size = 0;
    int height = 0;
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RedBlackTree() {
    }

    public RedBlackTree(ArrayList<Object> list) {
        this.root = ALToBST(list, 0, list.size());
    }

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean insert(Object key) {
        boolean exist = search(key);
        if (exist) {
            return false; // the key is already found. No insertion
        } else {
            RBNode insertedNode = insertBST(root, key);
            if (size == 1) {
                insertedNode.color = BLACK; // root node with color = black
            } else if (size > 1) {
                // more than one node are found
                insertedNode.color = RED;
                RBNode parent = insertedNode.parent;
                RBNode grandParent = parent.parent;
                if (grandParent == null) {
                    // parent is the root node which is BLACK so no problem
                    return true;
                }
                if (parent.color == BLACK) {
                    return true;
                } else {
//                    insertedNode.parent.color == RED
                    RBNode uncle = null;
                    if (isLeftChild(parent)) {
                        uncle = grandParent.right;
                    } else {
                        uncle = grandParent.left;
                    }
                    if (uncle == null || uncle.color == BLACK) {
                        // do suitable rotation and recolor child and grandparent
                        if(isLeftChild(insertedNode) && isLeftChild(parent)){ // LL
                            rotateRight(parent);
                        }else if(isRightChild(insertedNode) && isRightChild(parent)){ // RR
                            rotateLeft(parent);
                        }else if(isLeftChild(parent) && isRightChild(insertedNode)){ // LR
                            rotateLeft(parent);
                            rotateRight(parent);
                        }else if(isRightChild(parent) && isLeftChild(insertedNode)){ // RL
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

    private boolean isLeftChild(RBNode node) {
        return node.parent.left == node;
    }

    private boolean isRightChild(RBNode node) {
        return node.parent.right == node;
    }
    
    private RBNode getSuccessor(RBNode node) {
    	node=node.right;
    	while(node.left!=null) {
    		node=node.left;
    	}
    	return node;
    }
    
    private boolean hasNoChildren(RBNode node) {
        return node.left==null && node.right==null;
    }
    
    private boolean hasOneChild(RBNode node) {
        return (node.left==null && node.right!=null)||(node.left!=null && node.right==null);
    }
    
    private boolean hasTwoChildren(RBNode node) {
        return node.left!=null && node.right!=null;
    }
    private RBNode sibling(RBNode x) {
    	if(x.parent==null) return null;
    	
    	if(isLeftChild(x))return x.parent.right;
    	else return x.parent.left;
    }
    
    private boolean hasRedChild(RBNode node) {
        return (node.left!=null && node.left.color==RED) || (node.right!=null && node.right.color==RED);
    }
    
    private RBNode insertBST(RBNode node, Object key) {
        RBNode nodeToBeInserted = new RBNode(key);
        if (size == 0) {
            root = nodeToBeInserted;
            size++;
            return root;
        }
        String nodeKey = (String) node.key;
        String givenKey = (String) key;

        if (nodeKey.compareTo(givenKey) > 0) {
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
    public boolean delete(Object key) {
    	RBNode nodeToDelete=searchHelper(root,key);
    	if(nodeToDelete!=null) {
    		size--;
    		deleteHelper(nodeToDelete);
    		return true;
    	}
        return false;
    }
    
    private void deleteHelper(RBNode node) {
    	boolean deletedNodeColor=node.color;
    	RBNode successor=node;
    	RBNode replace=node;
    	if(hasNoChildren(node)) {//remove leafe node
    		if(node.parent!=null) {//not a root
    			if(isRightChild(node)) {
        			node.parent.right=null;
        		}else {
        			node.parent.left=null;
        		}
    		}else {
    			root=null;
    		}
    		replace=null;
    	}
    	
    	if(hasOneChild(node)) {
    		RBNode child=((node.right!=null)? node.right: node.left);
    		child.parent=node.parent;
    		if(node==root) {
    			root=child;
    		}else {
    			if(isRightChild(node)) {
    				node.parent.right=child;    			
    			}else {
    				node.parent.left=child;
    			}
    		}
    		replace=child;
    	}
    	
    	if(hasTwoChildren(node)) {
    		successor=getSuccessor(node);
    		node.key=successor.key;
    		deleteHelper(successor);
    	}
    	deleteBalance(node,replace);
    }
    
    private void deleteBalance(RBNode deleted,RBNode replace) {
    	if(root==null)return;//no tree
    	
    	if(deleted.color==RED) {
    		return;
    	}else if(deleted.color==BLACK &&(replace!=null && replace.color==RED)) {
    		replace.color=BLACK;
    		System.out.println("u->black, v->red");
    		return;
    	}else {//black black
    		if(replace!=null) doubleBlack(replace);
    		else doubleBlack(deleted);
    	}
    }
    private void doubleBlack(RBNode x) {
    	if(x==root) return;
    	
    	RBNode parent=null;
    	if(x!=null) {//not nil =deleted was not a leaf 
    		parent=x.parent;//node.parent
    	}
    	RBNode sibling=sibling(x);
    	
    	if(sibling==null) {
    		doubleBlack(parent);
    	}else {//sibling exist
    		if(sibling.color==BLACK) {
    			if(hasRedChild(sibling)) {//red cousins
    				if(isLeftChild(sibling)) {//L
    					if(sibling.left!=null && sibling.left.color==RED) {//LL
    						sibling.left.color=BLACK;
    						sibling.color=parent.color;
    						rotateRight(parent);
    					}else {//LR
    						sibling.right.color=parent.color;
    						rotateLeft(sibling);
    						rotateRight(parent);
    					}
    					
    				}else {//R
    					if(sibling.left!=null && sibling.left.color==RED) {//RL
    						sibling.left.color=parent.color;
    						rotateRight(sibling);
    						rotateLeft(parent);
    					}else {//RR
    						sibling.right.color=BLACK;
    						sibling.color=parent.color;
    						rotateLeft(parent);
    					}
    					
    				}
    				parent.color=BLACK;
    			}else {// 2black cousins
    				sibling.color=RED;
    				if(parent.color==RED) {
    					parent.color=BLACK;
    				}else {
    					doubleBlack(parent);
    				}
    			}
    			
    		}else if(sibling.color==RED){
    			parent.color=RED;
    			sibling.color=BLACK;
    			if(isRightChild(sibling)) {
    				rotateLeft(parent);
    			}else {
    				rotateRight(parent);
    			}
    			doubleBlack(x);
    		}
    	}
    }

    @Override
    public boolean search(Object key) {
        if (size != 0) {
            RBNode requiredNode = searchHelper(root, key);
            return requiredNode != null;
        }
        return false;
    }

    private RBNode searchHelper(RBNode node, Object key) {

        String nodeKey = (String) node.key;
        String givenKey = (String) key;
        if (node.key.equals(key)) {
            return node;
        } else if (nodeKey.compareTo(givenKey) > 0) {
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
    private int getHeight(RBNode node){
        if (node != null) {
            height++;
            printTree(node.left);
            printTree(node.right);
        }
        return height;
    }
    public ArrayList<Object> inorder() {
        return null;
    }

    public RBNode ALToBST(ArrayList<Object> list, int start, int end) {
        if (start > end)
            return null;
        int mid = (end - start) / 2;
        RBNode node = new RBNode(list.get(mid));
        node.left = ALToBST(list, start, mid - 1);
        node.right = ALToBST(list, mid + 1, end);
        return node;

    }

    private void rotateLeft(RBNode node) {
        RBNode temp = node.right;
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

    public void rotateRight(RBNode node) {
        RBNode temp = node.left;
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

    private void printTree(RBNode node) {
        if (node != null) {
            System.out.println(node.key);
            System.out.println(node.color?"black":"red");
            printTree(node.left);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
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
        Object key = "bye";
        boolean found = tree.search(key);
        System.out.println("The key you entered is: " + key + ". The key was found = " + found);
    }
}
