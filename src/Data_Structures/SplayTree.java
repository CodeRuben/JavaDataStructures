
package Data_Structures;

public class SplayTree<K,V> {
    public Node<K,V> root;
    private int currentSize;
    
    public SplayTree() {
        this.root = null;
        this.currentSize = 0;
    }
    
    public boolean add(K key, V val) {
        Node<K,V> newNode = new Node<>(key, val);
        
        if(root == null) {
            root = newNode;
            currentSize++;
            return true;
        }
        if(addNode(root, newNode) == false)
            return true;
        currentSize++;
        return true;
    }
    
    private boolean addNode(Node<K,V> parent, Node<K,V> newNode) {
        
        if(((Comparable<K>)newNode.key).compareTo(parent.key) < 0) {
            if(parent.left == null) {
                parent.left = newNode;
                newNode.parent = parent;
                splay(newNode);
                return true;
            }
            else
                addNode(parent.left, newNode);
        }
        else if(((Comparable<K>)newNode.key).compareTo(parent.key) > 0) {
            if(parent.right == null) {
                parent.right = newNode;
                newNode.parent = parent;
                splay(newNode);
                return true;
            }
            else
                addNode(parent.right, newNode);
        }
        else
            parent.value = newNode.value;
        return false;
    }
    
    public boolean contains(K key) {
        if(isEmpty())
            return false;
        return contains(null, root, key);
    }
    
    public boolean isEmpty() {
        return currentSize == 0;
    }
    
    public boolean isFull() {
        return false;
    }
    
    private boolean contains(Node<K,V> top, Node<K,V> node, K toFind) {
        if(node == null) {
            if(top == root)
                return false;
            splay(top);
            return false;
        }

        if(((Comparable<K>)toFind).compareTo(node.key) < 0)
            return contains(node, node.left, toFind);
        else if(((Comparable<K>)toFind).compareTo(node.key) > 0)
            return contains(node, node.right, toFind);
        else {
            if(node != root)
                splay(node);
            return true;
        }
    }
    
    private void splay(Node<K,V> node) {
        Node<K,V> parent = node.parent;
        Node<K,V> grand = node.parent.parent;
        
        while(node != root) {
            if(parent == root) {
                //single rotate on root
                if(((Comparable<K>)node.key).compareTo(node.parent.key) < 0)
                    rightRotate(root);
                else
                    leftRotate(root);
                root.parent = null;
                return;
            }
            if(((Comparable<K>)parent.key).compareTo(parent.parent.key) > 0) {
                //right/left(right-left rotate)
                if(((Comparable<K>)node.key).compareTo(node.parent.key) < 0) {
                    rightRotate(parent);
                    leftRotate(grand);
                }
                //right/right(left-left rotate)
                else {
                    leftRotate(grand);
                    leftRotate(parent);
                }
            }
            else {
                //left/right(left-right rotate)
                if(((Comparable<K>)node.key).compareTo(node.parent.key) > 0) {
                    leftRotate(parent);
                    rightRotate(grand);
                }
                //left/left(right-right rotate)
                else { 
                    rightRotate(grand);
                    rightRotate(parent);
                }
            }
            if(node.parent != null) {
                parent = node.parent;
                grand = node.parent.parent;
            }
        }
    }
    
    private void leftRotate(Node<K,V> node) {
        Node<K,V> newTop = node.right;
        node.right = newTop.left;
        if(newTop.left != null)
            newTop.left.parent = node;
        
        if(node.parent == null)
            root = newTop;
        else if(((Comparable<K>)node.key).compareTo(node.parent.key) <= 0)
            node.parent.left = newTop;
        else
            node.parent.right = newTop;
        
        newTop.left = node;
        newTop.parent = node.parent;
        node.parent = newTop;
    }
    
    private void rightRotate(Node<K,V> node) {
        Node<K,V> newTop = node.left;
        node.left = newTop.right;
        if(newTop.right != null)
            newTop.right.parent = node;
        
        if(node.parent == null)
            root = newTop;
        else if(((Comparable<K>)node.key).compareTo(node.parent.key) <= 0)
            node.parent.left = newTop;
        else
            node.parent.right = newTop;
        
        newTop.right = node;
        newTop.parent = node.parent;
        node.parent = newTop;
    }
    
    public void inorder(Node<K,V> node) {
        if(node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }
    
    public void breadthTraversal() {
        Queue<Node<K,V>> q = new Queue<>();
        Node<K,V> node;
        
        if(root != null) {
            q.enqueue(root);
            
            while(!q.isEmpty()) {
                node = q.dequeue();
                System.out.print(node.key + " ");
                if(node != null) {
                    if(node.left != null)
                        q.enqueue(node.left);
                    if(node.right != null)
                        q.enqueue(node.right);
                   
                }
            }
        }
    }
    
    private class Node<K,V> implements Comparable<Node<K,V>> {
        private K key;
        private V value;
        private Node<K,V> left, right, parent;
        
        public Node(K key, V val) {
            this.key = key;
            this.value = val;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
        
        public int compareTo(Node<K,V> node) {
            return (((Comparable<K>)key).compareTo(node.key));
        }
    }
}
