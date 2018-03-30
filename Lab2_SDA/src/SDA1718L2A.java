import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * 
 * Lab 2A - Printerin.id
 * @author Muhammad At Thoriq - 1606918484
 * 15/09/2017
 *
 */

public class SDA1718L2A 
{
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{
		TreeMap<String, Integer> dataQueue = new TreeMap(); //Map antrean
		ArrayList<String> printed = new ArrayList<>(); //Array user yang pernah mencetak
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str = in.readLine();
		
		while(str != null) 
		{
			
			String[] splitted = str.split(" ");
			
			if(splitted[0].equalsIgnoreCase("print") && splitted.length == 1) 
			{
				print(dataQueue, printed);
			}
			else if(splitted[1].equalsIgnoreCase("submit") && splitted.length == 4) 
			{
				int pages = Integer.valueOf(splitted[2]);
				String npm = splitted[0];
				
				if(npmValidation(npm, dataQueue) && pages<1000) //input validation
					submit(npm, pages, dataQueue);
			}
			else if(splitted[1].equalsIgnoreCase("cancel") && splitted.length == 2) 
			{
				String npm = splitted[0];
				if(npmValidation(npm, dataQueue))
					cancel(npm, dataQueue);
			}
			else if(splitted[0].equalsIgnoreCase("status") && splitted.length == 2) 
			{
				String npm = splitted[1];
				if(npmValidation(npm, dataQueue))
					status(npm, printed, dataQueue);
			}
			
			str = in.readLine(); //go to next line
			
		}
	}
	
	/**
	 * 
	 * method untuk memvalidasi npm
	 * @param npm
	 * @param map
	 * @return
	 */
	public static boolean npmValidation(String npm, Map map) 
	{
		if(npm.length() == 8 && npm.charAt(0) == '1')
			return true;
		return false;
	}
	
	/**
	 * 
	 * method submit antrean
	 * @param npm
	 * @param pages
	 * @param map
	 */
	public static void submit(String npm, int pages, TreeMap<String,Integer> map) 
	{
		if(pages > 0 && pages<=10) 
		{
			if(!map.containsKey(npm)) 
			{
				map.put(npm, pages);
				System.out.println("Submisi " + npm + " telah diterima");
			}
			else 
			{
				System.out.println("Harap tunggu hingga submisi sebelumnya selesai diproses");
			}
		}
		else
		{
			System.out.println("Jumlah halaman submisi " + npm + " terlalu banyak");
		}
	}
	
	/**
	 * 
	 * Method untuk mencetak berkas antrean
	 * @param map
	 * @param printed
	 */
	public static void print(TreeMap<String,Integer> map, ArrayList<String> printed) 
	{
		if(map.isEmpty())
			System.out.println("Antrean kosong");
		else
		{
			ArrayList<String> removed = new ArrayList<>(); //Array untuk menampung NPM yang berkasnya sudah dicetak
			int maxHal = 10; //maksimal halaman satu kali mencetak
			for(String npm: map.keySet())
			{
				maxHal -= map.get(npm);
				if(maxHal >= 0)
				{
					System.out.println("Submisi " + npm + " telah dicetak sebanyak " + map.get(npm) + " halaman");
					printed.add(npm);
					removed.add(npm);
				}
				else
					break;
			}
			
			for(String data: removed) //iterasi NPM yang berkasnya sudah dicetak dan menghapus NPM tersebut pada MAP
			{
				map.remove(data);
			}
		}
		
	}
	
	/**
	 * 
	 * method untuk membatalkan submisi
	 * @param npm
	 * @param map
	 */
	public static void cancel(String npm, TreeMap<String, Integer> map) 
	{
		if(map.containsKey(npm))
		{
			map.remove(npm);
			System.out.println("Submisi " + npm + " dibatalkan");
		}
		else
			System.out.println(npm + " tidak ada dalam antrean");
	}
	
	/**
	 * 
	 * method untuk memberikan status submisi
	 * @param npm
	 * @param printed
	 * @param map
	 */
	public static void status(String npm, ArrayList<String> printed, TreeMap<String, Integer> map) 
	{
		if(printed.contains(npm))
		{
			System.out.println("Submisi " + npm + " sudah diproses");
		}
		else if(!printed.contains(npm) && !map.containsKey(npm))
		{
			System.out.println(npm + " tidak ada dalam sistem");
		}
		else if(map.containsKey(npm))
		{
			System.out.println("Submisi " + npm + " masih dalam antrean");
		}
	}
	
}
