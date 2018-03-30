import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Lab 6 Soal A - Binary Heap
 * @author Muhammad At Thoriq
 * 1606918484
 * 11/11/2017
 */
public class SDA1606918484L6A {

	public static void main(String[] args) throws IOException {
		//TODO read the input command
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String instruction = reader.readLine();
		
		MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();
		
		while(instruction != null)
		{
			String[] commands = instruction.split(" ");
			
			if(commands[0].equalsIgnoreCase("INSERT"))
			{
				int value = Integer.parseInt(commands[1]);
				if(queue.add(value))
				{
					System.out.println("elemen dengan nilai " + value + " telah ditambahkan");
				}
			}
			else if(commands[0].equalsIgnoreCase("REMOVE"))
			{
				if(queue.isEmpty())
					System.out.println("min heap kosong");
				else
				{
					System.out.println(queue.poll() + " dihapus dari heap");
				}
			}
			else if(commands[0].equalsIgnoreCase("NUM_PERCOLATE_UP"))
			{
				int value = Integer.parseInt(commands[1]);
				
				int num = queue.percolateUp(value);
				System.out.println("percolate up " + num);
			}
			else if(commands[0].equalsIgnoreCase("NUM_PERCOLATE_DOWN"))
			{
				int value = Integer.parseInt(commands[1]);
				
				int num = queue.percolateDown(value);
				System.out.println("percolate down " + num);
			}
			else if(commands[0].equalsIgnoreCase("NUM_ELEMENT"))
			{
				System.out.println("element " + queue.size());
			}
			
			instruction = reader.readLine();
		}

	}

}

/**
 * Node class
 * note: percolateDown & percolateUp are an attribute to save how many the node percolated
 * @param <E>
 */
class Node<E extends Comparable<E>>{
	public E element;
	public int percolateDown;
	public int percolateUp;
	
	public Node(E element, int down, int up) {
		this.element = element;
		this.percolateDown = 0;
		this.percolateUp = 0;
	}
	public E getElement(){
		return this.element;
	}
}

/**
 * Priority Queue implemented with Min Binary Heap
 * Please read the tutorial handout for the
 * implementation guidance
 * 
 * To check what parts that have to be completed,
 * just search "TODO"
 * 
 * @author M. Reza Qorib
 * @since 1.7
 * @version 1.0
 */
class MyPriorityQueue<E extends Comparable<E>> {
	private static final int INITIAL_SIZE = 10;
	public ArrayList<Node<E>> data;
	private int size;
	
