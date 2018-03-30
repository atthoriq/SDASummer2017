import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.lang.Comparable;

/**
 * File Storage - Tugas Pemrograman 3 SDA
 * @author Muhammad At Thoriq
 * Start from 2/12/17
 */
public class SDA1606918484T3 
{
	public static void main(String[] args) throws IOException 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Storage dir = new Storage();
		
		String input = reader.readLine();
		
		while(input != null)
		{
			if(input.equals(""))
				break;
			
			StringTokenizer token = new StringTokenizer(input);
			String operation = token.nextToken();
			
			String newFolder, destFolder, folder, removed, 
			output, searched, fileName, fileType;
			
			switch(operation)
			{
			case "add":
				newFolder = token.nextToken();
				destFolder = token.nextToken();
				dir.add(newFolder, destFolder);
				break;
				
			case "insert":
				fileName = token.nextToken(" .");
				fileType = token.nextToken(" .");
				int fileSize = Integer.parseInt(token.nextToken(" ."));
				destFolder = token.nextToken(" .");

				output = dir.insert(fileName, fileType, fileSize, destFolder);
				if(!output.equals(""))
					System.out.println(output);
				break;
				
			case "remove":
				removed = token.nextToken();
				output = dir.remove(removed);
				
				if(!output.equals(""))
					System.out.println(output);
				break;
				
			case "search":
				searched = token.nextToken();
				
				output = dir.find(searched);
				if(!output.equals(""))
					System.out.print(output);
				break;
				
			case "print":
				folder = token.nextToken();
				
				System.out.print(dir.print(folder));
				break;
			}
			
			input = reader.readLine();
		}
		
	}
}

/**
 * Tree Implementation
 */
class Storage
{
	private Folder root;
	private Cloud cloud = new Cloud();
	
	public Storage() 
	{
		this.root = new Folder("root");
	}
	
	/**
	 * The data that will be put in a Tree, wrapped by a Node
	 */
	class Node implements Comparable<Node>
	{
		String name;
		Folder parent;
		String contentType;
		boolean flag;
		int size;
		
		/**
		 * To know node's size
		 * @return integer size
		 */
		public int size()
		{
			if(this instanceof Folder)
			{
				Folder tempFolder = (Folder) this;
				int size = 1;
				if(tempFolder.type == Folder.class)
				{
					for(Folder sub: tempFolder.subFolders)
					{
						size += sub.size();
					}
				}
				else if(tempFolder.type == File.class)
				{
					for(File file: tempFolder.files)
					{
						size += file.size();
					}
				}
				return size;
			}
			return size;
		}
		
		/**
		 * Un-link relation between parent and child
		 */
		void remove()
		{
			parent.remove(this);
		}

		@Override
		public int compareTo(Node other) {
			// TODO Auto-generated method stub
			if (!this.name.equals(other.name)) 
			{
                return this.name.compareTo(other.name);
            } 
			else if (this.contentType != null && other.contentType != null && !this.contentType.equals(other.contentType)) 
			{
                return this.contentType.compareTo(other.contentType);
            } 
			else 
			{
                return other.size() - this.size();
            }
		}
		
	}
	
	/**
	 * Object Folder
	 */
	class Folder extends Node
	{	
		private Class type;
        private List<File> files;
        private TreeSet<Folder> subFolders;
        
		public Folder(String name) 
		{
			this.name = name;
			this.type = null;
			cloud.add(this);
		}
		
		/**
		 * Method add new folder to existing folder
		 * @param newFolder
		 */
		void add(Folder newFolder)
		{
			newFolder.parent = this;
			if(this.type != Folder.class)
			{
				if(this.type == File.class)
				{
					newFolder.type = File.class;
					newFolder.files = this.files;
					newFolder.contentType = this.contentType;
					
					for(File file: newFolder.files)
					{
						file.parent = newFolder;
					}
					
					this.files = null;
				}
				this.type = Folder.class;
				this.subFolders = new TreeSet<Folder>(); 
			}
			newFolder.parent = this;
			this.subFolders.add(newFolder);
		}
		
