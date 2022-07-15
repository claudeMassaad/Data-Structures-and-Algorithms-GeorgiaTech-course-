import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexeable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        Iterator<T> itr = data.iterator();
        while (itr.hasNext()) {
            add(((T)itr.next()));
        }

        // for (T currdata : data) {
        //     add(currdata);
        // }

    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        //Exception
        if (data == null) {
            throw new IllegalArgumentException("The data you are adding is null");
        }
        else if (root == null) {
            BSTNode<T> newNode = new BSTNode<>(data);
            root = newNode;
        }
        else {
            addHelper(data, root);
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

    private BSTNode<T> addHelper(T data, BSTNode<T> node) {
        // if (data.compareTo(node.getData())<0) {
        //     if (node.getLeft()==null) {
        //         BSTNode<T> newNode = new BSTNode<>(data);
        //         node.setLeft(newNode);
        //         size++;
        //     }
        //     else {
        //         addHelper(data, node.getLeft());
        //     }
        // }  
        // else if (data.compareTo(node.getData())>0) {
        //     if (node.getRight()==null) {
        //         BSTNode<T> newNode = new BSTNode<>(data);
        //         node.setRight(newNode);
        //         size++;
        //     }
        //     else {
        //         addHelper(data, node.getRight());
        //     }
        // }
        // else {
        //     throw new IllegalArgumentException("Data already in the BST");
        // }
        //Uses pointer reinforcement to add the new node
        if (node == null) {
            //Base Case:
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            //Go right if the data to be entered is greater than the node's data
            node.setRight(addHelper(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            //Go left if the data to be entered is less than the node's data
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (data.equals(node.getData())) {
            //Decrement the size of the BST since we are adding a duplicate
            //In the main add function, the size is always incremented
            size--;
        }  
        return node;
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
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        //Exceptions:
        if (data == null) {
            throw new IllegalArgumentException("The data you entered is null! Cannot add null to the BST.");
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
            BSTNode<T> dummy = new BSTNode<>(null);
            removeHelper(data, root, dummy);
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
     * 
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> node, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("the data is not in the tree");
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
                Object temp = getPredecessor(node.getLeft());
                removeHelper((T) temp, node, dummy);
                dummy.setData(node.getData());
                node.setData((T) temp);
                return node;
            }
        }
        return node;
    }

    /**
     * Recursive helper function to get the predecessor.
     *
     * @param node the left child of the node for which predecessor we are searching for.
     * @return the predecessor node.
     */
    private T getPredecessor(BSTNode<T> node) {
        if (node.getRight() == null) {
            return (T) node.getData();
        }
        else {
            return getPredecessor(node.getRight());
        }
    }

    /**
     * Recursive helper function for get.
     * 
     * @param data data we are searching for
     * @param node current node we are searching
     * @return the node containing the data
    */
    private BSTNode<T> getHelper(T data, BSTNode<T> node) {
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
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
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
        BSTNode<T> temp = getHelper(data, root);
        if (temp.getData().equals(null)){
            throw new NoSuchElementException("data is not in the tree");
        }
        else {
            return temp.getData();
        }
    }



    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        //Exception
        if (data == null) {
            throw new IllegalArgumentException("data is null");
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
    private boolean containsHelper(T data, BSTNode<T> node) {
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
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * Recursive helper function for preorder
     *
     * @param node the node where we are looking.
     * @param list the preorder traversal of the tree.
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return ;
        }
        else {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * Recursive helper function for inorder
     *
     * @param node the node where we are looking.
     * @param list the preorder traversal of the tree.
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return ;
        }
        else {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * Recursive helper function for postorder
     *
     * @param node the node where we are looking.
     * @param list the preorder traversal of the tree.
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        postorderHelper(node.getLeft(), list);
        postorderHelper(node.getRight(), list);
        list.add(node.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        // Queue<BSTNode<T>> queue = new LinkedList<>();
        // List<T> list = new ArrayList<>();
        // if (size == 0) {
        //     return list;
        // }
        // queue.add(root);
        // while (queue.size()!=0) {
        //     if (queue.peek().getLeft() != null)
        //         queue.add(queue.peek().getLeft());
        //     if (queue.peek().getRight() != null)
        //         queue.add(queue.peek().getRight());
        //     list.add(queue.remove().getData());
        // }
        // return list;

        BSTNode<T> curr;
        List<BSTNode<T>> list1 = new ArrayList<>();
        List<T> list2 = new ArrayList<>();
        if (root == null) {
            return list2;
        }
        int i = 0; 
        list1.add(root);
        while ( i < list1.size()) {
            curr = list1.get(i);
            if (!(curr.getLeft() == null)) {
                list1.add(curr.getLeft());
            }
            if (!(curr.getRight() == null)) {
                list1.add(curr.getRight());
            }
            i++;
        }
        for (BSTNode<T> temp : list1) {
            list2.add(temp.getData());
        }
        return list2;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
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
    private int heightHelper(BSTNode<T> node) {
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
     *
     * Must be O(1).
     */
    public void clear() {
        //Reintializes the BST
        root = null;
        size = 0;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     * 
     * This method must be implemented recursively.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(BSTNode<T> treeRoot) {
        if (treeRoot == null) 
            return true;
        else    
            return isBSTHelper(treeRoot);
    }

    /**
     * Recursive helper function for isBST
     *
     * @param treeRoot the node we are checking
     */
    private static <T extends Comparable<? super T>> boolean isBSTHelper(BSTNode<T> node) {
        if (node == null)
            return true;
        if (node.getLeft() != null && node.getData().compareTo(node.getLeft().getData()) < 0 || node.getRight() != null && node.getData().compareTo(node.getRight().getData()) > 0) 
            return false;
        return isBSTHelper(node.getLeft()) && isBSTHelper(node.getRight());
    }
    

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
