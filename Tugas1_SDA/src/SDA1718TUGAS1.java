import java.util.*;
import java.io.*;

/**
 * 
 * Tugas 1 SDA - Browser Kucing
 * @author Muhammad At Thoriq - 1606918484
 *
 */
public class SDA1718TUGAS1 
{
	
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(input.readLine());
		
		// list sebagai tab dan stack sebagai halaman
		List<Stack<String>> tab = new ArrayList<>();
		// treeset untuk history agar otomatis terurut berdasarkan alpabet
		TreeSet<String> history = new TreeSet<>();
		
		tab.add(new Stack<String>());
		int activePage = 0;
		
		// iterasi sebanyak jumlah baris sesuai input user
		while(n!=0)
		{
			String command = input.readLine();
			StringTokenizer tokenizer = new StringTokenizer(command,";");
			String instruction = tokenizer.nextToken().toLowerCase();
			
			
			switch(instruction)
			{
			case "view":
				String x = tokenizer.nextToken();
				String y = tokenizer.nextToken();
				view(history,tab,x,y,activePage);
				break;
			case "back":
				back(tab, activePage);
				break;
			case "newtab":
				newtab(tab);
				break;
			case "changetab":
				int j = Integer.parseInt(tokenizer.nextToken());
				int changed = changeTab(tab, j, activePage);
				activePage = changed;
				break;
			case "history":
				history(history);
				break;
			}
			
			n-=1;
		}
		
	}
	
	/**
	 * Method melihat suatu halaman
	 * @param history
	 * @param tab
	 * @param x
	 * @param y
	 * @param activePage
	 */
	public static void view(TreeSet<String> history, List<Stack<String>> tab, String x, String y, int activePage)
	{
		String viewed = "VIEWING([" + x + ":" + y + "])";
		tab.get(activePage).push(viewed);
		history.add(x + ":" + y);
		
		System.out.println(viewed);
	}
	
	/**
	 * Method kembali dari halaman ke halaman sebelumnya
	 * @param tab
	 * @param activePage
	 */
	public static void back(List<Stack<String>> tab, int activePage)
	{
		if(tab.get(activePage).size() != 0)
			tab.get(activePage).pop();
		if(tab.get(activePage).size() == 0)
			System.out.println("EMPTY TAB");
		else
			System.out.println(tab.get(activePage).peek());
	}
	
	/**
	 * Method menambahkan tab baru namun tidak berpindah tab
	 * @param tab
	 */
	public static void newtab(List<Stack<String>> tab)
	{
		tab.add(new Stack<String>());
	}
	
	/**
	 * method mengecek apakah dapat memindahkan tab, dan mereturn activePage yang seharusnya
	 * @param tab
	 * @param j
	 * @param activePage
	 * @return
	 */
	public static int changeTab(List<Stack<String>> tab, int j, int activePage)
	{
		if(j > tab.size()-1)
		{
			System.out.println("NO TAB");
			return activePage;
		}
		else
		{
			System.out.println("TAB: " + j);
			return j;
		}
	}
	
	/**
	 * Method mencetak history
	 * @param history
	 */
	public static void history(TreeSet<String> history)
	{
		if(history.size() == 0)
			System.out.println("NO RECORD");
		else
		{
			for(String viewed: history)
			{
				System.out.println(viewed);
			}
		}
	}
	
}
