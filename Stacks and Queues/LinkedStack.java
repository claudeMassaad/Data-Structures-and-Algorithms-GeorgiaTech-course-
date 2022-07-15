import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedStack. It should NOT be circular.
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
public class LinkedStack<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the top of the stack.
     *
     * Must be O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        //Exception:
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null! It cannot be added.");
        }
        //Creating the new node:
        LinkedNode<T> newNode = new LinkedNode<>(data,null);

        //Edge case:
        if (size == 0) {
            head = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;

    }

    /**
     * Removes and returns the data from the top of the stack.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        //Exception:
        if (size == 0) {
            throw new NoSuchElementException("The stack is empty! There is nothing to pop.");
        }
        //Saving the removed element:
        Object removed = head.getData();
        if (size == 1) {
            head = null;
        } else {
            head = head.getNext();
        }
        size--;
        return (T) removed;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The stack is empty! There is nothing to peek.");
        }

        return head.getData();

    }

    /**
     * Returns the head node of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
