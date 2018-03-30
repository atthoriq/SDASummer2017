import java.util.List;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Binary Search Tree
 * @author Muhammad At Thoriq - 1606918484
 * 29 Oktober 2017
 */
public class SDA1606918484L5A
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String instruction = input.readLine();
		
		BSTree<String> data = new BSTree();
		
		while(instruction != null)
		{
			StringTokenizer token = new StringTokenizer(instruction, ";");
			
			String command = token.nextToken();
			
			switch(command)
			{
			case("ADD") :
				String inputString = token.nextToken();
				if(data.add(inputString))
					System.out.println(inputString + " berhasil ditambahkan ke dalam tree");
				else
					System.out.println(inputString + " sudah dimasukkan sebelumnya");
				break;
			case("REMOVE") :
				inputString = token.nextToken();
				if(data.remove(inputString))
					System.out.println(inputString + " berhasil dihapus dari tree");
				else
					System.out.println(inputString + " tidak ditemukan");
				break;
			case("CONTAINS") :
				inputString = token.nextToken();
				if(data.contains(inputString))
					System.out.println(inputString + " terdapat pada tree");
				else
					System.out.println(inputString + " tidak terdapat pada tree");
				break;
			case("PREORDER") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					List<String> preOrdered = data.preOrder();
					printList(preOrdered);
				}
				break;
			case("POSTORDER") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					List<String> postOrdered = data.postOrder();
					printList(postOrdered);
				}
				break;
			case("ASCENDING") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					List<String> inOrderedAsc = data.inOrderAscending();
					printList(inOrderedAsc);
				}
				break;
			case("DESCENDING") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					List<String> inOrderedDesc = data.inOrderDescending();
					printList(inOrderedDesc);
				}
				break;
			case("MAX") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					System.out.println(data.max() + " merupakan elemen dengan nilai tertinggi");
				}
				break;
			case("MIN") :
				if(data.isEmpty())
					System.out.println("Tidak ada elemen pada tree");
				else
				{
					System.out.println(data.min() + " merupakan elemen dengan nilai terendah");
				}
				break;
			}
			instruction = input.readLine();
		}
		
	}
	
	/**
	 * Method untuk print list
	 * @param list
	 */
	public static void printList(List<String> list)
	{
		String print = "";
		for(String theData: list)
		{
			if(!print.equals(""))
				print += ";";
			print += theData;
		}
		System.out.println(print);
	}
	
	
}
/**
 *
 * Kelas Binary Search Tree
 * Mahasiswa tidak diwajibkan menggunakan template ini, namun sangat disarankan menggunakan template ini
 * Pada template ini, diasumsikan kelas yang ingin dipakai mengimplementasikan (implements) interface Comparable
 * NOTE : Tidak semua method yang dibutuhkan sudah disediakan templatenya pada kelas ini sehingga mahasiswa harus menambahkan sendiri method yang dianggap perlu
 * @author Jahns Christian Albert
 *
*/
class BSTree <E extends Comparable<? super E>> {
	
	/**
	  *
	  * Kelas yang merepresentasikan node pada tree
	  * @author Jahns Christian Albert
	  *
	*/
	private static class Node<E> {
		
		E elem;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		
		/**
		 *
		 * Constructor
		 * @param elemen pada node
		 * @param node kiri
		 * @param node kanan
		 * @param node parent
		 *
		*/
		public Node(E elem, Node<E> left, Node<E> right, Node<E> parent){
			
			this.elem = elem;
			this.left = left;
			this.right = right;
			this.parent = parent;
			
		}
		
	}
	
	private Node<E> root;
	
	
	/**
	  *
	  * Constructor Kelas Binary Search Tree
	  *
	*/
	public BSTree(){
		
		root = null;
		
	}
	
	/**
	  *
	  * Mengetahui apakah tree kosong atau tidak
	  * @return true jika kosong, false jika sebaliknya
	  *
	*/
	public boolean isEmpty(){
		
		return root == null;
		
	}
	
	/**
	  *
	  * Menambahkan objek ke dalam tree
	  * @param elemen yang ingin ditambahkan
	  * @return true jika elemen berhasil ditambahkan, false jika elemen sudah terdapat pada tree
	  *
	*/
	public boolean add(E elem){
		
		boolean res = false;
		
		if(root == null)
		{
			// TO DO : Lengkapi bagian ini
			root = new Node(elem, null, null, null);
			res = true;
		} 
		else 
		{
			Node<E> prev = null;
			Node<E> current = root;
			
			while(current != null)
			{
				E currElem = current.elem;
				if(elem.compareTo(currElem) < 0)
				{
					// TO DO : Lengkapi bagian ini
					// diletakkan di sebelah kiri
					prev = current;
					current = current.left;
					
				}
				else if(elem.compareTo(currElem) > 0)
				{
					// TO DO : Lengkapi bagian ini
					// diletakkan di sebelah kanan
					prev = current;
					current = current.right;
				}
				else
				{
					// TO DO : Lengkapi bagian ini
					// ketika elemen sudah ada di dalam tree
					return res;
				}
			}
			
			// TO DO : Lengkapi bagian ini
			if(elem.compareTo(prev.elem) < 0)
			{
				prev.left = new Node(elem, null, null, prev);
			}
			else if(elem.compareTo(prev.elem) > 0)
			{
				prev.right = new Node(elem, null, null, prev);
			}
			
			res = true;
		}
		
		return res;
		
	}
	
