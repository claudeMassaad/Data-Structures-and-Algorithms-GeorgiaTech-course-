import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedQueue. It should NOT be circular.
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
public class LinkedQueue<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the back of the queue.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        //Exception:
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null! So you cannot enqueue it!");
        }
        //Creating the new node:
        LinkedNode<T> newNode = new LinkedNode<>(data, null);

        //Edge case:
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            //Adding to the back
            tail.setNext(newNode);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        //Exception:
        if (size == 0) {
            throw new NoSuchElementException("The list is empty! So there is nothing to dequeue!");
        }
        //Saving the removed object
        Object removed = head.getData();

        //Setting the head to next, which will remove the head node from the queue:
        head = head.getNext();

        //Decrementing the size:
        size--;

        //Readjusting for the tail:
        //If the size is now zero, then the tail pointer, which was the deleted element, should also be set to null:
        if (size == 0) {
            tail = null;
        }

        //Returning the removed element:
        return (T) removed;
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        //Exception:
        if (size == 0) {
            throw new NoSuchElementException("The list is empty! So there is nothing to peek!");
        }

        Object next = head.getData();
        return (T) next;
    }

    /**
     * Returns the head node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
