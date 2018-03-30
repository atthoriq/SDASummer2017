import java.util.*;
import java.io.*;

/**
 * LAB 3 SDA - Pinder
 * @author Muhammad At Thoriq - 1606918484
 * 28 September 2017
 */
public class soalA-old 
{
	
	public static void main(String[] args) throws IOException
	{
		String s = "LV<VLOE3EO";
		System.out.println(pinder(s));
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		
//		int n = Integer.parseInt(in.readLine());
//		
//		while(n!=0)
//		{
//			String str = in.readLine();
//			if(str.length()%2 !=0)
//				System.out.println("TIDAK BAGUS");
//			else
//			{
//				if(pinder(str))
//					System.out.println("BAGUS");
//				else
//					System.out.println("TIDAK BAGUS");
//			}
//			
//			n--;
//		}
		
	}
	
	static boolean pinder(String a)
	{
		String awal = a.substring(0,1);
		String akhir = a.substring(1,2);
		
		if(a.length() == 2)
			return check(awal, akhir);
		
		if(check(awal,akhir))
			return pinder(a.substring(2));
		
		else if(!check(awal,akhir))
		{
			boolean result = false;
			boolean newResult = false;
			
			for(int i=3; i<a.length(); i+=2)
			{
				if(check(awal, a.substring(i, i+1)))
				{
					if(a.length()>i+1)
						newResult = pinder(a.substring(1, i)) && pinder(a.substring(i+1));
					newResult = pinder(a.substring(1, i));
				}
				result = result || newResult;
			}
			
			return result;
		}
		return false;
	}
	
	static boolean check(String a, String b)
	{
		if(a.equals("L") && b.equals("O"))
			return true;
		else if(a.equals("V") && b.equals("E"))
			return true;
		else if(a.equals("<") && b.equals("3"))
			return true;
		return false;
	}
}
