import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * SDA Lab 7B
 * @author Muhammad At Thoriq
 * 17/11/17
 */
public class SDA1606918484L7B 
{
    public static void main(String[] args) throws IOException 
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        
        String[] listOfString = input.readLine().split(" ");
        Long[] listOfNumber = new Long[listOfString.length];

        AVLTree2<Long> data = new AVLTree2<Long>();
        int[] listGreater = new int[listOfNumber.length];

        for (int i = listOfNumber.length-1; 0 <= i; i--)
        {
            listOfNumber[i] = Long.parseLong(listOfString[i]);
            data.insert(listOfNumber[i]);

            int instance = data.findGreater(listOfNumber[i]);
            listGreater[i] = instance;
        }

        for (int i = 0; i < listGreater.length-1; i++) 
        {
            System.out.printf("%d ", listGreater[i]);
        }
        
        System.out.println(listGreater[listGreater.length-1]);
    }
}

/* Class AVLTree */
class AVLTree2 <E extends Comparable<? super E>>
{

    /** Class AVLNode */
    private class AVLNode<E>
    {
        AVLNode<E> left, right;
        E data;
        int height;
        int sumOfChild; // jumlah anak dari node

        /* Constructor */
        public AVLNode(E element)
        {
            left = null;
            right = null;
            sumOfChild = 0;
            data = element;
            height = 0;
        }
    }
    /** END */

    private AVLNode<E> root;

    /* Constructor */
    public AVLTree2()
    {
        root = null;
    }

    /* Function to insert data */
    public void insert(E element)
    {
        root = insert(element, root);
    }

    /* Function to get height of node */
    private int height(AVLNode<E> t )
    {
        return t == null ? -1 : t.height;
    }

    /* Function to max of left/right node */
    private int max(int lhs, int rhs)
    {
        return lhs > rhs ? lhs : rhs;
    }


    /**
     * Function to insert data recursively
     * Method internal untuk menambahkan objek ke dalam tree
     * @param elem elemen yang ingin ditambahkan
     * @param current posisi node saat ini
     * @return root baru dari subtree
     *
     */
    private AVLNode<E> insert( E elem, AVLNode<E> current )
    {
        if( current == null ) 
        {
            current = new AVLNode<E>(elem);
        }

        int compareResult = elem.compareTo( current.data );

        // if the elem that want to be inserted is less than current
        if( compareResult < 0 ) 
        {
            current.left = insert(elem, current.left);
            current.sumOfChild++;

            current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;
        }
        
        else if( compareResult > 0 ) 
        {
            current.right = insert(elem, current.right);
            current.sumOfChild++;

            current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;
        }

        /**
         * melakukan balancing di node current.
         * apakah current node masih memenuhi syarat AVL Tree
         * apakah perlu dilakukan rotation atau tidak
         */
        return balance( current );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Mem-balance kan tree
     * Gunakan method rotateWithLeftChild, rotateWithRightChild,
     * doubleWithRightChild, dan doubleWithLeftChild
     * Jangan lupa update height dari node
     * @param current root dari subtree yang ingin dibalance
     * @return node setelah balance
     */
    private AVLNode<E> balance( AVLNode<E> current )
    {
        if( current == null ) 
        {
            return current;
        }

        if( height( current.left ) - height( current.right ) > ALLOWED_IMBALANCE ) 
        {
            if( height( current.left.left ) >= height( current.left.right ) ) 
            {
                current = rotateWithLeftChild(current);
            }
            
            else 
            {
                current = doubleWithLeftChild(current);
            }
        }

        else if( height( current.right ) - height( current.left ) > ALLOWED_IMBALANCE ) 
        {
            if( height( current.right.right ) >= height( current.right.left ) ) 
            {
                current = rotateWithRightChild(current);
            }

            else 
            {
                current = doubleWithRightChild(current);
            }
        }

        if (current != null)
            current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;

        return current;
    }

    /**
     * Rotate tree node with left child
     * @param parent
     * @return child
     */
    private AVLNode<E> rotateWithLeftChild(AVLNode<E> parent)
    {

        AVLNode<E> child = parent.left;
        parent.left = child.right;
        child.right = parent;

        parent.height = max( height( parent.left ), height( parent.right ) ) + 1;
        child.height = max( height( child.left ), parent.height ) + 1;

        int sumAfterRotate = (parent.left != null)? parent.left.sumOfChild : -1;
        parent.sumOfChild = parent.sumOfChild - (child.sumOfChild + 1) + sumAfterRotate + 1;
        child.sumOfChild = child.sumOfChild - (sumAfterRotate + 1) + parent.sumOfChild + 1;

        return child;
    }

    /**
     * Rotate tree node with right child
     * @param parent
     * @return child
     */
    private AVLNode<E> rotateWithRightChild(AVLNode<E> parent)
    {
        AVLNode child = parent.right;
        parent.right = child.left;
        child.left = parent;
        
        parent.height = max( height( parent.left ), height( parent.right ) ) + 1;
        child.height = max( height( child.right ), parent.height ) + 1;

        int sumAfterRotate = (parent.right != null)? parent.right.sumOfChild : -1;
        parent.sumOfChild = parent.sumOfChild - (child.sumOfChild + 1) + sumAfterRotate + 1;
        child.sumOfChild = child.sumOfChild - (sumAfterRotate + 1) + parent.sumOfChild + 1;

        return child;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child 
     */
    private AVLNode<E> doubleWithLeftChild(AVLNode<E> parent)
    {
        parent.left = rotateWithRightChild( parent.left );
        return rotateWithLeftChild( parent );
    }
    
    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child 
     */
    private AVLNode doubleWithRightChild(AVLNode parent)
    {
        parent.right = rotateWithLeftChild( parent.right );
        return rotateWithRightChild( parent );
    }

    public int findGreater(E elem)
    {
        int instance = findGreater(elem, root);

        return instance;
    }

    private int findGreater(E elem, AVLNode<E> node)
    {
        int res = 0;
        AVLNode<E> current = node;

        while (current != null)
        {
            int sumOfRightSubTree = (current.right != null)? current.right.sumOfChild : -1;

            if (current.data.compareTo(elem) > 0)
            {
                res += sumOfRightSubTree + 1 + 1;
                current = current.left;
            }
            else if (current.data.compareTo(elem) < 0)
            {
                current = current.right;
            }
            else 
            {
                res += sumOfRightSubTree + 1;
                break;
            }
        }

        return res;
    }
}
