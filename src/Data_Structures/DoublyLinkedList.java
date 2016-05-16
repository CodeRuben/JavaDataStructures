
package Data_Structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements LinearListADT<E>{
    
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private long modificationCounter;
    
    public DoublyLinkedList()
    {
        head = new Node<>(null);
        tail = new Node<>(null);
        this.size = 0;
        this.modificationCounter = 0;
    }
    
    // Creates a new node which takes the first position in the list
    public boolean addFirst(E obj)
    {
        Node<E> newNode = new Node<>(obj);
        
        if(head.next == null)
        {
            newNode.prev = head;
            newNode.next = tail;
            head.next = tail.prev = newNode;
        }
        else
        {
            newNode.next = head.next;
            newNode.prev = head;
            newNode.next.prev = newNode;
            head.next = newNode;    
        }
        size++;
        modificationCounter++;
        return true;
    }
    
    // Creates a new node which takes the first position in the list
    public boolean addLast(E obj)
    {
        Node<E> newNode = new Node<>(obj);
        
        if(head.next == null)
        {
            newNode.prev = head;
            newNode.next = tail;
            head.next = tail.prev = newNode;
        }
        else
        {
            newNode.next = tail;
            newNode.prev = tail.prev;
            tail.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;
        modificationCounter++;
        return true;
    }
    
    // Method removes the first node in the list
    public E removeFirst()
    {
        E removedData; 
       
        if(head.next == null)
            return null;
        else if(size == 1)
        {
            removedData = head.next.data;
            head.next = tail.prev = null;
            size--;
        }
        else
        {
            removedData = head.next.data;
            head.next = head.next.next;
            head.next.prev = head;
            size--;
        }
        modificationCounter++;
        return removedData;
    }

    // Method removes the last node in the list
    public E removeLast()
    {
        E removedData; 
       
        if(head.next == null)
            return null;
        else if(size == 1)
        {
            removedData = tail.prev.data;
            head.next = tail.prev = null;
            size--;
        }
        else
        {
            removedData = tail.prev.data;
            tail.prev = tail.prev.prev;
            tail.prev.next = tail;
            size--;
        }
        modificationCounter++;
        return removedData;        
    }
    
    // Removes an item from the list, given the item is within the list 
    public E remove(E obj)
    {
        Node<E> temp = head.next;
        E info = null;
        
        if(head.next == tail)
            return null;
        
        while(temp.next != null)
        {
            if(((Comparable<E>)obj).compareTo(temp.data) == 0)
            {
                info = temp.data;
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                size--;
                modificationCounter++;
                break;
            }
            temp = temp.next;
        }
        return info;
    }
    
    // Checks if an item is within the list, returns true if it is
    public boolean contains(E obj)
    {
        Node<E> temp = head.next;
        
        while(temp.next != null)
        {
            if(((Comparable<E>)obj).compareTo(temp.data) == 0)
                return true;
            temp = temp.next;
        }
        return false;
    }
    
    public E find(E obj)
    {
        E info;
        Node<E> temp = head.next;
        
        while(temp.next != null)
        {
            info = temp.data;
            if(((Comparable<E>)obj).compareTo(temp.data) == 0)
                return info;
            temp = temp.next;
        }
        return null;
    }
    
    // Returns the value in the first node of the list, if there is one
    public E peekFirst()
    {
        if(size == 0)
            return null;
        return head.next.data;
    }
    
    // Returns the value in the last node of the list, if there is one
    public E peekLast()
    {
        if(size == 0)
            return null;
        return tail.prev.data;
    }
    
    // Returns true if the list has no nodes
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    // Always returns false, linked lists can't be full
    public boolean isFull()
    {
        return false;
    }
    
    // The list is returned to an empty state.
    public void clear()
    {
        head = new Node<>(null);
        tail = new Node<>(null);
        this.size = 0;
        this.modificationCounter = 0;
    }
    
    // Returns the number of nodes in the list
    public int size()
    {
        return size;
    }
    
    // Returns an iterator for the list
    public Iterator<E> iterator()
    {
        return new iteratorHelper();
    }
    
    // Node inner class
    private class Node<T> {
        
        private T data;
        private Node<T> next;
        private Node<T> prev;
        
        public Node(T obj)
        {
            this.data = obj;
            this.next = null; 
            this.prev = null;
        }
    } // End of Node class
    
    class iteratorHelper implements Iterator<E>
    {
        Node<E> index;
        long modCounter;
        
	iteratorHelper() 
        {
	    index = head;
            modCounter = modificationCounter;
	}
        
        public boolean hasNext() 
        {
            if(modCounter != modificationCounter)
                throw new ConcurrentModificationException();
            if(index.next == null)
                return false;
            return index.next != tail;
        }

        public E next() 
        {
            if (!hasNext())
		throw new NoSuchElementException();
            
            index = index.next;
	    return index.data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(); 
        }    
    }
}