	/**
	 * Creates a PriorityQueue with the default
	 * initial capacity (10) that orders its elements
	 * according to their natural ordering.
	 */
	public MyPriorityQueue() {
		super();
		this.data = new ArrayList<Node<E>>(INITIAL_SIZE);
		this.size = 0;
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue,
	 * or returns null if this queue is empty.
	 * 
	 * @return the head of this queue, or null if this queue is empty
	 */
	public Node peek() {
		//TODO: implement peek
		if(data.isEmpty())
			return null;
		return data.get(0);
	}
	
	/**
	 * Retrieves and removes the head of this queue,
	 * or returns null if this queue is empty.
	 * 
	 * @return the head of this queue, or null if this queue is empty
	 */
	public E poll() {
		Node top = peek();
		
		//TODO: move the last element to the first index
		if(top == null)
			return null;
			
		swap(size-1, 0);
		data.remove(size-1);
		size--;
		
		if(size > 0)
		{
			Node obj = data.get(0);
			obj.percolateDown += this.percolateDown(0);
		}
		return (E) top.element;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param element - the element to add
	 * @return true if the insert operation succeed, false if not.
	 */
	public boolean add(E element) {
		//TODO: get the desired index (last element)
		int index = size;
		//TODO: insert to the data array
		Node obj = new Node(element, 0, 0);
		data.add(obj);
		size++;
		
		obj.percolateUp += this.percolateUp(index);
		return true;
	}
	
	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return the number of elements in this collection
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns true if this collection contains no elements.
	 * 
	 * @return true if this collection contains no elements
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Returns an ArrayList containing all of the elements in this queue.
	 * The elements are in no particular order.
	 * 
	 * The returned array will be "safe" in that no references to it are maintained by this queue,
	 * but this method only does a shallow copy so all the element reference is still pointed to
	 * the same object. 
	 * 
	 * @return an array containing all of the elements in this queue
	 */
	public ArrayList<Node> toArray() {
		return new ArrayList<Node>(this.data);
	}
	
	/**
	 * Check whether an index is within the data array
	 * 
	 * @param index - the index that want to be checked
	 * @return true if the index is valid, false if not
	 */
	private boolean isOutOfBound(int index) {
		//TODO: check whether the index is still valid
		return (index < 0 || index >= this.size); 
	}
	
	/**
	 * Get the index of the parent node (abstract) of desired index
	 * 
	 * @param index - the index that want to know it's parent (kinda sad :") )
	 * @return index of the parent if found, -1 if not
	 */
	private int getParent(int index) {
		//TODO: get parent index with formula on the tutorial
		if(index == 0)
			return -1;
		else
			return (index-1)/2;
	}
	
	/**
	 * Get the index of the right child node (abstract) of desired index
	 * 
	 * @param index - the index that want to know it's right child
	 * @return index of the right child if found, -1 if not
	 */
	private int getRightChild(int index) {
		//TODO: get right child index with formula on the tutorial
		if(!isOutOfBound(index))
			return 2 * (index + 1);
		return -1;
	}
	
	/**
	 * Get the index of the left child node (abstract) of desired index
	 * 
	 * @param index - the index that want to know it's left child
	 * @return index of the left child if found, -1 if not
	 */
	private int getLeftChild(int index) {
		//TODO: get left child index with formula on the tutorial
		if(!isOutOfBound(index))
			return (2 * index) + 1;
		return -1;
	}
	
	/**
	 * Get the index of the element
	 * @param element
	 * @return index of the element
	 */
	private int getIndex(E element)
	{
		int index = -1;
		for(int i = 0; i < data.size(); i++)
		{
			if((data.get(i).element).compareTo(element) == 0) 
			{
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	/**
	 * Get how many times the element percolated up
	 * @param element
	 * @return times
	 */
	public int percolateUp(E element)
	{
		int index = getIndex(element);
		
		if(index == -1)
			return 0;
		return data.get(index).percolateUp;
	}
	
	/**
	 * Get how many times the element percolated down
	 * @param element
	 * @return times
	 */
	public int percolateDown(E element)
	{
		int index = getIndex(element);
		
		if(index == -1)
			return 0;
		return data.get(index).percolateDown;
	}

	/**
	 * Method to percolate up the element in desired index
	 * @param index - the index of the element to percolate up
	 */
	private int percolateUp(int index) {
		// Check whether the index valid
		if (isOutOfBound(index) || index == 0) {
			return 0;
		}
		// Get the required data
		int parentIndex = getParent(index);
		Node parent = data.get(parentIndex);
		Node current = data.get(index);
		
		// Compare the value of gathered data
		if ((current.element).compareTo(parent.element) < 0) {
			//TODO: implement if current value is smaller than parent
			swap(parentIndex, index);
			int res = percolateUp(parentIndex)+1;
			return res;
		} 
		else {
			//TODO: implement if current value is not smaller than parent
			return 0;
			
		}
	}
	
	/**
	 * Method to percolate down the element in desired index
	 * @param index - the index of the element to percolate down
	 */
	private int percolateDown(int index) {
		//TODO: complete the percolate down implementation below
		if (isOutOfBound(index))
			return 0;
		
		int leftIndex = getLeftChild(index);
		int rightIndex = getRightChild(index);
		Node leftChild = null;
		Node rightChild = null;
		
		if(!isOutOfBound(leftIndex)) 
		{
			leftChild = data.get(leftIndex);
		}
		if(!isOutOfBound(rightIndex)) 
		{
			rightChild = data.get(rightIndex);
		}
	
		Node current = data.get(index);
		
		// cases when percolating
		if( (rightChild != null) && (leftChild != null) ) 
		{
			if( ((current.element).compareTo(rightChild.element) > 0) && ((current.element).compareTo(leftChild.element) > 0) ) 
			{
				if((leftChild.element).compareTo(rightChild.element) < 0) 
				{
					swap(leftIndex, index);
					return percolateDown(leftIndex)+1;
				}
				else 
				{
					swap(rightIndex, index);
					return percolateDown(rightIndex)+1;
				}
			}
			else
			{
				if((current.element).compareTo(rightChild.element) > 0)
				{
					swap(rightIndex, index);
					return percolateDown(rightIndex)+1;
				}
				else if((current.element).compareTo(leftChild.element) > 0)
				{
					swap(leftIndex, index);
					return percolateDown(leftIndex)+1;
				}
			}
		}
		else if((leftChild != null) && ((current.element).compareTo(leftChild.element) > 0)) 
		{
			swap(leftIndex, index);
			return percolateDown(leftIndex)+1;
		}
		return 0;
	}
	
	/**
	 * Swap the element of two indexes
	 * 
	 * @param index1 - the first index to be swapped
	 * @param index2 - the second index to be swapped
	 * @return true if the swap succeed, false if not
	 */
	private boolean swap(int index1, int index2) {
		if (isOutOfBound(index1) || isOutOfBound(index2)){
			return false;
		}
		
		Node temp = data.get(index1);
		data.set(index1, data.get(index2));
		data.set(index2, temp);
		
		return true;
	}
}