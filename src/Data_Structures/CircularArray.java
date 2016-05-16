
package Data_Structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularArray<E> implements LinearListADT<E> {
    
    private E[] list;
    private final int listSize;
    private int currentSize, firstPosition, lastPosition;
    
    public CircularArray() {
        this.listSize = DEFAULT_MAX_CAPACITY;
        this.list = (E[]) new Object[listSize];
        this.firstPosition = 0;
        this.lastPosition = 99;
        this.currentSize = 0;
    }
    
    public CircularArray(int size) {
        if(size <= 0) 
            throw new IllegalArgumentException(); 
        
        this.listSize = size;
        this.list = (E[]) new Object[listSize];
        this.firstPosition = 0;
        this.lastPosition = listSize - 1;
        this.currentSize = 0;
    }

//  Adds the Object obj to the beginning of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full.
    public boolean addFirst(E obj) {
        if((firstPosition == 0 && list[listSize - 1] != null) || isFull()) 
            return false;

        if(firstPosition == 0 && list[listSize - 1] == null)
            firstPosition = listSize;
        
        list[firstPosition - 1] = obj;
        this.firstPosition--;
        this.currentSize++;
        return true;
    }
    
//  Adds the Object obj to the end of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full..  
    public boolean addLast(E obj) {
        if(isFull()) 
            return false; 
        
        if(lastPosition == listSize - 1 && list[0] == null)
            lastPosition = -1;
        
        list[lastPosition + 1] = obj;
        this.lastPosition++;
        this.currentSize++;
        return true;
    }
    
//  Removes and returns the parameter object obj in first position in list if the list is not empty,  
//  null if the list is empty. 
    public E removeFirst() {
        if(currentSize == 0)
            return null; 
        
        E temp = list[firstPosition];
        list[firstPosition] = null;
        
        if(firstPosition == listSize - 1) {
            firstPosition = 0;
            currentSize--;
            return temp;
        }
        else {
            firstPosition++;
            currentSize--;
            return temp;
        }
    }
    
//  Removes and returns the parameter object obj in last position in list if the list is not empty, 
//  null if the list is empty. 
    public E removeLast() {
        if(currentSize == 0) 
            return null; 
        
        E temp = list[lastPosition];
        list[lastPosition] = null;
        
        if(lastPosition == 0) {
            lastPosition = listSize - 1;
            currentSize--;
            return temp;
        }
        else {
            lastPosition--;
            currentSize--;
            return temp;
        }
    }
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
//  Note that you may have to shift elements to fill in the slot where the deleted element was located.
    public E remove(E obj) {
        boolean found = false;
        int index = firstPosition;
        E temp = null;
        int count = 0; 
        int moves = 0;
        
        if(isEmpty()) 
            return null; 
        
        // Searches list for the given object
        for(int i = 0; i < currentSize; i++) {
            if(((Comparable<E>)obj).compareTo(list[index]) == 0) {
                temp = list[index];
                found = true;
                break;
            }
            moves++;
            index++;
            if(index == listSize) 
                index = 0; 
        }
        // Returns null if object is not found
        if(!found) 
            return null; 
        if(currentSize == 1) { 
            clear(); 
            return temp;
        }
        moves = currentSize - moves;
        count = index; 
        
        // Fills the gap left by the removal
        for(int j = 0; j < moves - 1; j++) {
            if(count != listSize - 1) {
                list[count] = list[count + 1];
                count++;
            }
            else {
                list[count] = list[0];
                count = 0;
            }
        }
        
        if(lastPosition == 0) {
            lastPosition = listSize - 1;
            currentSize--;
        }
        else {
            lastPosition--;
            currentSize--;
        }    
        return temp;
    }
    
//  Returns the first element in the list, null if the list is empty.
//  The list is not modified.
    public E peekFirst() {
        return list[firstPosition];
    }
    
//  Returns the last element in the list, null if the list is empty.
//  The list is not modified.
    public E peekLast() {
        return list[lastPosition];
    }

//  Returns true if the parameter object obj is in the list, false otherwise.
//  The list is not modified.
    public boolean contains(E obj) {
        int index = firstPosition;
        
        for(int i = 0; i < currentSize; i++) {
            if(index == listSize) 
                index = 0; 
            if(((Comparable<E>)obj).compareTo(list[index++]) == 0)
                return true;
        }
        return false;
    }
    
//  Returns the element matching obj if it is in the list, null otherwise.
//  In the case of duplicates, this method returns the element closest to front.
//  The list is not modified.
    public E find(E obj) {       
        int index = firstPosition;
        
        for(int i = 0; i < currentSize; i++) {
            if(((Comparable<E>)obj).compareTo(list[index]) == 0)
                return list[index];  
            index++;
            if(index == listSize) 
                index = 0;
        }
        return null;
    }

//  The list is returned to an empty state.
    public void clear() {
        list = (E[]) new Object[listSize];
        currentSize = 0;
        firstPosition = 0;
        lastPosition = listSize - 1;
    }
    
//  Returns true if the list is empty, otherwise false
    public boolean isEmpty() {
        return currentSize == 0;
    }
    
//  Returns true if the list is full, otherwise false
    public boolean isFull() {
        return currentSize == listSize; 
    }

//  Returns the number of Objects currently in the list.
    public int size() {
        return currentSize;
    }
    
    public Iterator<E> iterator() {
        return new MyIterator();
    }
     
    private class MyIterator implements Iterator<E> {
	int index;
        int front;
        
	MyIterator() {
	    index = 0;
            front = firstPosition;
	}
	public E next() {
	    if (!hasNext())
		throw new NoSuchElementException();
            if(front == listSize) 
                front = 0; 
            index++;
	    return list[front++];
	}
        
	public boolean hasNext() {
	    return index < currentSize;
	}
        
	public void remove() {
	    /* this is an optional operation - we don't support it */
	    throw new UnsupportedOperationException();
	}
    }
}

    
