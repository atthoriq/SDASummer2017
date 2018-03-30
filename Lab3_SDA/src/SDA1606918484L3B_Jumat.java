import java.util.*;
import java.io.*;

/**
 * LAB3SDA - Bahagia
 * @author Muhammad At Thoriq - 1606918484
 * 29 September 2017
 */
public class SDA1606918484L3B_Jumat 
{
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        TreeMap<Integer, Integer> map = new TreeMap<>();
        
        int n = Integer.parseInt(in.readLine());
        int jarak = 0;
        
        stairs(map);
        
        while(n!=0)
        {
            jarak = Integer.parseInt(in.readLine());
            int langkah = map.get(jarak);
            System.out.println(langkah);
            
            n--;
        }
	}
	
	/**
	 * method mencari langkah tercepat menuruni anak tangga untuk Riicky
	 * @param map
	 */
	static void stairs(TreeMap<Integer,Integer> map)
	{
		map.put(0, 0);
		
		// iterasi langkah untuk 10000 tangga dan memberi nilai temporary
		for(int anakTangga = 1; anakTangga < 10001; anakTangga++)
		{
			int temp = map.get(anakTangga-1) + 1;
			map.put(anakTangga, temp);
			
			// iterasi mencari faktor dari setiap nilai tangga dan memberi nilai langkah yang tercepat
			for(int faktor = 1; faktor<=anakTangga; faktor++)
			{
				if(faktor <= anakTangga/faktor && anakTangga%faktor==0)
				{
					int langkah = Math.min(map.get(anakTangga), map.get(anakTangga/faktor)+1);
					map.put(anakTangga, langkah);
				}
			}
		}
	}	
}