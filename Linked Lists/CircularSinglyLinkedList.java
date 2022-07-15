import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //Exceptions:
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you entered is negative!");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index you entered is greater than the size of the list!");
        }

        //Edge cases:
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } /* General case: */ else {
            //Setting the pointer
            CircularSinglyLinkedListNode<T> curr = head;

            //Looping through the list
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }

            //Creating the new node and adding it to the list:
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, curr.getNext());
            curr.setNext(newNode);
            size++;
        }

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        //Exception:
        if (data == null) {
            throw new IllegalArgumentException("The data you want to add is null!");
        }

        //Case:  Size is 0
        if (size == 0) {
            //Creating a new node
            CircularSinglyLinkedListNode<T> newNode =  new CircularSinglyLinkedListNode<>(data);

            //Adding the new node to the list
            head = newNode;
            newNode.setNext(head);

        } else {
            //Copying head into the newNode
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(head.getData());

            //Setting the data of the new element in head
            head.setData(data);

            //Setting the next of the the newNode
            newNode.setNext(head.getNext());

            //Setting the next of the new head
            head.setNext(newNode);
        }
        size++;


    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        //Exception:
        if (data == null) {
            throw new IllegalArgumentException("The data you want to add is null!");
        }

        //Case:  Size is 0
        if (size == 0) {
            //Creating a new node
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);

            //Adding the new node to the list
            head = newNode;
            newNode.setNext(head);
        } else {
            //Copying head into the newNode
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(head.getData());

            //Setting the data of the new element in head
            head.setData(data);

            //Setting the next of the the newNode
            newNode.setNext(head.getNext());

            //Setting the next of the new head
            head.setNext(newNode);

            //Moving the new element to the back of the list
            head = head.getNext();
        }
        size++;
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index you entered is negative!");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The index you entered is greater than the size of the list!");
        }
        //Edge cases:
        if (index == 0) {
            return removeFromFront();

        } else if (index == size) {

            return removeFromBack();

        } /* General */ else {

            //Creating the pointer:
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }

            //Saving the removed element:
            Object removedElement = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return (T) removedElement;
        }

    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        //Exception:
        if (size == 0) {
            throw new NoSuchElementException("Nothing to remove! The list is empty.");
        }

        //Saving the object which will be removed
        Object removedElement = head.getData();

        if (size == 1) {
            head = null;
        } else {
            //Setting the data of head as the data of next element
            head.setData(head.getNext().getData());

            //Setting the next of head as the next of next
            head.setNext(head.getNext().getNext());

            //Eventually, the removed node will be garbage collected
        }
        size--;

        //Returning the removed element
        return (T) removedElement;

    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        //Exception:
        if (size == 0) {
            throw new NoSuchElementException("The list is empty! Nothing to remove.");
        } else if (size == 1) {
            //Saving the removed element
            Object removedElement = head.getData();

            //Removing the head
            head = null;
            size--;
            return (T) removedElement;
        } else {
            //Creating a node to loop through the list:
            CircularSinglyLinkedListNode<T> curr = head;

            //Looping through the list to find the last element:
            while (curr.getNext().getNext() != head) {
                curr = curr.getNext();
            }


            //Saving the deleted element
            Object removedElement = curr.getNext().getData();
            curr.setNext(head);
            size--;
            return (T) removedElement;
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        //Exceptions:
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is negative!");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater than the size of the list!");
        }

        //Creating the pointer:
        CircularSinglyLinkedListNode<T> curr = head;

        //Looping through the list:
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }

        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return 0 == size;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        head = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        //Exception:
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null!");
        }
        if (size == 0) {
            //Had to add this since I got a NullPointerException when I ran the method on an empty list.
            throw new NoSuchElementException("The list is empty! There is nothing to look for.");
        }

        //Creating the pointers:
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> removeNext = null;

        //Looping through the list
        while (curr.getNext() != head) {
            if (curr.getNext().getData().equals(data)) {
                removeNext = curr;
            }
            curr = curr.getNext();
        }

        if (removeNext == null) {
            if (head.getData().equals(data)) {
                return removeFromFront();
            } else {
                throw new NoSuchElementException("The data you entered is not in the list!");
            }
        } else {
            //Saving the removed element
            Object removedElement = removeNext.getNext().getData();

            //Deleting the element
            removeNext.setNext(removeNext.getNext().getNext());
            size--;

            return (T) removedElement;
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        //Initialzing the array which will be returned
        Object[] array = new Object[size];

        //Creating the pointer
        CircularSinglyLinkedListNode<T> curr = head;

        //Adding the elements to the array
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }

        return (T[]) array;

    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
