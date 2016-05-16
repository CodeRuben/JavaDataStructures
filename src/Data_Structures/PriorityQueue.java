package Data_Structures;

/**
 *
 * @author Ruben
 */
public class PriorityQueue<E> {
    
    private BinaryHeap<E> queue;
    
    public PriorityQueue(int size) {
        if(size < 1)
            throw new IllegalArgumentException("Size must be larger than 1.");
        this.queue = new BinaryHeap<>(size);
    }
    
    public boolean enqueue(E obj) {
        return queue.add(obj);
    }
    
    public E dequeue() {
        return queue.remove();
    }
    
    public E peek() {
        return queue.peek();
    }
    
    public boolean contains(E obj) {
        return queue.contains(obj);
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public boolean isFull() {
        return queue.isFull();
    }
    
    public void clear() {
        queue.clear();
    }
    
}