		/**
		 * This method is a helper method of insert method and is a part of method to
		 * find available folder to insert a new file.
		 * This method focuses on iterating a folder inside a folder.
		 * @param file; a new file.
		 * @param goParent; a boolean as a flag of whether iterator should go back to
		 * 					its parent or not
		 * @return the available folder (if the available folder is 'null', it means nothing available)
		 */
		private Folder findAvailableFolder(File file, boolean goParent)
		{
			if(this.type == null)
				return this;
			else if(this.type == File.class)
			{
				if(this.contentType.equals(file.contentType))
					return this;
			}
			else
			{
				for(Folder sub: this.subFolders)
				{
					Folder available = sub.findAvailableFolder(file, false);
					if(available != null)
						return available;
				}
			}
			
			if(goParent && this.parent != null)
				return parent.findAvailableFolder(file, this);
			return null;
		}
		
		/**
		 * This method is another helper method of insert method and is a part of method
		 * to find available folder to insert a new file.
		 * This method focuses on turning iterator back to top of list.
		 * Overall this method could iterate a list of data circularly.
		 * @param file; a new file
		 * @param unavailable; the unavailable folder. (so the iterator iterates after the unavailable one)
		 * @return the available folder (if the available folder is 'null', it means nothing available)
		 */
		private Folder findAvailableFolder(File file, Folder unavailable)
		{
			SortedSet<Folder> head = subFolders.headSet(unavailable);
			SortedSet<Folder> tail = subFolders.tailSet(unavailable);
			
			Iterator<Folder> iterHead = head.iterator();
			Iterator<Folder> iterTail = tail.iterator();
            iterTail.next();
            
            while (iterTail.hasNext() || iterHead.hasNext()) 
            {
            	Folder subFolder;
            	if(iterTail.hasNext())
            		subFolder = iterTail.next();
            	else
            		subFolder = iterHead.next();
            	
                Folder ret = subFolder.findAvailableFolder(file, false);
                if (ret != null) 
                	return ret;
            }
            
            if(parent != null)
            	return parent.findAvailableFolder(file, this);
            return null;
		}
		
		/**
		 * The main method that aims to insert a new file inside a folder.
		 * @param file; a new file.
		 * @return String; an output that is tailored to the needs of program.
		 */
		String insert(File file)
		{
			Folder availableFolder = findAvailableFolder(file, true);
			if(availableFolder != null)
			{
				availableFolder.type = File.class;
				file.parent = availableFolder;
				availableFolder.contentType = file.contentType;
				
				if(availableFolder.files == null)
				{
					availableFolder.files = new ArrayList<>();
				}
				availableFolder.files.add(file);
				cloud.add(file);
				String output = file.name + "." + file.contentType + " added to " + file.parent.name;
				return output;
			}
			return "";
		}
		
		/**
		 * This method has a function to remove whether folder or file inside a folder.
		 * @param 'thing' could be a file or folder, because those are wrapped by a Node.
		 */
		void remove(Node thing)
		{
			if(thing instanceof Folder)
				this.subFolders.remove((Folder) thing);
			else if(thing instanceof File)
				this.files.remove((File) thing);
			
			if(this.subFolders != null && this.subFolders.isEmpty())
			{
				this.type = null;
			}
			if(this.files != null && this.files.isEmpty())
			{
				this.type = null;
				this.contentType = null;
			}
		}
		
		
	}

	/**
	 * Object File
	 */
	class File extends Node
	{
		public File(String name, String type, int size) 
		{
			this.name = name;
			contentType = type;
			this.size = size;
		}
	}
	
	/**
	 * Helper class to save all of the data
	 */
	class Cloud
	{
		private HashMap<String, Folder> allFolder = new HashMap<String, Folder>();
        private HashMap<String, List<File>> allFile = new HashMap<String, List<File>>();

