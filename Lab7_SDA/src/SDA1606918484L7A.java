import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Lab 7A SDA
 * @author Muhammad At Thoriq
 * 17/11/17
 */
public class SDA1606918484L7A 
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        String instruction = input.readLine();
        AVLTree<Komik> data = new AVLTree<Komik>();

        while (instruction != null)
        {
            String[] command = instruction.split(" ");

            if (command[0].equalsIgnoreCase("ADD"))
            {
                Komik komik = new Komik(command[1], Integer.parseInt(command[2]));
                data.insert(komik);
                System.out.println("Komik " + command[1] + " sudah disimpan dalam JaringToon");
            }
            else if (command[0].equalsIgnoreCase("REMOVE"))
            {
                if (data.isEmpty())
                    System.out.println("Tidak ada komik dalam JaringToon");
                else 
                {
                    Komik komik = new Komik(command[1], Integer.parseInt(command[2]));
                    data.remove(komik);
                    System.out.println("Komik " + command[1] + " sudah dihapus dari JaringToon");
                }
            }
            else if (command[0].equalsIgnoreCase("POPULARITY")) 
            {
                if (data.isEmpty())
                    System.out.println("Tidak ada komik dalam JaringToon");
                else 
                {
                    Komik max = data.findMax();
                    Komik min = data.findMin();
                    //jika min dan max sama berarti Tree hanya punya 1 node
                    if (min.compareTo(max) == 0)
                    	System.out.println("Hanya ada komik " + max.nama);
                    else
                    	System.out.println("Tertinggi " + max.nama + "; Terendah " + min.nama );
                }
            }
            else if (command[0].equalsIgnoreCase("PRINT"))
            {
                if (data.isEmpty())
                {
                    System.out.println("Tidak ada komik dalam JaringToon");
                }
                else
                {
                	// Get and print an In Order Traversal AVL Tree's List
                    System.out.print("In Order: ");
                    ArrayList<Komik> list = data.inorder();
                    printList(list);

                    // Get and print an Pre Order Traversal AVL Tree's List
                    System.out.print("Pre Order: ");
                    list = data.preorder();
                    printList(list);

                    // Get and print an Post Order Traversal AVL Tree's List
                    System.out.print("Post Order: ");
                    list = data.postorder();
                    printList(list);
                }
            }

            instruction = input.readLine();
        }
    }

    /**
     * Method to print elements from a given tree
     * @param List
     */
    public static void printList (ArrayList<Komik> list)
    {
        for (int i = 0; i < list.size()-1; i++)
        {
        	System.out.print(list.get(i).nama + "; ");
        }
        System.out.println(list.get(list.size()-1).nama);
    }
}

/**
 * Objek komik
 *
 */
class Komik implements Comparable<Komik>
{
    String nama;
    int popularitas;

    public Komik(String nama, int popularitas)
    {
        this.nama = nama;
        this.popularitas = popularitas;
    }

    /**
     * Compare the comics by its popularity
     */
    @Override
    public int compareTo(Komik other)
    {
        if (popularitas < other.popularitas)
            return -1;
        else if (popularitas > other.popularitas)
            return 1;
        else
            return 0;
    }

    /**
     * Print the object by its name
     */
    @Override
    public String toString()
    {
        return nama;
    }
}

/**
 * AVLTree Generic Class
 * by template
 * @param <E>
 */
class AVLTree <E extends Comparable<? super E>>
{

    /** Class AVLNode */
    private class AVLNode<E>
    {
        AVLNode<E> left, right;
        E data;
        int height;

        /* Constructor */
        public AVLNode(E element)
        {
            left = null;
            right = null;

            data = element;
            height = 0;
        }
    }
    /** END */

    private AVLNode<E> root;

    /* Constructor */
    public AVLTree()
    {
        root = null;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty()
    {
        return root == null;
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
            current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;
        }

        else if( compareResult > 0 ) 
        {
            current.right = insert(elem, current.right);
            current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;
        }

        return balance( current );
    }

