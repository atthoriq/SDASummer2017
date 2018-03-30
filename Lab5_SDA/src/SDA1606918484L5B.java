import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Iterator;


public class SDA1606918484L5B 
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String instruction = input.readLine();
		
		BSTreeMap<String,Integer> map = new BSTreeMap<>();
		
		while(instruction != null)
		{
			StringTokenizer token = new StringTokenizer(instruction, ";");
			String command = token.nextToken();
			
			switch(command)
			{
			case("REGISTER") :
				String name = token.nextToken();
				int score = Integer.parseInt(token.nextToken());
				if(map.add(name, score))
				{
					System.out.println(name + ":" + score + " berhasil ditambahkan");
				}
				else
				{
					System.out.println(name + " sudah terdaftar di dalam sistem");
				}
				break;
			case("RESIGN") :
				name = token.nextToken();
				if(map.remove(name))
				{
					System.out.println(name + " mengundurkan diri");
				}
				else
				{
					System.out.println(name + " tidak ditemukan dalam sistem");
				}
				break;
			case("RETEST") :
				name = token.nextToken();
				score = Integer.parseInt(token.nextToken());
				if(map.replace(name, score))
				{
					System.out.println(name + ":" + score + " perubahan berhasil");
				}
				else
				{
					System.out.println(name + " tidak ditemukan dalam sistem");
				}
				break;
			case("SMARTEST") :
				if(map.isEmpty())
				{
					System.out.println("Tidak ada siswa yang terdaftar dalam sistem");
					break;
				}
			
				Map<String, Integer> inordered = sortByValue(map.inOrderAscending());
				Iterator<Map.Entry<String, Integer>> iterator = inordered.entrySet().iterator();
				
				String smartest = "";
				int hiScore = 0;
				while(iterator.hasNext())
				{
					Map.Entry<String, Integer> res = iterator.next();
					int tempScore = res.getValue();
					
					if(hiScore == 0)
					{
						smartest += res.getKey();
						hiScore = tempScore;
					}
					else
					{
						if(tempScore != hiScore)
							break;
						else
							smartest += ", " + res.getKey();
					}
				}
				
				System.out.println(smartest + " : " + hiScore);
				break;
			case("RANKING") :
				if(map.isEmpty())
				{
					System.out.println("Tidak ada siswa yang terdaftar dalam sistem");
					break;
				}
				inordered = sortByValue(map.inOrderAscending());
				List<Map.Entry<String, Integer>> listOfMap = new LinkedList<Map.Entry<String, Integer>>(inordered.entrySet());
				
				iterator = inordered.entrySet().iterator();
				
				String people = "";
				int scores = 0;
				int rank = 1;
				int iterate = inordered.size();
				
				if(listOfMap.size() == 1)
				{
					people = listOfMap.get(0).getKey();
					scores = listOfMap.get(0).getValue();
				}
				
				for(int i = 1; i < listOfMap.size(); i++)
				{
					if(scores == 0)
					{
						
						scores = listOfMap.get(i-1).getValue();
						people += listOfMap.get(i-1).getKey();
					}
					
					if(listOfMap.get(i).getValue() == scores)
					{
						people += ", " + listOfMap.get(i).getKey();
					}
					else
					{
						System.out.println(rank + ". " + people + " : " + scores);
						scores = listOfMap.get(i).getValue();
						people = listOfMap.get(i).getKey();
						rank++;
					}
					
				}
				System.out.println(rank + ". " + people + " : " + scores);
				break;
			}
			instruction = input.readLine();
		}
	}
	
	public static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap)
	{ 
		 List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String,Integer>>(unsortedMap.entrySet());
	     
		 Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() 
		 {
			 public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) 
			 {
	             if(o1.getValue() < o2.getValue())
	            	 return 1;
	             else if(o1.getValue() > o2.getValue())
	            	 return -1;
	             else
	            	 return (o1.getKey()).compareTo(o2.getKey());
	         }
		 });
	    
	     Map<String,Integer> sortedMap = new LinkedHashMap<String,Integer>();
	     for(Map.Entry<String,Integer> entry:list)
	     {
	    	 sortedMap.put(entry.getKey(), entry.getValue());
	     }
	     return sortedMap;
	}
}

class BSTreeMap <K extends Comparable<? super K>, V> {
	
	/**
	  * Kelas yang merepresentasikan node pada tree
	  * @author Jahns Christian Albert
	*/
	private static class Node<K, V> {
		
		K key;
		V value;
		Node<K,V> left;
		Node<K,V> right;
		Node<K,V> parent;
		
		/**
		 * Constructor
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 * @param parent
		 */
		public Node(K key, V value, Node<K,V> left, Node<K,V> right, Node<K,V> parent){
			
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
			
		}
		
	}
	
