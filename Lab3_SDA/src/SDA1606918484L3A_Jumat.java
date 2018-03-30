import java.util.*;
import java.io.*;

/**
 * LAB 3 SDA - Pinder
 * @author Muhammad At Thoriq - 1606918484
 * 28 September 2017
 */
public class SDA1606918484L3A_Jumat 
{
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(in.readLine());
		
		// iterasi input
		while(n!=0)
		{
			String str = in.readLine();
			if(str.length()%2 !=0)
				System.out.println("TIDAK BAGUS");
			else
			{
				if(pinder(str))
					System.out.println("BAGUS");
				else
					System.out.println("TIDAK BAGUS");
			}
			
			n--;
		}
		
	}
	
	/**
	 * method menentukan bagus atau tidak
	 * @param a
	 * @return
	 */
	static boolean pinder(String a)
	{
		String pembuka = "";
		String update = a;
		boolean result = true;
		
		// iterasi string a
		while(update.length() != 0)
		{
			if(isPembuka(update.charAt(0)))
			{
				pembuka = "" + update.charAt(0) + pembuka;
				update = update.substring(1);
			}
			else if(isPenutup(update.charAt(0)) && pembuka.length() != 0)
			{
				if(pembuka.length() == 1)
				{
					if(check(pembuka.charAt(0), update.charAt(0)))
					{
						pembuka = "";
						update = update.substring(1);
						result = true;
					}
					else
					{
						update = "";
						result = false;
					}
				}
				else
				{
					if(check(pembuka.charAt(0), update.charAt(0)))
					{
						pembuka = pembuka.substring(1);
						update = update.substring(1);
						result = true;
					}
					else
					{
						update = "";
						result = false;
					}
				}
			}
			else if(isPenutup(update.charAt(0)) && pembuka.length() == 0)
			{
				update = "";
				result = false;
			}
		}
		if(pembuka.length() > 0) result = false;
		return result;
	}
	
	/**
	 * method cek apakah char merupakan char pembuka
	 * @param a
	 * @return
	 */
	static boolean isPembuka(char a)
	{
		if(a == 'L' || a == 'V' || a == '<')
			return true;
		return false;
	}
	
	/**
	 * method cek apakah char merupakan char penutup
	 * @param a
	 * @return
	 */
	static boolean isPenutup(char a)
	{
		if(a == 'O' || a == 'E' || a == '3')
			return true;
		return false;
	}
	
	/**
	 * method cek dua char apakah merupakan pasangan
	 * @param a
	 * @param b
	 * @return
	 */
	static boolean check(char a, char b)
	{
		if(a == 'L' && b == 'O')
			return true;
		else if(a == 'V' && b == 'E')
			return true;
		else if(a == '<' && b == '3')
			return true;
		return false;
	}
}
