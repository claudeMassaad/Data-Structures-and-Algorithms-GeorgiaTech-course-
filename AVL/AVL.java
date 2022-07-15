import java.util.Collection;
import java.util.NoSuchElementException;

import javax.xml.crypto.Data;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        //Exception 
        if (data == null) {
            throw new IllegalArgumentException("Your data that you're adding is null!");
        }
        for (T entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException("An entry in your data set is null!");
            }

        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you tried to enter is null");
        }
        else if (root == null) {
            AVLNode<T> newNode = new AVLNode<>(data);
            root = newNode;
        }
        else {
            root = addHelper(data, root);
        }
        size++;
    }

    /**
     * Recursive helper function for the add function
     *
     * @param data data to be added.
     * @param node node that is being checked
     * @return returns the node which was deleted
     * @throws java.lang.IllegalArgumentException if data is already in the BST
     */

    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        System.out.println("Enterd addHelper with data: " + data);
        if (node == null) {
            System.out.println("Entered base case");
            //Base Case:
            AVLNode<T> newNode = new AVLNode<>(data);
            newNode.setHeight(0);
            newNode.setBalanceFactor(0);
            System.out.println("New node is: " + newNode);
            return newNode;
        } 
        else if (data.compareTo(node.getData()) > 0) {
            System.out.println("Entered go right after checking with node: " + node);
            //Go right if the data to be entered is greater than the node's data
            node.setRight(addHelper(data, node.getRight()));
        } 
        else if (data.compareTo(node.getData()) < 0) {
            //Go left if the data to be entered is less than the node's data
            node.setLeft(addHelper(data, node.getLeft()));
        } 
        else if (data.equals(node.getData())) {
            //Decrement the size of the BST since we are adding a duplicate
            //In the main add function, the size is always incremented
            size--;
        }  
        //Once done with adding and updating the node's children, update the height of the node
        node.setHeight(Math.max(getNodeHeight(node.getLeft()) + 1 , getNodeHeight(node.getRight()) + 1));
        //Now update the node's balance factor:
        node.setBalanceFactor(getNodeHeight(node.getLeft()) - getNodeHeight(node.getRight()));
        //Check for necessity of rotations
        if (node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1) {
            System.out.println("Checking for rotations with node :" + node);

            if (node.getBalanceFactor() == -2) {

                // Left rotation
                if (node.getRight().getBalanceFactor() == 0 || node.getRight().getBalanceFactor() == -1) {
                    System.out.println("Entered left rotation");
                    AVLNode<T> temp = node.getRight();
                    System.out.println("temp is " + temp);
                    //Do left rotation
                    rotateLeft(node);
                    //Return the right child of the rotated node
                    return temp;
                }

                // Right-Left Rotation
                else if (node.getRight().getBalanceFactor() == 1) {
                    // Change the structure to a structure requiring left rotation
                    // For annotations check the Right-Left Rotation slide
                    AVLNode<T> node85 = node.getRight();
                    AVLNode<T> nodeC = node.getRight().getLeft().getRight();
                    node.setRight(node.getRight().getLeft());
                    node.getRight().setRight(node85);
                    node85.setLeft(nodeC);

                    AVLNode<T> temp = node.getRight();
                    // Now do a right rotation
                    rotateLeft(node);
                    //Return the left child of the rotated node
                    return temp;
                }
            }
             
            else if (node.getBalanceFactor() == 2) {

                // Right rotation
                if (node.getLeft().getBalanceFactor() == 0 || node.getLeft().getBalanceFactor() == 1) {
                    AVLNode<T> temp = node.getLeft();
                    rotateRight(node);
                    //Return the left child of the rotated node
                    return temp;
                }

                // Left-Right rotation
                else if (node.getLeft().getBalanceFactor() == -1) {
                    // Change the structure to a structure requiring right rotation
                    // For annotations check the Left-Right Rotation slide
                    AVLNode<T> node38 = node.getLeft();
                    AVLNode<T> nodeB = node.getLeft().getRight().getLeft();
                    node.setLeft(node.getLeft().getRight());
                    node.getLeft().setLeft(node38);
                    node38.setRight(nodeB);

                    AVLNode<T> temp = node.getLeft();
                    // Now do a right rotation
                    rotateRight(node);
                    //Return the left child of the rotated node
                    return temp;
                }
            }
        }
        System.out.println("Right child of node: " + node + " is: " + node.getRight());
        return node;
    }
    
    /**
     * Recursive helper function for the height function
     *
     * @param node the node for whose height we are searching 
     * @return the height of the root of the tree, -1 if the tree is empty
    */
    private int getNodeHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        else {
            return node.getHeight();
        }
    }

    /**
     * Recursive helper function to do a left rotation and update new subtree's nodes' new heights and balances
     *
     * @param node the node that we apply left rotation to
    */
    private void rotateLeft(AVLNode<T> node) {
        //Middle node becomes root of the new subtree
        AVLNode<T> newSubTreeRoot = node.getRight();
        //Storing the subtree of the middle node
        AVLNode<T> subTreeOfMidNode = node.getRight().getLeft();
        //Update the right child child's left child to the rotated node
        node.getRight().setLeft(node);
        //Set right child of rotated node to left subtree of middle node
        node.setRight(subTreeOfMidNode);
        //Updating the new tree's nodes' heights and balances 
        updateHeightsAndBalances(newSubTreeRoot);
    }

    /**
     * Recursive helper function to do a left rotation and update new subtree's nodes' new heights and balances
     *
     * @param node the node that we apply right rotation to
    */
    private void rotateRight(AVLNode<T> node) {
        //Middle node becomes root of the new subtree
        AVLNode<T> newSubTreeRoot = node.getLeft();
        //Storing the right subtree of the middle node
        AVLNode<T> subTreeOfMidNode = node.getLeft().getRight();
        //Update the left child's right child to the rotated node
        node.getLeft().setRight(node);
        //Set right child of rotated node to right subtree of middle node
        node.setLeft(subTreeOfMidNode);
        //Updating the new tree's nodes' heights and balances 
        updateHeightsAndBalances(newSubTreeRoot);
    }

    /**
     * Recursive helper function to update the heights and balances of the node and all its children
     *
     * @param node the node for which we update the height and balance 
     * @return the height of the root of the tree, -1 if the tree is empty
    */
    private void updateHeightsAndBalances(AVLNode<T> node) {
        if (node == null) {
            return ;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            node.setBalanceFactor(0);
            node.setHeight(0);
            return ;
        }
        updateHeightsAndBalances(node.getLeft());
        updateHeightsAndBalances(node.getRight());
        node.setHeight(Math.max(getNodeHeight(node.getLeft()) + 1  , getNodeHeight(node.getRight()) + 1));
        node.setBalanceFactor(getNodeHeight(node.getLeft()) - getNodeHeight(node.getRight()));
        return ;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        // Exception
        if (data == null) {
            throw new IllegalArgumentException("The data you want to remove is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty.");
        }

        Object removedData;

        if (size == 1) {
            if (data.equals(root.getData())){
                removedData = root.getData();
                root = null;
            }
            else {
                throw new NoSuchElementException("The data is not found in the tree.");
            }
        }
        else {
            AVLNode<T> dummy = new AVLNode<>(null);
            root = removeHelper(data, root, dummy);
            removedData = dummy.getData();
            if (removedData.equals(null)) 
                throw new NoSuchElementException("The data is not found in the tree.");
        }
        size--;
        return (T) removedData;
    }

    /**
     * Recursive helper function for the remove method
     *
     * @param data the data to search for in the tree
     * @param node the node where we are beginning our search from
     * @param dummy the dummy node that we'll use to store the data in case we find it in the tree
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), dummy));
        }
        else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), dummy));
        }
        else if (data.compareTo(node.getData()) == 0){
            //Case 1: node is a leaf
            if (node.getLeft() == null && node.getRight() == null) {
                dummy.setData(node.getData());
                return null;
            }
            //Case 2: Node has one child
            else if (node.getLeft() != null && node.getRight() == null) {
                dummy.setData(node.getData());
                return node.getLeft();
            }
            else if (node.getLeft() == null && node.getRight() != null) {
                dummy.setData(node.getData());
                return node.getRight();
            }
            //Case 3: Node has 2 child
            else {
                dummy.setData(node.getData());
                T successorData = successor(node.getData());
                AVLNode<T> successorDummy = new AVLNode<>(null);
                AVLNode<T> newReturn = removeHelper(successorData, node, successorDummy);
                node.setData(successorDummy.getData());
                return newReturn;
            }
        }

        System.out.println("RC of root after removal: " + node.getRight());
        System.out.println("Height and balance of node " + node +  " before updates: " + node.getHeight() + " , "+ node.getBalanceFactor());

        // After having updated the node, update its heights and balances
        updateHeightsAndBalances(node);

        System.out.println("Height and balance of node " + node +  " after updates: " + node.getHeight() + " , "+ node.getBalanceFactor());

        // Check for necessity of rotations
        if (node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1) {
            System.out.println("Checking for rotations with node :" + node);

            if (node.getBalanceFactor() == -2) {

                // Left rotation
                if (node.getRight().getBalanceFactor() == 0 || node.getRight().getBalanceFactor() == -1) {
                    System.out.println("Entered balance factor of -2 and child with 0 or -1 BF");
                    System.out.println("Entered left rotation");
                    AVLNode<T> temp = node.getRight();
                    System.out.println("temp is " + temp);
                    //Do left rotation
                    rotateLeft(node);
                    //Return the right child of the rotated node
                    return temp;
                }

                // Right-Left Rotation
                else if (node.getRight().getBalanceFactor() == 1) {
                    System.out.println("Entered balance factor of -2 and child with 1 BF");
                    // Change the structure to a structure requiring left rotation
                    // For annotations check the Right-Left Rotation slide
                    AVLNode<T> node85 = node.getRight();
                    AVLNode<T> nodeC = node.getRight().getLeft().getRight();
                    node.setRight(node.getRight().getLeft());
                    node.getRight().setRight(node85);
                    node85.setLeft(nodeC);

                    AVLNode<T> temp = node.getRight();
                    // Now do a right rotation
                    rotateLeft(node);
                    //Return the left child of the rotated node
                    return temp;
                }
            }
             
            else if (node.getBalanceFactor() == 2) {
                // Right rotation
                if (node.getLeft().getBalanceFactor() == 0 || node.getLeft().getBalanceFactor() == 1) {
                    System.out.println("Entered balance factor of 2 and child with 0 or 1 BF");
                    AVLNode<T> temp = node.getLeft();
                    rotateRight(node);
                    System.out.println("temp is: " + temp);
                    //Return the left child of the rotated node
                    return temp;
                }

                // Left-Right rotation
                else if (node.getLeft().getBalanceFactor() == -1) {
                    System.out.println("Entered balance factor of 2 and child with -1 BF");
                    // Change the structure to a structure requiring right rotation
                    // For annotations check the Left-Right Rotation slide
                    AVLNode<T> node38 = node.getLeft();
                    AVLNode<T> nodeB = node.getLeft().getRight().getLeft();
                    node.setLeft(node.getLeft().getRight());
                    node.getLeft().setLeft(node38);
                    node38.setRight(nodeB);

                    AVLNode<T> temp = node.getLeft();
                    // Now do a right rotation
                    rotateRight(node);
                    //Return the left child of the rotated node
                    return temp;
                }
            }
        }
        System.out.println("Right child of node: " + node + " is: " + node.getRight());
        return node;
    }
    
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
       //Exception
       if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        AVLNode<T> temp = getHelper(data, root);
        if (temp == null){
            throw new NoSuchElementException("data is not in the tree");
        }
        else {
            return temp.getData();
        }
    }

    /**
     * Recursive helper function for get.
     * 
     * @param data data we are searching for
     * @param node current node we are searching
     * @return the node containing the data
    */
    private AVLNode<T> getHelper(T data, AVLNode<T> node) {
        if (node == null)
            return null;
        else if (data.compareTo(node.getData()) > 0){
            return getHelper(data, node.getRight());
        }
        else if (data.compareTo(node.getData()) < 0){
            return getHelper(data, node.getLeft());
        }
        else {
            return node;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        //Exception
        if (data == null) {
            throw new IllegalArgumentException("Data you are searching for is null");
        }
        else {
            return containsHelper(data, root);
        }
    }

    /**
     * Contains helper function
     * 
     * @param data the  data we are searching for
     * @param node is the current node being searched
     * @return true if data is in the tree, false otherwise
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null)
            return false;
        else if (data.compareTo(node.getData()) > 0){
            return containsHelper(data, node.getRight());
        }
        else if (data.compareTo(node.getData()) < 0){
            return containsHelper(data, node.getLeft());
        }
        else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        else {
            return heightHelper(root);
        }
    }

    /**
     * Recursive helper function for the height function
     *
     * Returns the height of the root
     * 
     * @return the height of the root of the tree, -1 if the tree is empty
    */
    private int heightHelper(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        int leftheight = heightHelper(node.getLeft());
        int rightheight = heightHelper(node.getRight());
        return Math.max(leftheight+1,rightheight+1);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        dummy.setHeight(0);
        maxDeepestNodeHelper(root , dummy , 0);
        return dummy.getData();
    }
    
    /**
     * Recursive helper function maxDeepestNode
     *
     * Returns the height of the root
     * 
     * @return the height of the root of the tree, -1 if the tree is empty
    */
    private void maxDeepestNodeHelper(AVLNode<T> node , AVLNode<T> dummy , int depth) {
        if (node == null){
            return ;
        }
        if (depth >= dummy.getHeight()) {
            dummy.setData(node.getData());
            dummy.setHeight(depth);
        }
        maxDeepestNodeHelper(node.getLeft() , dummy , depth + 1);
        maxDeepestNodeHelper(node.getRight() , dummy , depth + 1);
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node containing data whose left child is also
     * an ancestor of data.
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        // Exception if root does not exist
        if (root == null) {
            throw new NoSuchElementException("The tree is empy, no successor");
        }
        // Exeception
        if (data == null)   throw new IllegalArgumentException("Your data is null");
        AVLNode<T> dummy = new AVLNode<>(null);
        dummy.setData(root.getData());
        successorHelper(dummy , root , data);
        // If there is no larger data than the one given, i updated its height to be equal to 1
        if (dummy.getHeight() == 1) {
            return null;
        }
        T temp = dummy.getData();
        if (temp == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }  
        return temp;
    }

    /**
     * Recursive helper function to search for the node for which we want to get the successor.
     *
     * @param node the left child of the node for which predecessor we are searching for.
     * @return the successor node.
     */
    private void successorHelper(AVLNode<T> dummy , AVLNode<T> node , T data) {
        // If data is not in the tree
        if (node == null) {
            dummy.setData(null);
            return ;
        }
        // Updating the value of the potential successor if it is an ancestor of the node
        if (node.getData().compareTo(data) > 0) {
            dummy.setData(node.getData());
        }
        if (data.compareTo(node.getData()) > 0){
            successorHelper(dummy , node.getRight() , data);
        }
        else if (data.compareTo(node.getData()) < 0){
            successorHelper(dummy , node.getLeft() , data);
        }
        else {
            decideSuccessor(dummy , node , data);
        }
    }
    /**
     * Recursive helper function to get the successor.
     *
     * @param node the left child of the node for which predecessor we are searching for.
     * @return the successor node.
     */
    private void decideSuccessor(AVLNode<T> dummy , AVLNode<T> node , T data) {
        // First Case: right subtree is not null
        if (node.getRight() != null) {
            dummy.setData(node.getRight().getData());
            getSuccessorRight(dummy , node.getRight());
        }
        // Second Case: right subtree is null
        else {
            if (dummy.getData().compareTo(data) < 0) {
                dummy.setHeight(1);
            }
            return ;
        }
    }

    /**
     * Recursive helper function to get the successor.
     *
     * @param node the left child of the node for which predecessor we are searching for.
     * @return the successor node.
     */
    private void getSuccessorRight(AVLNode<T> dummy , AVLNode<T> node) {
        // Base Case 
        if (node.getLeft() == null) {
            return ;
        }
        else {
            dummy.setData(node.getLeft().getData());
            getSuccessorRight(dummy , node.getLeft());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