	private Node<K,V> root;
	
	
	/**
	  * Constructor Kelas Binary Search Tree
	  *
	*/
	public BSTreeMap(){
		
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
	  * Menambahkan objek ke dalam tree
	  * @param elemen yang ingin ditambahkan
	  * @return true jika elemen berhasil ditambahkan, false jika elemen sudah terdapat pada tree
	  *
	*/
	public boolean add(K key, V value){
		
		boolean res = false;
		
		if(root == null)
		{
			// TO DO : Lengkapi bagian ini
			root = new Node(key, value, null, null, null);
			res = true;
		} 
		else 
		{
			Node<K,V> prev = null;
			Node<K,V> current = root;
			
			while(current != null)
			{
				K currKey = current.key;
				if(key.compareTo(currKey) < 0)
				{
					// TO DO : Lengkapi bagian ini
					// diletakkan di sebelah kiri
					prev = current;
					current = current.left;
					
				}
				else if(key.compareTo(currKey) > 0)
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
			if(key.compareTo(prev.key) < 0)
			{
				prev.left = new Node(key, value, null, null, prev);
			}
			else if(key.compareTo(prev.key) > 0)
			{
				prev.right = new Node(key, value, null, null, prev);
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
	private Node<K,V> find(K key){
		
		Node<K,V> res = null;
		
		if(root != null){
			
			Node<K,V> current = root;
			boolean found = false;
			
			while(!found && current != null){
				
				K currKey = current.key;
				if(key.compareTo(currKey) < 0){
					// TO DO : Lengkapi bagian ini
					current = current.left;
					
				} else if(key.compareTo(currKey) > 0){
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
	public boolean remove(K key){

        boolean res = false;

        Node<K,V> node = find(key);

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
                        if(key.compareTo(node.parent.key) < 0)
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
                        if(key.compareTo(node.parent.key) < 0)
                        {
                            node.parent.left = node.right;
                        }
                        else if(key.compareTo(node.parent.key) > 0)
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
                        if(key.compareTo(node.parent.key) > 0)
                        {
                            node.parent.right = node.left;
                        }
                        else if(key.compareTo(node.parent.key) < 0)
                        {
                            node.parent.left = node.left;
                        }
                    }
                }
                // Ketika tree memiliki degree 2
                else 
                {
                    // TO DO : Lengkapi bagian ini
                    Node<K,V> successor = minNode(node.right);
                    node.key = successor.key;
                    if(successor.right == null)
                    {
                        if(successor.parent == root)
                        {
                            successor.parent.right = null;
                        }
                        else
                        {
                            if(successor.key.compareTo(successor.parent.key) < 0)
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
	
	public boolean replace(K key, V value)
	{
		boolean res = false;
		
		Node<K,V> node = find(key);
		
		if(node != null)
		{
			node.value = value;
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
	public Node<K,V> min(){
		
		Node<K,V> res = null;
		Node<K,V> minNode = minNode(root);
		
		if(minNode != null){
			
			res = minNode;
			
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
	private Node<K,V> minNode(Node<K,V> node){
		
		Node<K,V> res = null;
		if(node != null){
			
			Node<K,V> current = node;
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
	public Node<K,V> max(){
		
		Node<K,V> res = null;
		Node<K,V> maxNode = maxNode(root);
		
		if(maxNode != null){
			
			res = maxNode;
			
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
	private Node<K,V> maxNode(Node<K,V> node){
		
		Node<K,V> res = null;
		if(node != null){
			
			Node<K,V> current = node;
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
	public boolean contains(K key){
		
		// TO DO : Lengkapi method ini
		Node<K,V> node = find(key);
		if(node == null)
			return false;
		return true;
		
	}
	
	/**
	  * Mengembalikan tree dalam bentuk pre-order
	  * @return tree dalam bentuk pre-order sebagai list of E
	*/
	public Map<K,V> preOrder(){
		
		Map<K,V> map = new HashMap<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return preOrder(root,map);
		
	}
	
	/**
	  *
	  * Method helper dari preOrder()
	  * @param node pointer
	  * @param map sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan pre-order
	  *
	*/
	private Map<K,V> preOrder(Node<K,V> node, Map<K,V> map){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			map.put(node.key, node.value);
			preOrder(node.left, map);
			preOrder(node.right, map);
		}
		
		return map;
		
	}
	
	/**
	  * Mengembalikan tree dalam bentuk post-order
	  * @return tree dalam bentuk post-order sebagai list of E
	*/
	public Map<K,V> postOrder(){
		
		Map<K,V> map = new HashMap<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return postOrder(root,map);
		
		
	}
	
	/**
	  *
	  * Method helper dari postOrder()
	  * @param node pointer
	  * @param map sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan post-order
	  *
	*/
	private Map<K,V> postOrder(Node<K,V> node, Map<K,V> map){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			postOrder(node.left, map);
			postOrder(node.right, map);
			map.put(node.key, node.value);
		}
		return map;
		
	}
	
	
	/**
	  * Mengembalikan tree dalam bentuk in-order secara ascending
	  * @return tree dalam bentuk in-order secara ascending sebagai list of E
	*/
	public Map<K,V> inOrderAscending(){
		
		Map<K,V> map = new HashMap<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderAscending(root,map);
		
	}
	
	/**
	  *
	  * Method helper dari inOrderAscending()
	  * @param node pointer
	  * @param map sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order secara ascending
	  *
	*/
	private Map<K,V> inOrderAscending(Node<K,V> node, Map<K,V> map){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			inOrderAscending(node.left, map);
			map.put(node.key, node.value);
			inOrderAscending(node.right, map);
		}
		return map;
		
	}
	
	
	/**
	  * Mengembalikan tree dalam bentuk in-order secara descending
	  * @return tree dalam bentuk in-order secara descending sebagai list of E
	*/
	public Map<K,V> inOrderDescending(){
		
		Map<K,V> map = new HashMap<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderDescending(root,map);
		
	}
	
	/**
	  *
	  * Method helper dari inOrderDescending()
	  * @param node pointer
	  * @param list sebagai akumulator
	  * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order descending
	  *
	*/
	private Map<K,V> inOrderDescending(Node<K,V> node, Map<K,V> map){
		
		// TO DO : Lengkapi method ini
		if(node != null)
		{
			inOrderAscending(node.right, map);
			map.put(node.key, node.value);
			inOrderAscending(node.left, map);
		}
		return map;
		
	}
	
	
}