        void add(Folder target)
        {
            allFolder.put(target.name, target);
        }

        void add(File target)
        {
            if (!allFile.containsKey(target.name)) 
            	allFile.put(target.name, new ArrayList<File>());
            allFile.get(target.name).add(target);
        }
        
        void remove(Folder target)
        {
            allFolder.remove(target.name);
        }

        void remove(String target)
        {
            allFile.remove(target);
        }
        
        Folder getFolder(String key)
        {
            return allFolder.get(key);
        }

        List<File> getFiles(String key)
        {
            return allFile.get(key);
        }

	}

	/**
	 * This method is a public method which has a function to insert something into the file system.
	 * @param name; file name
	 * @param type; type of file
	 * @param size; file's size
	 * @param destination; a folder that would be containing the new file
	 * @return String; an output that is tailored to the needs of program.
	 */
	public String insert(String name, String type, int size, String destination)
	{
		File newFile = new File(name, type, size);
		Folder fixedDestination = cloud.getFolder(destination);
		
		return fixedDestination.insert(newFile);
	}
	
	/**
	 * This method is a public method which has a function to add a new folder into the file system
	 * @param folderName; a new folder's name
	 * @param destinationName; a folder that would be containing the new folder
	 */
	public void add(String folderName, String destinationName)
	{
		Folder newFolder = new Folder(folderName);
		Folder destination = cloud.getFolder(destinationName);
		
		destination.add(newFolder);
	}
	
	/**
	 * This method is a public method which has a function to remove something
	 * whether a folder or a file from the file system
	 * @param something; a folder or a file's name that would be removed
	 * @return String; an output that is tailored to the needs of program.
	 */
	public String remove(String something)
	{
		Folder targetFolder = cloud.getFolder(something);
		List<File> targetFile = cloud.getFiles(something);
		if(targetFolder != null)
			return remove(targetFolder);
		else if(targetFile != null)
			return remove(targetFile);
		return "";
	}
	
	/**
	 * This method is a helper method of remove method.
	 * This method focuses to clear everything inside a folder and
	 * remove a folder from cloud database
	 * @param folder which its contents would be removed
	 */
	void removeHelper(Folder folder)
	{
		if(folder.type == Folder.class)
		{
			for(Folder sub: folder.subFolders)
			{
				removeHelper(sub);
			}
		}
		else if(folder.type == File.class)
		{
			for(File file: folder.files)
			{
				String name = file.name;
				Iterator<File> iterator = cloud.getFiles(name).iterator();
				
				while(iterator.hasNext())
				{
					File subfile = iterator.next();
					if(subfile.parent == folder)
					{
						iterator.remove();
					}
				}
				
				if(cloud.getFiles(name).isEmpty())
				{
					cloud.remove(name);
				}
			}
			folder.files.clear();
		}
		cloud.remove(folder);
	}
	
	/**
	 * This method is a helper method of remove method
	 * This method focuses on removing the folder given by user input.
	 * @param folder that would be removed
	 * @return String; an output that is tailored to the needs of program.
	 */
	String remove(Folder folder)
	{
		removeHelper(folder);
		folder.remove();
		cloud.remove(folder);
		String output = "Folder " + folder.name + " removed";  
		return output;
	}
	
	/**
	 * This method is a helper method of remove method
	 * This method focuses on removing the file given by user input from 
	 * the cloud database
	 * @param files; list of file that each file has the same name each other
	 * @return String; an output that is tailored to the needs of program.
	 */
	String remove(List<File> files)
	{
		String name = files.get(0).name;
		Iterator<File> iterator = files.iterator();
		int sumFiles = 0;
		
		while(iterator.hasNext())
		{
			File file = iterator.next();
			iterator.remove();
			file.remove();
			sumFiles++;
		}
		String output = sumFiles + " File " + name + " removed";
		return output;
	}
	
