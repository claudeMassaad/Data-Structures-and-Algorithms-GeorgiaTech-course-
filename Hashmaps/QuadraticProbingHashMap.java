import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import apple.laf.JRSUIUtils.Tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Your implementation of a QuadraticProbingHashMap.
 *
 * @author Gino Doumit
 * @version 1.0
 * @userid gdoumit3
 * @GTID 903665016
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class QuadraticProbingHashMap<K, V> {

    /**
     * The initial capacity of the QuadraticProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the QuadraticProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private QuadraticProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new QuadraticProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public QuadraticProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new QuadraticProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public QuadraticProbingHashMap(int initialCapacity) {
        this.table = new QuadraticProbingMapEntry[initialCapacity];
        this.size = 0;
    }


    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * You must also resize when there are not any valid spots to add a
     * (key, value) pair in the HashMap after checking table.length spots.
     * There is more information regarding this case in the assignment PDF.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        //Exception
        if (key == null || value == null) 
            throw new IllegalArgumentException("Key or value is null");
        if ( ( ( (double) size+1.0 )/ this.table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(2*this.table.length +1);
        }
        //hashing the key using hashCode()
        int hashCode = key.hashCode();
        //System.out.println("Hashcode of " + key + " is " + hashCode);
        //Mod by the table length
        int index = hashCode % table.length;
        //System.out.println("Index of " + hashCode + "is" + index);
        //take the absolute value
        index = Math.abs(index);

        //index of an we find removed
        int indexOfRemoved = -1;

        //number of probes
        int probe = 0;

        //boolean to check if the pair was added
        boolean added = false;

        while (added == false) {
            while (probe < table.length) {
                //we reach a null entry 
                if (table[index] == null) {
                    //if prior we didnt find a removed entry
                    if (indexOfRemoved == -1) {
                        table[index] = new QuadraticProbingMapEntry<>(key, value);
                        size++;
                        added = true;
                        // System.out.println("ENTRED A NULL CASE WITH INDEX 0");
                        // System.out.println(Arrays.toString(table));
                        return null;
                    }
                    //if prior we found a removed entry
                    else {
                        table[indexOfRemoved] = new QuadraticProbingMapEntry<>(key, value);
                        added = true;
                        return null;

                    }
                }
                //we find the first removed entry and save its index
                else if (table[index].isRemoved() && indexOfRemoved == -1) {
                    indexOfRemoved = index;
                }
                //if the key of the table is the same as ours, we set the value to our value and return old value
                if (table[index].getKey() == key) {
                    V temp = table[index].getValue();
                    table[index].setValue(value);
                    added = true;
                    return temp;
                }
                
                probe++;
                index = (hashCode + probe*probe) % table.length;
                index = Math.abs(index);
            }

            //if new pair was not added, we resize the table and retry to add new pair
            resizeBackingTable(2*this.table.length +1);
            //hashing the key using hashCode()
           // System.out.println("Entered the new phase");
            hashCode = key.hashCode();
            //System.out.println("NEW HASHCODE: " + hashCode);
            //Mod by the table length
            index = hashCode % table.length;
            //System.out.println("NEW INDEX: " + index);
            //take the absolute value
            index = Math.abs(index);
            probe = 0;
        }
        return table[1].getValue();     
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V remove(K key) {
        System.out.println("ENTERED REMOVED");
        //Exception if key is null
        if (key == null) {
            throw new IllegalArgumentException("The key you entered is null");
        }
        //hashing the key using hashCode()
        int hashCode = key.hashCode();
        //Mod by the table length
        int index = hashCode % table.length;
        //take the absolute value
        index = Math.abs(index);

        int probe = 0;

        while (probe < table.length) {
            //the key is at index, return the value and set removed to true
            if (table[index] != null) {
                //the entry at that key was already removed and so in not in table anymore
                if (table[index].isRemoved() == true && (int) table[index].getKey() == (int) key) {
                    throw new NoSuchElementException("The key is not in the map");
                }
                else if ( table[index].getKey() == key) {
                    V temp = table[index].getValue();
                    table[index].setRemoved(true);
                    size--;
                    return temp;
                }
            }
            //We arrive at a nullspot 
            else if (table[index] == null) {
                throw new NoSuchElementException("The key is not in the map");
            }

            probe++;
            index = (hashCode + probe*probe) % table.length;
            index = Math.abs(index);
        }
        return table[index].getValue();
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        //Exception if key is null
        if (key == null) {
            throw new IllegalArgumentException("The key you entered is null");
        }
        //hashing the key using hashCode()
        int hashCode = key.hashCode();
        //Mod by the table length
        int index = hashCode % table.length;
        //take the absolute value
        index = Math.abs(index);

        int probe = 0;

        while (probe < table.length) {
            //the key is at index, return the value and set removed to true
            if ((int) table[index].getKey() == (int) key) {
                if (table[index].isRemoved() == false) {
                    return table[index].getValue();
                }
                else {
                    throw new NoSuchElementException("The key is not in the map");
                }
            }
            probe++;
            index = (hashCode + probe*probe) % table.length;
            index = Math.abs(index);
        }
        throw new NoSuchElementException("The key is not in the map");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Your key is null");
        }
        for (QuadraticProbingMapEntry entry : table) {
            if (entry != null) {
                if (entry.getKey() == key) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> s = new HashSet<K>();
        for (QuadraticProbingMapEntry entry : table) {
            if (entry != null) {
                if (entry.isRemoved() == false) {
                    s.add((K) entry.getKey());
                }
            }
        }
        return s;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> s = new ArrayList<>();
        for (QuadraticProbingMapEntry entry : table) {
            if (entry != null) {
                if (entry.isRemoved() == false) {
                    s.add((V) entry.getValue());
                }
            }
        }
        return (List) s;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * Note: This method does not have to handle the case where the new length
     * results in collisions that cannot be resolved without resizing again. It
     * also does not have to handle the case where size = 0, and length = 0 is
     * passed into the function.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        //Exception
        if (length < size)
            throw new IllegalArgumentException("Length is smaller than the number of items in the array");
        
        //Creating the new table
        QuadraticProbingMapEntry<K, V>[] newTable = new QuadraticProbingMapEntry[length];
        //Iterating over the old array
        for (QuadraticProbingMapEntry<K, V> entry : table) {
            if (entry != null) {
                //hashing the key using hashCode()
                int hashCode = entry.getKey().hashCode();
                //System.out.println("Hashcode of " + key + " is " + hashCode);
                //Mod by the table length
                int index = hashCode % newTable.length;
                //take the absolute value
                index = Math.abs(index);
                //number of probes
                int probe = 0;

                //boolean to check if the pair was added
                boolean added = false;

                while (added == false) {
                    while (probe < newTable.length) {
                        //we reach a null entry 
                        if (newTable[index] == null) {
                            newTable[index] = new QuadraticProbingMapEntry<>(entry.getKey(), entry.getValue());
                            added = true;
                            break;
                        }
                        probe++;
                        index = (hashCode + probe*probe) % newTable.length;
                        index = Math.abs(index);
                    }

                    if (added == false) {
                        //if new pair was not added, we resize the table and retry to add new pair
                        resizeBackingTable(2*this.table.length +1);
                        //hashing the key using hashCode()
                        hashCode = entry.getKey().hashCode();
                        //Mod by the table length
                        index = hashCode % table.length;
                        //take the absolute value
                        index = Math.abs(index);
                        probe = 0;
                    }
                }
            }
        }
        table = newTable; 
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.table = new QuadraticProbingMapEntry[INITIAL_CAPACITY];
        this.size = 0;    
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public QuadraticProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
