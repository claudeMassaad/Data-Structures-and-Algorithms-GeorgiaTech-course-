import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
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
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
/**/    private Comparable[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
/**/         backingArray = new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null || data.isEmpty())
            throw new IllegalArgumentException("Your set of data is empty");
        
/**/       backingArray =  (new Comparable[INITIAL_CAPACITY]);

        if (2*data.size()+1 != INITIAL_CAPACITY)
/**/             backingArray =  (new Comparable[2*data.size()+1]);
        
        for (int i = 0 ; i < data.size() ; i++) {
            if (data.get(i) == null)
                throw new IllegalArgumentException("An element in your data set is null");
            backingArray[i+1] = data.get(i);
            size++;
        }
        int index = size/2;        
        while (index >=1) {
            heapifyHelper(index);
            index--;
        }
    }
    
    /**
     * Helper function which "heapifies" the heap at a particular node
     *
     * @param index the index where we want to perform heapify
     */
    private void heapifyHelper(int index) {
        while ( 2*index <= size) {
            if (2*index + 1 <= size) {
                if (backingArray[index].compareTo(backingArray[2*index]) < 0 || backingArray[index].compareTo(backingArray[2*index+1]) < 0){
                    if ( ( backingArray[2*index + 1].compareTo(backingArray[2*index]) ) < 0 ) {
                        Object temp = backingArray[2*index];
                        backingArray[2*index] = backingArray[index];
                        backingArray[index] = /*(T)*/ (Comparable) temp;
                        index = index*2;
                    }
                    else {
                        Object temp = backingArray[2*index+1];
                        backingArray[2*index+1] = backingArray[index];
                        backingArray[index] = /*(T)*/ (Comparable) temp;
                        index = index*2 +1;
                    }
                }
            }
            else {
                if (backingArray[index].compareTo(backingArray[2*index]) < 0) {
                    Object temp = backingArray[index];
                    backingArray[index] = backingArray[2*index];
                    backingArray[2*index] = /*(T)*/ (Comparable) temp;
                }
                index = index*2;
            }
        }   
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        //Exception
        if (data == null) 
            throw new IllegalArgumentException("The data you entered is null");

        if (size == backingArray.length -1) {
            Comparable[] tempBackingArray = new Comparable[2*backingArray.length];
            for (int i =1 ; i<backingArray.length ; i++) {
                tempBackingArray[i] = backingArray[i];
            }
            backingArray = /*(T[])*/ tempBackingArray;
        }
        
        int index = size + 1;
        backingArray[index] = data;
        int parentIndex = index/2;
        while (parentIndex >=1 && backingArray[parentIndex].compareTo(backingArray[index]) < 0) {
            //switching
            Object temp = backingArray[index];
            backingArray[index] = backingArray[parentIndex];
            backingArray[parentIndex] =/*(T)*/ (Comparable) temp;
            
            index = parentIndex;
            parentIndex = parentIndex/2;
        }
        size++;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public Comparable remove() {
        int index = size;
/**/         Comparable data = backingArray[1];
        backingArray[1] = backingArray[index];
        backingArray[index] = null;
        index = 1;
        size--;
        heapifyHelper(index);
        return data;
    }

    

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
/**/     public Comparable getMax() {
        if (size == 0) {
            return null;
            //throw new NoSuchElementException("The heap is empty, no Max.");
        }
        else    
            return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
/**/     public Comparable[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