	/**
	 * This method is a public method to search anything from the File System
	 * @param something; a folder or a file's name that would be searched
	 * @return String; an output that is tailored to the needs of program.
	 */
	public String find(String something)
	{
		if(cloud.getFolder(something) != null)
			return find(cloud.getFolder(something));
		else if(cloud.getFiles(something) != null)
			return find(cloud.getFiles(something));
		return "";
	}
	
	/**
	 * This method is a helper method of find method which has a function
	 * to set a flag/mark a Node target from the root to the way it placed
	 * @param target; Node that would be marked
	 * @param flag; a boolean if only if it is true; it is marked.
	 */
	void setFlag(Node target, boolean flag) {
        target.flag = flag;
        if (target.parent != null) 
        	setFlag(target.parent, flag);
    }

	/**
	 * This method is a helper method of find method which has a function
	 * to find the way from root to the target (in this method, the target is Folder) is placed and return an output
	 * @param target; Node that would be marked
	 * @return String; an output that is tailored to the needs of program.
	 */
    String find(Node target) {
        setFlag(target, true);
        String ret = print(root, 0, "search");
        setFlag(target, false);
        return ret;
    }

    /**
     * This method is another helper method of find method which has a function
     * to find the way from root to the target (in this method, the target is File) is placed and return an output
     * @param target; list of file from cloud database
     * @return String; an output that is tailored to the needs of program.
     */
    String find(List<File> target) {
        for (File e: target) 
        { 
        	setFlag(e, true); 
        }
        String ret = print(root, 0, "search");
        
        for (File e: target)
        {
        	setFlag(e, false); 
        }
        return ret;
    }
    
    /**
     * This method is a public method to print a folder and its contents.
     * @param folderName; a folder's name that would be printed
     * @return String; an output that is tailored to the needs of program.
     */
    public String print(String folderName)
    {
    	Folder folder = cloud.getFolder(folderName);
    	return print(folder,0, "print");
    }
    
    /**
     * This method is a helper method of print method and find method which has a function
     * to search the target from the root and note its way in String variable
     * @param folder that would be printed
     * @param depth means a folder layer number
     * @param kindOfPrint is a string to determine if this method used for search method xor print method 
     * @return String; an output that is tailored to the needs of program.
     */
    String print(Folder folder, int depth, String kindOfPrint) {
    	StringBuilder str = new StringBuilder(finalPrint(depth,folder,true));
    	if(kindOfPrint.equals("search"))
    		str = new StringBuilder(finalPrint(depth,folder,false));
    	
        if (folder.type == Folder.class)
        {
            for (Folder sub: folder.subFolders)
            {
            	if(sub.flag && kindOfPrint.equals("search"))
            		str.append(print(sub,depth+1, kindOfPrint));
            	else if(kindOfPrint.equals("print"))
            		str.append(print(sub,depth+1, kindOfPrint));
            }
        }
        else if (folder.type == File.class)
        {
            Collections.sort(folder.files);
            for (File sub: folder.files)
            {
            	if(sub.flag && kindOfPrint.equals("search"))
            		str.append(finalPrint(depth+1,sub,false));
            	else if(kindOfPrint.equals("print"))
            		str.append(finalPrint(depth+1,sub,true));
            }
        }
        return str.toString();
    }
    
    /**
     * This method is a helper method of print method which has a function
     * to create an output according to program's needs.
     * @param depth means a folder layer number
     * @param node means the target whether it is a folder or a file
     * @param printSize is a boolean to determine if this method should print the node's size or not
     * @return String; an output that is tailored to the needs of program.
     */
    String finalPrint(int depth, Node node, boolean printSize)
    {
        String spaces = new String(new char[depth]).replace("\0", "  ");
        String ret = spaces + "> " + node.name;
        if (node instanceof File) 
        	ret += "." + node.contentType;
        if(printSize)
        	return ret + " " + node.size() + "\n";
        return ret + "\n";
    }

}