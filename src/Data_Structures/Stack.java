
package Data_Structures;

import java.util.Iterator;

public class Stack<E> {
    
    private int size;
    private LinkedList<E> list;
    
    public Stack() {
        this.size = 0;
        this.list = new LinkedList<>();
    }
    
    // inserts the object obj into the stack
    public void push(E obj) {
        list.addFirst(obj);
        size++;
    }
    
    // pops and returns the element on the top of the stack    
    public E pop() {
        E temp = list.removeFirst();
        if(temp == null)
            return null;
        size--;
        return temp;
    }
    
    // returns the number of elements currently in the stack
    public int size() {
        return size;
    }
    
    // return true if the stack is empty, otherwise false
    public boolean isEmpty() {
        return size == 0;
    }
    
    // returns but does not remove the element on the top of the stack    
    public E peek() {
        return list.peekLast();
    }
     
    // returns true if the object obj is in the stack,
    // otherwise false   
    public boolean contains(E obj) {
        return list.contains(obj);
    }
    
    // returns the stack to an empty state    
    public void makeEmpty() {
        list.clear();
        this.size = 0;
    }
    
    // removes the Object obj if it is in the stack and
    // returns true, otherwise returns false.
    public boolean remove(E obj) {
        if(list.remove(obj) != null) {
            size--;
            return true;
        }
        return false;
    }
    
    // returns a iterator of the elements in the stack.  The elements
    // must be in the same sequence as pop() would return them.    
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
