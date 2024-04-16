public class Queue<E> {
    public Queue() { this.size = 0; tail = head; }
    private ListNode<E> head = new ListNode<>();
    private ListNode<E> tail;
    private int size;

    public int getSize() { return this.size; }
    public boolean isEmpty() { return this.size == 0; }
    private class ListNode<E> {
        public E value;
        public ListNode<E> next;
        public ListNode() {
        }
        public ListNode(E o) {
            this.value = o;
        }
    }

    public void enqueue(E value) {
        ListNode<E> node = new ListNode(value);
        ListNode<E> tempNode = head.next;

        node.next = tempNode;
        head.next = node;

        this.size++;
    }
    public E dequeue() {
        ListNode<E> tempHead = head;
        ListNode<E> tempHeadNext = head.next;
        while(tempHeadNext.next != null){
            tempHead = tempHeadNext;
            tempHeadNext = tempHeadNext.next;
        }
        tempHead.next = null;
        this.size--;
        return tempHeadNext.value;

    }
}