    /**
     * Menghapus elemen dari tree
     * @param x elemen yang akan dihapus
     */
    public void remove( E x )
    {
        root = remove( x, root );
    }

    /**
     *
     * Menghapus objek dari tree, menggunakan predecessor inorder untuk menghapus elemen yang memiliki left node dan right node
     * Manfaatkan method findMax(AvlNode<E> node) untuk mencari predecessor inorder
     * @param elem elemen yang ingin dihapus
     * @param current posisi node saat ini.
     * @return true root baru dari subtree
     *
     */
    private AVLNode<E> remove( E elem, AVLNode<E> current )
    {
        if( current == null ) 
        {
            return current;   // Item not found; do nothing
        }

        int compareResult = elem.compareTo( current.data );

        if( compareResult < 0 ) 
        {
            current.left = remove(elem, current.left);
        }

        else if( compareResult > 0 ) 
        {
            current.right = remove(elem, current.right);
        }

        else if( current.left != null && current.right != null ) 
        {
            current.data = findMax(current.left).data;
            current.left = remove(current.data, current.left);
        }

        else 
        {
            current = (current.left != null) ? current.left : current.right;
        }

        if (current != null) 
        	current.height = height(current.left) > height(current.right) ? height(current.left) + 1 : height(current.right) + 1;
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
                System.out.println("Lakukan rotasi sekali pada "+ current.data.toString());
            }
            else 
            {
                current = doubleWithLeftChild(current);
                System.out.println("Lakukan rotasi dua kali pada "+current.data.toString());
            }
        }

        else if( height( current.right ) - height( current.left ) > ALLOWED_IMBALANCE ) 
        {
            if( height( current.right.right ) >= height( current.right.left ) ) 
            {
                current = rotateWithRightChild(current);
                System.out.println("Lakukan rotasi sekali pada "+current.data.toString());
            }

            else 
            {
                current = doubleWithRightChild(current);
                System.out.println("Lakukan rotasi dua kali pada "+current.data.toString());
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

    /**
     * Helper method to get inorder traversal
     * @return List
     */
    public ArrayList<E> inorder()
    {
        ArrayList<E> list = new ArrayList<E>();
        inorder(root, list);
        return list;
    }
    
    /**
     * Method to add inorder traversal to a given list
     * @param node
     * @param list
     */
    private void inorder(AVLNode<E> node, ArrayList<E> list)
    {
        if (node != null)
        {
            inorder(node.left, list);
            list.add(node.data);
            inorder(node.right, list);
        }
    }

    /**
     * Helper method to get preorder traversal
     * @return List
     */
    public ArrayList<E> preorder()
    {
        ArrayList<E> list = new ArrayList<E>();
        preorder(root, list);
        return list;
    }
    
    /**
     * Method to add preorder traversal to a given list
     * @param node
     * @param list
     */
    private void preorder(AVLNode<E> node, ArrayList<E> list)
    {
        if (node != null)
        {
            list.add(node.data);
            preorder(node.left, list);
            preorder(node.right, list);
        }
    }
    /**
     * Helper method to get postorder traversal
     * @return List
     */
    public ArrayList<E> postorder()
    {
        ArrayList<E> list = new ArrayList<E>();
        postorder(root, list);
        return list;
    }
    
    /**
     * Method to add postorder traversal to a given list
     * @param node
     * @param list
     */
    private void postorder(AVLNode<E> node, ArrayList<E> list)
    {
        if (node != null)
        {
            postorder(node.left, list);
            postorder(node.right, list);
            list.add(node.data);
        }
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public E findMin( )
    {
        return findMin( root ).data;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public E findMax( )
    {
        return findMax( root ).data;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param current the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AVLNode<E> findMin( AVLNode<E> current )
    {
        if( current == null )
            return current;

        while( current.left != null )
            current = current.left;
        return current;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param current the node that roots the tree.
     * @return node containing the largest item.
     */
    private AVLNode<E> findMax( AVLNode<E> current )
    {
        if( current == null )
            return current;

        while( current.right != null )
            current = current.right;
        return current;
    }
}
