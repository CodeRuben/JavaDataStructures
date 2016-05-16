package Data_Structures;

import java.util.Iterator;

/**
 * Generic Hash Table data structure. Hash table has methods
 * which support insertion, removal and search operations.
 * 
 * @author Ruben Ramirez   
 * @param <K> the type of keys in the list
 * @param <V> the type of values in the list
 */
  
public class HashTable<K, V> implements Iterable<K> {
	
    private LinkedList<HashNode<K, V>>[] table;
    private int tableSize;
    private int numElements;
    
    public HashTable(int maxSize) {
        
        this.tableSize = (int) (maxSize * 1.3f);
        this.table = new LinkedList[tableSize];
        this.numElements = 0;
        
        // Create a linked list in each array cell of the array
        for(int i = 0; i < tableSize; i++)
            table[i] = new LinkedList<HashNode<K, V>>();
    }

    /**
     * Adds an object containing a key and a value to the table.
     * @param key the key value to be added to the hash.
     * @param value the value to be added to the hash.
     * @return true if the object is successfully added.
     */
    public boolean add(K key, V value) {

        // Create a larger table if load factor is too large
        if(loadFactor() >= .75)
            resize(tableSize * 2);

        int index = getIndex(key);

        // Check for a duplicate key, delete if one is found
        if(table[index].contains(new HashNode<K, V>(key, null))) {
            table[index].remove(new HashNode<K, V>(key, null));
                this.numElements--;
        }

        // Insert HashNode into table
        table[index].addFirst(new HashNode<>(key, value));
        this.numElements++;
        return true;
    }

    /**
     * Returns an index value based on a key value.
     * @param key, the key that is to be hashed and modded.
     * @return the index that was generated from the hash code.
     */
    public int getIndex(K key) {
        // Get hash value, make it positive, mod on table size
        return (key.hashCode() & 0x7FFFFFFF) % tableSize;
}

    /**
     * Removes an object from the hash table if it is in the table.
     * @param key, the key that is going to be searched for.
     * @return true if an object got removed, false otherwise.
     */
    public boolean remove(K key) {
        int index = getIndex(key);
        HashNode<K,V> tmp = table[index].remove(new HashNode<K, V>(key, null));

        if(tmp == null)
            return false;
        this.numElements--;
        return true;
    }

    /**
     * Returns a value associated with a given key.
     * @param key, the key associated with the value.
     * @return the value associated with the key, null
     * if there is no element with the given key.
     */
    public V getValue(K key) {
        int index = getIndex(key);
        HashNode<K,V> tmp = table[index].get(new HashNode<K, V>(key, null));

        if(tmp == null)
            return null;
        return tmp.data;
    }

    /**
     * Returns the number of Objects currently in the hash table.
     * @return the number of Objects currently in the hash table.
     */
    public int size() {
        return this.numElements;
    }

    /**
     * Test whether the hash table is empty.
     * @return true if the list is empty, otherwise false
     */
    public boolean isEmpty() {
        return this.numElements == 0;
    }

    /**
     * Returns the current load factor value.
     * Load factor = number of elements / table size
     * @return the current value of the load factor.
     */
    public double loadFactor() {
        return (double) numElements / tableSize;
    }

    /**
     * Resizes the hash table when the load factor exceeds 0.75.
     * Every existing element gets rehashed into the new table.
     * @param newSize, the size of the new hash table.
     */
    public void resize(int newSize) {

        LinkedList<HashNode<K, V>>[] newList = new LinkedList[newSize];
        HashNode<K,V> temp = null;
        int index, linkedListSize;
        this.tableSize = newSize;

        // Create a linked list in each array cell of the new array
        for(int i = 0; i < newSize; i++)
            newList[i] = new LinkedList<HashNode<K, V>>(); 

        // Rehash every element in the table
        for(int i = 0; i < tableSize / 2; i++) {
            linkedListSize = table[i].size();

            for(int j = 0; j < linkedListSize; j++) {
                temp = table[i].removeFirst();
                index = getIndex(temp.key);
                newList[index].addFirst(temp);
            }
        }
        this.table = newList;
    }

    /**
     *  Returns an iterator of the keys in the hash table, in
     *  ascending sorted order.
     *  @return an iterator that traverses the keys in the table.
 */
    public Iterator<K> iterator() {
        return new KeyIterator<K>();
    }

    /**
     *  Returns an iterator of the keys in the hash table, in
     *  ascending sorted order.
     *  @return an iterator that traverses the keys in the table.
 */
    public Iterator<K> keys() {
        return new KeyIterator<K>();
    }

    /**
     *  Returns an Iterator of the values in the hash table. The 
     *  order of the values must match the order of the keys.
     *  @return an iterator that traverses the values in the table.
 */
    public Iterator<V> values() {
        return new ValueIterator<V>();
    }

    /**
     * ValueIterator inner class that iterates through the values
     * stored in the hash table. 
     * 
     * Extends the abstract IteratorHelper class, and implements 
     * the next() method so the values associated with the key 
     * get returned.
     */
    private class ValueIterator<V> extends IteratorHelper<V> {
        public ValueIterator() {
            super();
        }
        public V next() {
            return (V) nodeArray[index++].data;
        }
    }

    /**
     * KeyIterator inner class that iterates through the key values
     * in the hash table. 
     * 
     * Extends the abstract IteratorHelper class, and implements 
     * the next() method so the key values get returned.
     */
    private class KeyIterator<K> extends IteratorHelper<K> {
        public KeyIterator() {
            super();
        }
        public K next() {
            return (K) nodeArray[index++].key;
        }
    }

    // Abstract IteratorHelper super class that will be used by the 
    // key() and value() iterators. 
    private abstract class IteratorHelper<E> implements Iterator<E> {
        protected HashNode<K, V>[] nodeArray;
        protected int index, j;

        public IteratorHelper() {
            nodeArray = new HashNode[numElements];
            index = 0;
            j = 0;

            // Add all the HashNodes of the table to an array
            for(int i = 0; i < tableSize; i++) {
                for(HashNode<K,V> node : table[i]) 
                    nodeArray[j++] = node;
            }
        }

        public boolean hasNext() {
            return index < numElements;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // HashNode inner class. HashNodes hold a key and value pair.
    private class HashNode<K,V> implements Comparable<HashNode<K,V>> {
        private K key;
        private V data;

        public HashNode(K key, V value) {
            this.key = key;
            this.data = value;
        }
        // HashNodes are compared based on their key value
        public int compareTo(HashNode<K,V> obj) {
            return (((Comparable<K>)key).compareTo(obj.key));
        }
    }
}