	/**
	  *
	  * Mendapatkan node dengan elemen tertentu
	  * @param elemen yang ingin dicari nodenya
	  * @return node dari elemen pada parameter, null jika tidak ditemukan
	  *
	*/
	private Node<E> find(E elem){
		
		Node<E> res = null;
		
		if(root != null){
			
			Node<E> current = root;
			boolean found = false;
			
			while(!found && current != null){
				
				E currElem = current.elem;
				if(elem.compareTo(currElem) < 0){
					// TO DO : Lengkapi bagian ini
					current = current.left;
					
				} else if(elem.compareTo(currElem) > 0){
					// TO DO : Lengkapi bagian ini
					current = current.right;
					
				} else {
					// TO DO : Lengkapi bagian ini
					res = current;
					found = true;
				}
				
			}
			
		}
		
		return res;
		
	}
	
	/**
	 *
	 * Menghapus objek dari tree, menggunakan successor inorder untuk menghapus elemen yang memiliki left node dan right node
	 * Manfaatkan method minNode(Node<E> node) untuk mencari successor inorder
	 * @param elemen yang ingin dihapus
	 * @return true jika elemen ditemukan dan berhasil dihapus, false jika elemen tidak ditemukan
	 *
	*/
	public boolean remove(E elem){

        boolean res = false;

        Node<E> node = find(elem);

        if(node != null){
            if(node.left == null){
                if(node.right == null){
                    // TO DO : Lengkapi bagian ini
                	// Jika yang dihapus adalah root
                    if(node == root)
                    {
                        root = null;
                        return true;
                    }
                    else
                    {
                        if(elem.compareTo(node.parent.elem) < 0)
                        {
                            node.parent.left = null;
                        }
                        else
                        {
                            node.parent.right = null;
                        }
                    }
                }
                // Ketika tree memiliki degree 1 sebelah kanan
                else {
                    // TO DO : Lengkapi bagian ini
                    node.right.parent = node.parent;
                    // Jika yang dihapus adalah root
                    if(node.right.parent == null)
                    {
                        root = node.right;
                        return true;
                    }
                    else
                    {
                        if(elem.compareTo(node.parent.elem) < 0)
                        {
                            node.parent.left = node.right;
                        }
                        else if(elem.compareTo(node.parent.elem) > 0)
                        {
                            node.parent.right = node.right;
                        }
                    }
                }
            }
            else {
            	// Ketika tree memiliki degree 1 sebelah kiri
                if(node.right == null)
                {
                    // TO DO : Lengkapi bagian ini
                    node.left.parent = node.parent;
                    // Jika yang dihapus adalah root
                    if(node.left.parent == null){
                        root = node.left;
                        return true;
                    }
                    else
                    {
                        if(elem.compareTo(node.parent.elem) > 0)
                        {
                            node.parent.right = node.left;
                        }
                        else if(elem.compareTo(node.parent.elem) < 0)
                        {
                            node.parent.left = node.left;
                        }
                    }
                }
                // Ketika tree memiliki degree 2
                else 
                {
                    // TO DO : Lengkapi bagian ini
                    Node<E> successor = minNode(node.right);
                    node.elem = successor.elem;
                    if(successor.right == null)
                    {
                        if(successor.parent == root)
                        {
                            successor.parent.right = null;
                        }
                        else
                        {
                            if(successor.elem.compareTo(successor.parent.elem) < 0)
                            {
                                successor.parent.left = null;
                            }
                            else
                            {
                                successor.parent.right = null;
                            }
                        }
                    }
                    else
                    {
                        if(successor.parent == root)
                        {
                            successor.parent.right = successor.right;
                            successor.right.parent = successor.parent;
                        }
                        else
                        {
                            successor.right.parent = successor.parent;
                            successor.parent.right = successor.right;
                        }
                    }
                }
            }
            res = true;
        }
        return res;
    }
	
	/**
	 *
	 * Mencari elemen dengan nilai paling kecil pada tree
	 * @return elemen dengan nilai paling kecil pada tree
	 *
	*/
	public E min(){
		
		E res = null;
		Node<E> minNode = minNode(root);
		
		if(minNode != null){
			
			res = minNode.elem;
			
		}
		
		return res;
		
	}
	
