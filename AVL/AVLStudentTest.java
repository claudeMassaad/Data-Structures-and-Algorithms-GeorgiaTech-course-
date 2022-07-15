import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Not 100% comprehensive: doesn't include TA Junits.
 *
 * @author Chinmayi Kompella
 * @version 1.0
 */

public class AVLStudentTest {

    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotateSimple() {
        tree.clear();
        tree.add(0);
        tree.add(1);
        tree.add(2);

        /*                     *Left Rotate*
                0
                 \
                  1           ->              1
                   \                         / \
                    2                       0   2
         */

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        /*my test*/ System.out.println(root );
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node0 = root.getLeft();
        assertEquals((Integer) 0, node0.getData());
        assertEquals(0, node0.getHeight());
        assertEquals(0, node0.getBalanceFactor());

        AVLNode<Integer> node2 = root.getRight();
        assertEquals((Integer) 2, node2.getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotate1() {
        tree.clear();
        tree.add(5);
        tree.add(2);
        tree.add(4);

        /*                  *Left-Right Rotate: left rotate on 2 and right rotate on 5*
                5
               /
              2             ->              4
               \                           / \
                4                         2   5
         */

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node5 = root.getRight();
        assertEquals((Integer) 5, node5.getData());
        assertEquals(0, node5.getHeight());
        assertEquals(0, node5.getBalanceFactor());

        AVLNode<Integer> node2 = root.getLeft();
        assertEquals((Integer) 2, node2.getData());
        assertEquals(0, node2.getHeight());
        assertEquals(0, node2.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotate2() {
        tree.clear();
        assertEquals(0, tree.size());
        tree.add(50);
        tree.add(60);
        tree.add(40);
        tree.add(35);
        tree.add(41);
        tree.add(42);
        assertEquals(6, tree.size());

        /*              *Left-Right Rotation: left rotate on 40 and right rotate on 50*

                50                                               41
              /    \                                           /    \
            40      60            ->                          40     50
           /  \                                              /      /  \
          35   41                                           35     42   60
                 \
                  42
         */
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 41, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node40 = root.getLeft();
        assertEquals((Integer) 40, node40.getData());
        assertEquals(1, node40.getHeight());
        assertEquals(1, node40.getBalanceFactor());

        AVLNode<Integer> node50 = root.getRight();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(1, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());

        AVLNode<Integer> node35 = node40.getLeft();
        assertEquals((Integer) 35, node35.getData());
        assertEquals(0, node35.getHeight());
        assertEquals(0, node35.getBalanceFactor());

        AVLNode<Integer> node42 = node50.getLeft();
        assertEquals((Integer) 42, node42.getData());
        assertEquals(0, node42.getHeight());
        assertEquals(0, node42.getBalanceFactor());

        AVLNode<Integer> node60 = node50.getRight();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotate() {
        tree.clear();
        assertEquals(0, tree.size());
        tree.add(44);
        tree.add(32);
        assertEquals(2, tree.size());
        /*
            44
           /
          32
         */
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer)44, root.getData());
        assertEquals(1, root.getBalanceFactor());
        assertEquals(1, root.
                getHeight());
        tree.add(50);
        /*
            44
           /  \
          32   50
         */
        assertEquals(0, root.getBalanceFactor());
        tree.add(17);
        tree.add(48);
        tree.add(68);
        tree.add(62);
        tree.add(78);
        /*
            44
          /    \
         32    50
        /     /  \
       17    48  68
                /  \
               62  78
         */
        assertEquals(3,root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals(8, tree.size());
        tree.add(54);

        /*      *Right-Left Rotate: right rotate on 68 and then left rotate on 50*

            44                                      44
          /    \                                  /    \
         32    50                                32    62
        /     /  \            ->                /     /  \
       17    48  68                            17    50  68
                /  \                                /  \   \
               62  78                              48  54  78
              /
             54
         */
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        AVLNode<Integer> node32 = root.getLeft();
        assertEquals((Integer) 32, node32.getData());
        assertEquals(1, node32.getHeight());
        assertEquals(1, node32.getBalanceFactor());

        AVLNode<Integer> node62 = root.getRight();
        assertEquals((Integer) 62, node62.getData());
        assertEquals(2, node62.getHeight());
        assertEquals(0, node62.getBalanceFactor());

        AVLNode<Integer> node17 = node32.getLeft();
        assertEquals((Integer) 17, node17.getData());

        AVLNode<Integer> node50 = node62.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(1, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());

        AVLNode<Integer> node68 = node62.getRight();
        assertEquals((Integer) 68, node68.getData());
        assertEquals(1, node68.getHeight());
        assertEquals(-1, node68.getBalanceFactor());

        AVLNode<Integer> node48 = node50.getLeft();
        assertEquals((Integer) 48, node48.getData());

        AVLNode<Integer> node54 = node50.getRight();
        assertEquals((Integer) 54, node54.getData());

        AVLNode<Integer> node78 = node68.getRight();
        assertEquals((Integer) 78, node78.getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAddDuplicate() {
        testAddLeftRotateSimple();
        /*
                             1
                           /   \
                          0     2
         */
        int previousSize = tree.size();
        tree.add(1);
        tree.add(0);
        tree.add(2);
        assertEquals(previousSize, tree.size());
    }

    @Test
    public void testRemoveRightRotateNoChild() {
        Integer temp = 70;
        tree.add(60);
        tree.add(40);
        tree.add(temp);
        tree.add(30);
        tree.add(50);
        //assuming tree built properly
        /*
                                           60
                                          /  \
                                         40  70
                                        /  \
                                       30  50
         */

           // my tests
        System.out.println("THe root is :" + tree.getRoot());
        System.out.println("LC1 , RC1 :" + tree.getRoot().getLeft() + " , " + tree.getRoot().getRight());
        System.out.println("LC2 , RC2 :" + tree.getRoot().getLeft().getLeft() + " , " + tree.getRoot().getLeft().getRight());

        assertEquals(temp,  tree.remove(70));
        /*                          *right rotation on 60*
                                         40
                                        /  \
                                       30  60
                                           /
                                          50
         */
        assertEquals(4, tree.size());
        assertFalse(tree.contains(temp));

        // my tests
        System.out.println("AFter removal:");
        System.out.println("THe root is :" + tree.getRoot());
        System.out.println("LC1 , RC1 :" + tree.getRoot().getLeft() + " , " + tree.getRoot().getRight());
        System.out.println("LC2 , RC2 :" + tree.getRoot().getLeft().getLeft() + " , " + tree.getRoot().getLeft().getRight());


        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 40, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        AVLNode<Integer> node30 = root.getLeft();
        assertEquals((Integer) 30, node30.getData());
        assertEquals(0, node30.getHeight());
        assertEquals(0, node30.getBalanceFactor());

        AVLNode<Integer> node60 = root.getRight();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(1, node60.getHeight());
        assertEquals(1, node60.getBalanceFactor());

        AVLNode<Integer> node50 = node60.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(0, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());
    }

    @Test
    public void testRemoveRightRotateOneChild() {
        Integer temp = 80;
        tree.add(70);
        tree.add(60);
        tree.add(temp);
        tree.add(55);
        tree.add(65);
        tree.add(85);
        tree.add(50);
        tree.add(62);
        tree.add(66);
        /*
                                       70
                                     /    \
                                   60     80
                                  /  \       \
                                55   65       85
                               /    /  \
                              50   62  66
         */
        assertEquals(temp, tree.remove(80));
        /*                  *right rotate on 70*

                                        60
                                     /      \
                                    55      70
                                   /      /    \
                                  50     65    85
                                        /  \
                                       62  66
         */
        assertFalse(tree.contains(temp));
        assertEquals(8, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 60, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        AVLNode<Integer> node55 = root.getLeft();
        assertEquals((Integer) 55, node55.getData());
        assertEquals(1, node55.getHeight());
        assertEquals(1, node55.getBalanceFactor());

        AVLNode<Integer> node70 = root.getRight();
        assertEquals((Integer) 70, node70.getData());
        assertEquals(2, node70.getHeight());
        assertEquals(1, node70.getBalanceFactor());

        AVLNode<Integer> node50 = node55.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(0, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());

        AVLNode<Integer> node65 = node70.getLeft();
        assertEquals((Integer) 65, node65.getData());
        assertEquals(1, node65.getHeight());
        assertEquals(0, node65.getBalanceFactor());

        AVLNode<Integer> node85 = node70.getRight();
        assertEquals((Integer) 85, node85.getData());
        assertEquals(0, node85.getHeight());
        assertEquals(0, node85.getBalanceFactor());

        AVLNode<Integer> node62 = node65.getLeft();
        assertEquals((Integer) 62, node62.getData());
        assertEquals(0, node62.getHeight());
        assertEquals(0, node62.getBalanceFactor());

        AVLNode<Integer> node66 = node65.getRight();
        assertEquals((Integer) 66, node66.getData());
        assertEquals(0, node66.getHeight());
        assertEquals(0, node66.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightRotateWithNoChildSuccessor() {
        testAddRightLeftRotate();
        tree.remove(48);
        tree.remove(54);
        tree.remove(78);
        tree.remove(50);
        tree.remove(68);
        /*
                                            44
                                          /    \
                                         32    62
                                        /
                                       17


         */
        //assuming that data in tree was returned, not data passed in
        assertEquals((Integer) 44, tree.remove(44));
        //my test: 
        System.out.println("new root is : " + tree.getRoot());
        /*                               *right rotate on 44*

                                               32
                                              /  \
                                             17  62
         */
        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 32, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node17 = root.getLeft();
        assertEquals((Integer) 17, node17.getData());
        assertEquals(0, node17.getHeight());
        assertEquals(0, node17.getBalanceFactor());

        AVLNode<Integer> node62 = root.getRight();
        assertEquals((Integer) 62, node62.getData());
        assertEquals(0, node62.getHeight());
        assertEquals(0, node62.getBalanceFactor());
    }

    @Test
    public void testRemoveLeftRotateNoChild() {
        Integer temp = 40;
        tree.add(60);
        tree.add(temp);
        tree.add(70);
        tree.add(80);
        /*
                                          60
                                        /    \
                                       40    70
                                               \
                                                80
         */
        assertEquals(temp, tree.remove(40));
        /*                              *left rotate on 60*

                                               70
                                              /  \
                                            60    80
         */
        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 70, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node60 = root.getLeft();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());

        AVLNode<Integer> node80 = root.getRight();
        assertEquals((Integer) 80, node80.getData());
        assertEquals(0, node80.getHeight());
        assertEquals(0, node80.getBalanceFactor());


    }

    @Test
    public void testRemoveLeftRotateOneChild() {
        Integer temp = 40;
        tree.add(50);
        tree.add(temp);
        tree.add(80);
        tree.add(20);
        tree.add(60);
        tree.add(90);
        tree.add(85);
        tree.add(95);
        /*
                                                     50
                                                   /    \
                                                  40    80
                                                 /     /  \
                                                20    60  90
                                                         /  \
                                                        85  95
         */
        assertEquals(temp,tree.remove(40));
        /*                                         *left rotate on 50*

                                                        80
                                                     /      \
                                                    50       90
                                                   /  \     /  \
                                                  20  60   85  95
         */
        assertEquals(7, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 80, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node50 = root.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(1, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());

        AVLNode<Integer> node90 = root.getRight();
        assertEquals((Integer) 90, node90.getData());
        assertEquals(1, node90.getHeight());
        assertEquals(0, node90.getBalanceFactor());

        AVLNode<Integer> node20 = node50.getLeft();
        assertEquals((Integer) 20, node20.getData());
        assertEquals(0, node20.getHeight());
        assertEquals(0, node20.getBalanceFactor());

        AVLNode<Integer> node60 = node50.getRight();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());

        AVLNode<Integer> node85 = node90.getLeft();
        assertEquals((Integer) 85, node85.getData());
        assertEquals(0, node85.getHeight());
        assertEquals(0, node85.getBalanceFactor());

        AVLNode<Integer> node95 = node90.getRight();
        assertEquals((Integer) 95, node95.getData());
        assertEquals(0, node95.getHeight());
        assertEquals(0, node95.getBalanceFactor());



    }
    @Test
    public void testRemoveLeftRotateWithNoChildSuccessor() {
        testAddRightLeftRotate();
        tree.remove(48);
        tree.remove(54);
        /*
                                            44
                                          /    \
                                         32    62
                                        /     /  \
                                       17    50  68
                                                   \
                                                    78
         */
        //assuming that data in tree was returned, not data passed in
        assertEquals((Integer) 44, tree.remove(44));

        /*                                  *Left rotate on 62*

                                                   50
                                                  /  \
                                                 32   68
                                                /    /  \
                                               17   62  78
         */

        assertFalse(tree.contains(44));
        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 50, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node32 = root.getLeft();
        assertEquals((Integer) 32, node32.getData());
        assertEquals(1, node32.getHeight());
        assertEquals(1, node32.getBalanceFactor());

        AVLNode<Integer> node68 = root.getRight();
        assertEquals((Integer) 68, node68.getData());
        assertEquals(1, node68.getHeight());
        assertEquals(0, node68.getBalanceFactor());

        AVLNode<Integer> node17 = node32.getLeft();
        assertEquals((Integer) 17, node17.getData());
        assertEquals(0, node17.getHeight());
        assertEquals(0, node17.getBalanceFactor());

        AVLNode<Integer> node62 = node68.getLeft();
        assertEquals((Integer) 62, node62.getData());
        assertEquals(0, node62.getHeight());
        assertEquals(0, node62.getBalanceFactor());

        AVLNode<Integer> node78 = node68.getRight();
        assertEquals((Integer) 78, node78.getData());
        assertEquals(0, node78.getHeight());
        assertEquals(0, node78.getBalanceFactor());

    }
    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeftRotateWithNoChild() {
        Integer temp = 32;
        tree.add(50);
        tree.add(temp);
        tree.add(68);
        tree.add(60);
        assertEquals(4, tree.size());
        /*
                                                   50
                                                 /    \
                                                32    68
                                                     /
                                                    60
         */
        assertEquals(temp, tree.remove(32));
        /*                     *Right rotate on 68, left rotate on 50*

                                                   60
                                                  /  \
                                                 50   68
         */
        assertEquals(3, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 60, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node50 = root.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(0, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());

        AVLNode<Integer> node68 = root.getRight();
        assertEquals((Integer) 68, node68.getData());
        assertEquals(0, node68.getHeight());
        assertEquals(0, node68.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRightRotate() {
        Integer temp = 68;
        tree.add(50);
        tree.add(32);
        tree.add(temp);
        tree.add(35);
        assertEquals(4, tree.size());
        /*
                                                   50
                                                 /    \
                                                32    68
                                                  \
                                                   35
         */
        assertEquals(temp, tree.remove(68));
        /*                             *Left rotate on 32, right rotate on 50*

                                                   35
                                                  /  \
                                                 32  50

         */
        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 35, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node32 = root.getLeft();
        assertEquals((Integer) 32, node32.getData());
        assertEquals(0, node32.getHeight());
        assertEquals(0, node32.getBalanceFactor());

        AVLNode<Integer> node50 = root.getRight();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(0, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode1() {
        Integer temp = 1;
        tree.add(temp);
        assertSame(temp, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode2() {
        Integer temp = 6;
        tree.add(3);
        tree.add(1);
        tree.add(5);
        tree.add(0);
        tree.add(2);
        tree.add(4);
        tree.add(temp);
         /*
                     3
                   /   \
                 1      5
                / \    / \
               0   2  4   6
         */

        assertSame(temp, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testSuccessor1() {
        testAddRightLeftRotate();
        /*
                                            44
                                          /    \
                                         32    62
                                        /     /  \
                                       17    50  68
                                            /  \   \
                                           48  54  78
         */
        assertEquals((Integer) 48, tree.successor(44));
        assertEquals((Integer) 44, tree.successor(32));
        assertEquals((Integer) 68, tree.successor(62));
        assertEquals((Integer) 32, tree.successor(17));
        assertEquals((Integer) 54, tree.successor(50));
        assertEquals((Integer) 78, tree.successor(68));
        assertEquals((Integer) 50, tree.successor(48));
        assertEquals((Integer) 62, tree.successor(54));
        assertNull(tree.successor(78));
    }
    /*
     *****************************************************EXCEPTION TESTS***********************************************
     */
    //mine
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorWithNullElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(null);
        AVL<Integer> avl = new AVL<>(list);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorWithNullData() {
        AVL<Integer> avl = new AVL<>(null);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullElement() {
        tree.add(null);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullData() {
        testAddLeftRotateSimple();
        tree.remove(null);

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNonExistingData() {
        testAddLeftRotateSimple();
        tree.remove(5);

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetAndContainsNullData() {
        testAddLeftRotateSimple();
        tree.get(null);
        tree.contains(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNonExistingData() {
        testAddLeftRotateSimple();
        tree.get(5);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void successorOfNonExistingData() {
        testAddLeftRotateSimple();
        tree.successor(5);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void successorOfNullData() {
        testAddLeftRotateSimple();
        tree.successor(null);
    }
}