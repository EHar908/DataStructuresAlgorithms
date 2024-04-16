public class PriorityQueue<E extends Comparable<? super E>> {
    private Node<E> root;

    public void enqueue(E value){
        Node<E> newNode = new Node<E>(value);
        root = merge(root, newNode);
    }
    public E dequeue(){
        if(root != null){
            E dequeuedRoot = root.value;
            root = merge(root.left, root.right);
            return dequeuedRoot;
        }
        else{
            return null;
        }
    }
    public boolean isEmpty(){
        if(root == null){
            return true;
        }
        else{
            return false;
        }
    }
    private Node<E> merge(Node<E> t1, Node<E> t2) {
        Node<E> small;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        if (t1.value.compareTo(t2.value) < 0) {
            t1.right = merge(t1.right, t2);
            small = t1;
        }
        else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNpl(small.left) < getNpl(small.right))
            swapkids(small);
        setNullPathLength(small);
        return small;
    }
    private int getNpl(Node<E> t) {
        if (t == null) return -1;
        return t.npl;
    }
    private void swapkids(Node<E> t){
        Node<E> oldRight = t.right;
        Node<E> oldLeft = t.left;
        t.left = oldRight;
        t.right = oldLeft;
    }
    private void setNullPathLength(Node<E> t){
        if(t == null){
            t.npl = -1;
        }
        else{
            if(t.right != null){
                t.npl = 1 + t.right.npl;
            }
            else{
                t.npl = 0;
            }

        }
    }
    private class Node<E> {
        Node(E value) {
            this(value, null, null);
        }
        Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            npl = 0;
        }
        public E value; // The data in the node
        public Node<E> left; // Left child
        public Node<E> right; // Right child
        public int npl; // null path length
    }
}