	/**
	  *
	  * Method untuk mengembalikan node dengan elemen terkecil pada suatu subtree
	  * Hint : Manfaatkan struktur dari binary search tree
	  * @param node root dari subtree yang ingin dicari elemen terbesarnya
	  * @return node dengan elemen terkecil dari subtree yang diinginkan
	  *
	*/
	private Node<E> minNode(Node<E> node){
		
		Node<E> res = null;
		if(node != null){
			
			Node<E> current = node;
			// TO DO : Lengkapi bagian ini
			res = node;
			if(node.left != null)
			{
				res = minNode(node.left);
			}
			
		}
		
		return res;
		
	}
	
	/**
	 *
	 * Mencari elemen dengan nilai paling besar pada tree
	 * @return elemen dengan nilai paling besar pada tree
	 *
	*/
	public E max(){
		
		E res = null;
		Node<E> maxNode = maxNode(root);
		
		if(maxNode != null){
			
			res = maxNode.elem;
			
		}
		
		return res;
		
	}
	
	/**
	  *
	  * Method untuk mengembalikan node dengan elemen terbesar pada suatu subtree
	  * Hint : Manfaatkan struktur dari binary search tree
	  * @param node root dari subtree yang ingin dicari elemen terbesarnya
	  * @return node dengan elemen terbesar dari subtree yang diinginkan
	  *
	*/
	private Node<E> maxNode(Node<E> node){
		
		Node<E> res = null;
		if(node != null){
			
			Node<E> current = node;
			// TO DO : Lengkapi bagian ini
			res = node;
			if(node.right != null)
			{
				res = maxNode(node.right);
			}
		}
		
		return res;
		
	}
	
	/**
	  *
	  * Mengetahui apakah sebuah objek sudah terdapat pada tree
	  * Asumsikan jika elem.compareTo(otherElem) == 0, maka elem dan otherElem merupakan objek yang sama
	  * Hint : Manfaatkan method find
	  * @param elemen yang ingin diketahui keberadaannya dalam tree
	  * @return true jika elemen ditemukan, false jika sebaliknya
	  *
	*/
	public boolean contains(E elem){
		
		// TO DO : Lengkapi method ini
		Node<E> node = find(elem);
		if(node == null)
			return false;
		return true;
		
	}
	
	/**
	  * Mengembalikan tree dalam bentuk pre-order
	  * @return tree dalam bentuk pre-order sebagai list of E
	*/
	public List<E> preOrder(){
		
		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return preOrder(root,list);
		
	}
	
	/**
	  *
	  * Method helper dari preOrder()
	  * @param node pointer
	  * @param list sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan pre-order
	  *
	*/
	private List<E> preOrder(Node<E> node, List<E> list){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			list.add(node.elem);
			preOrder(node.left, list);
			preOrder(node.right, list);
		}
		
		return list;
		
	}
	
	/**
	  * Mengembalikan tree dalam bentuk post-order
	  * @return tree dalam bentuk post-order sebagai list of E
	*/
	public List<E> postOrder(){
		
		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return postOrder(root,list);
		
		
	}
	
	/**
	  *
	  * Method helper dari postOrder()
	  * @param node pointer
	  * @param list sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan post-order
	  *
	*/
	private List<E> postOrder(Node<E> node, List<E> list){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			postOrder(node.left, list);
			postOrder(node.right, list);
			list.add(node.elem);
		}
		return list;
		
	}
	
	
	/**
	  * Mengembalikan tree dalam bentuk in-order secara ascending
	  * @return tree dalam bentuk in-order secara ascending sebagai list of E
	*/
	public List<E> inOrderAscending(){
		
		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderAscending(root,list);
		
	}
	
	/**
	  *
	  * Method helper dari inOrderAscending()
	  * @param node pointer
	  * @param list sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order secara ascending
	  *
	*/
	private List<E> inOrderAscending(Node<E> node, List<E> list){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			inOrderAscending(node.left, list);
			list.add(node.elem);
			inOrderAscending(node.right, list);
		}
		return list;
		
	}
	
	
	/**
	  * Mengembalikan tree dalam bentuk in-order secara descending
	  * @return tree dalam bentuk in-order secara descending sebagai list of E
	*/
	public List<E> inOrderDescending(){
		
		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderDescending(root,list);
		
	}
	
	/**
	  *
	  * Method helper dari inOrderDescending()
	  * @param node pointer
	  * @param list sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order descending
	  *
	*/
	private List<E> inOrderDescending(Node<E> node, List<E> list){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			inOrderDescending(node.right, list);
			list.add(node.elem);
			inOrderDescending(node.left, list);
		}
		return list;
		
	}
	
	
}