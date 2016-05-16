package Data_Structures;

import java.util.Iterator;

/**
 * Implementation of a generic min heap. 
 * @author Ruben Ramirez
 */
public class BinaryHeap<E> implements Iterable<E> {
    
    private Node<E>[] heap;
    private int currentSize;
    private int arraySize;
    private long entryNumber;
    
    public BinaryHeap() {
        this.arraySize = 30;
        this.heap = new Node[arraySize];
        this.currentSize = 0;
        this.entryNumber = 0;
    }
    
    public BinaryHeap(int size) {
        this.arraySize = size;
        this.heap = new Node[arraySize];
        this.currentSize = 0;
        this.entryNumber = 0;
    }
    
    /**
     * Adds a new object to the max heap
     * @param obj, the object to be added to the heap
     * @return true if the object was added, false otherwise
     */
    public boolean add(E obj) {
        if(isFull()) 
            resize();
        heap[currentSize++] = new Node<E>(obj);
        trickleUp();
        return true;
    }
    
    /**
     * Removes and returns the maximum element in the heap
     * @return the maximum element, null if heap is empty
     */
    public E remove() {
        Node<E> tmp;
        
        if(isEmpty())
            return null;
        if(currentSize == 1) {
            tmp = heap[0];
            clear();
            return tmp.data;
        }
        tmp = heap[0];
        heap[0] = heap[--currentSize];
        trickleDown();
        return tmp.data;
    }
    
    /**
     * Returns the value stored at the top of the heap
     * @return E, the value stored as the top of the heap
     */
    public E peek() {
        if(isEmpty())
            return null;
        return heap[0].data;
    }
    
    /**
     * Checks if a value is stored in the heap
     * @param obj, the object to be searched for
     * @return true if the item is in the heap, false otherwise
     */
    public boolean contains(E obj) {
        if(isEmpty())
            return false;
        
        // Search the array for a match
        for(int i = 0; i < currentSize; i++) {
            if(((Comparable<Node<E>>)heap[i]).compareTo(new Node<E>(obj)) == 0)
                return true;
        }
        return false;
    }
    
    /**
     * Private method that performs trickle down operations after
     * a deletion from the heap. Restores heap order structure.
     */
    private void trickleDown() {
        int index = 0;
        int left = (index << 1) + 1;
        int right = (index << 1) + 2;
        Node<E> tmp;
        
        if(left > currentSize || right > currentSize)
            return;
        
        while(((Comparable<Node<E>>)heap[index]).compareTo(heap[left]) > 0 ||
              (((Comparable<Node<E>>)heap[index]).compareTo(heap[right]) > 0)) {
            
            // Left child value is larger than the right child value
            if(((Comparable<Node<E>>)heap[left]).compareTo(heap[right]) < 0) {
                tmp = heap[index];
                heap[index] = heap[left];
                heap[left] = tmp;
                index = left;
            }
            // Left child value is larger than the right child value 
            else {
                tmp = heap[index];
                heap[index] = heap[right];
                heap[right] = tmp;
                index = right;
            }
            left = (index << 1) + 1;
            right = (index << 1) + 2;
            
            if(left > currentSize || right > currentSize)
                return;
        }
    }
    
    /**
     * Private method that performs trickle down operations after
     * an addition into the heap. Restores heap order structure.
     */
    private void trickleUp() {
        int index = currentSize - 1;
        int parent = (index - 1) >> 1;
        Node<E> tmp;
        
        if(parent < 0 || index < 0)
                return;
        
        while(((Comparable<E>)heap[index]).compareTo((E) heap[parent]) < 0) {
            tmp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = tmp;
            index = parent;
            parent = (index - 1) >> 1;
            
            if(parent < 0 || index < 0)
                return;
        }
    }
    
    /**
     * Resets the heap to an empty position
     */
    public void clear() {
        this.heap = new Node[arraySize];
        this.currentSize = 0;
    }
    
    /**
     * Checks if the heap is currently empty
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return currentSize == 0;    
    }
    
    /**
     * Checks if the heap is currently full
     * @return true if the heap is full, false otherwise
     */
    public boolean isFull() {
        return currentSize == arraySize;
    }
    
    /**
     * Returns the number of elements stored in the heap
     * @return int, the size of the heap
     */
    public int size() {
        return this.currentSize;
    }
    
    /**
     * Doubles the size of the heap if it gets full.
     */
    private void resize() {
        this.arraySize = arraySize * 2;
        Node<E>[] newArray = new Node[arraySize];
        
        // Transfer elements into new array
        for(int i = 0; i < currentSize; i++)
            newArray[i] = heap[i];
        heap = newArray;
    }

    /**
     * Returns an iterator over the elements in the heap. There is
     * no guarantee of any ordering in the returned elements. 
     * @return Iterator, an iterator to traverse the heap
     */
    public Iterator<E> iterator() {
        return new HeapIterator();
    }
    
    /**
     * Node class which serves as a wrapper class. Holds a long value which
     * serves as a way to make the heap stable. Equal elements get sorted based
     * on first in first out convention. Implements the compareTo method in the 
     * Comparable interface.
     */
    private class Node<E> implements Comparable<Node<E>> {
        private long order;
        private E data;
        
        public Node(E data) {
            this.order = entryNumber++;
            this.data = data;
        }
        
        public int compareTo(Node<E> node) {
            if(((Comparable<E>)data).compareTo(node.data) == 0)
                return (int) (order - node.order);
            return ((Comparable<E>)data).compareTo(node.data);
        }
    }
    
    /**
     * Class constructs an iterator to be used to traverse the elements
     * in the heap. Implements the methods in the Iterator interface. The 
     * remove() method is not implemented. 
     */
    private class HeapIterator<E> implements Iterator<E> {
        private Node<E>[] array;
        private int index;
        
        public HeapIterator() {
            this.array = new Node[currentSize];
            this.index = 0;
            // Populate new temporary array
            for(int i = 0; i < currentSize; i++) 
                array[i] = (Node<E>) heap[i];
        }

        /**
         * Checks if there are more elements to traverse
         * @return true if there are more elements, false otherwise
         */
        public boolean hasNext() {
            return index < array.length;
        }

        /**
         * Returns the next element in the heap. Returned elements are not in
         * sorted order.
         * @return E, the next element in the heap
         */
        public E next() {
            return array[index++].data;
        }

        /**
         * Unsupported method.
         */
        public void remove() {
            throw new UnsupportedOperationException("Method not supported.");
        }
    }
}
