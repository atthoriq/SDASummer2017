import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * 
 * LAB 2B - Kolektor Mobil
 * @author Thow
 * 15/09/2017
 */
public class SDA1718L2B 
{
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		LinkedList<String> parked = new LinkedList<>(); // array mobil yang terparkir
		ArrayList<String> mobilMogok = new ArrayList<>(); // array yang berisi mobil mogok
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str = in.readLine();
		
		while(str != null)
		{
			String[] splitted = str.split(" ");
			
			if(splitted[0].equalsIgnoreCase("masuk"))
			{
				String jenisMobil = splitted[1];
				String arahMasuk = splitted[2];
				if(jenisMobilValidation(jenisMobil) && !parked.contains(jenisMobil))
					masuk(jenisMobil, arahMasuk, parked);
			}
			else if(splitted[0].equalsIgnoreCase("keluarkan"))
			{
				String jenisMobil = splitted[1];
				keluarkan(jenisMobil, parked, mobilMogok);
			}
			else if(splitted[0].equalsIgnoreCase("mogok"))
			{
				String jenisMobil = splitted[1];
				mogok(jenisMobil, mobilMogok);
			}
			else if(splitted[0].equalsIgnoreCase("servis"))
			{
				String jenisMobil = splitted[1];
				servis(jenisMobil, mobilMogok);
			}
			
			str = in.readLine();
		}
	}
	
	/**
	 * Method yang memvalidasi jenis mobil
	 * @param jenisMobil
	 * @return
	 */
	public static boolean jenisMobilValidation(String jenisMobil)
	{
		if(jenisMobil.length() < 20)
			return true;
		return false;
	}
	
	/**
	 * method untuk memasukan mobil kedalam garasi
	 * @param jenisMobil
	 * @param arahMasuk
	 * @param parked
	 */
	public static void masuk(String jenisMobil, String arahMasuk, LinkedList<String> parked)
	{
		if(arahMasuk.equalsIgnoreCase("barat"))
			parked.addFirst(jenisMobil);
		else if(arahMasuk.equalsIgnoreCase("timur"))
			parked.addLast(jenisMobil);
		System.out.println(jenisMobil + " masuk melalui pintu " + arahMasuk);
	}
	
	/**
	 * method untuk mengeluarkan mobil keluar garasi
	 * @param jenisMobil
	 * @param parked
	 * @param mobilMogok
	 */
	public static void keluarkan(String jenisMobil, LinkedList<String> parked, ArrayList<String> mobilMogok)
	{
		int counterBarat = 0;
		int counterTimur = 0;
		boolean mogokBarat = false;
		boolean mogokTimur = false;
		String mobilBarat = "";
		String mobilTimur = "";
		
		if (!(parked.contains(jenisMobil))) 
		{
			System.out.println(jenisMobil + " tidak ada di garasi");
		} 
		else if (mobilMogok.contains(jenisMobil)) 
		{
			System.out.println("Mobil " + jenisMobil + " sedang mogok");
		} 
		else 
		{
			// Iterasi dari Barat
			Iterator<String> iterator = parked.listIterator();
			while(iterator.hasNext()) 
			{
				counterBarat++;
				String temp = iterator.next();
				if (mobilMogok.contains(temp))
				{
					mogokBarat = true;
					mobilBarat = temp;
				}
				else if (temp.equals(jenisMobil))
					break;
			}
			
			// Iterasi dari Timur
			iterator = parked.descendingIterator();
			while(iterator.hasNext()) 
			{
				counterTimur++;
				String temp = iterator.next();
				if (mobilMogok.contains(temp)) 
				{
					mogokTimur = true;
					mobilTimur = temp;
				} 
				else if (temp.equals(jenisMobil)) 
					break;
			}
			
			// Kondisi jika ada yang mogok di Timur atau Barat atau Keduanya
			if (mogokTimur && mogokBarat)
				System.out.println(jenisMobil + " tidak bisa keluar, mobil " + mobilBarat + " dan " + mobilTimur + " sedang mogok");
			
			else if (mogokBarat || (counterBarat > counterTimur) && !mogokTimur) 
			{
				parked.remove(jenisMobil);
				System.out.println(jenisMobil + " keluar melalui pintu TIMUR");
			} 
			
			else if (mogokTimur || (counterTimur >= counterBarat) && !mogokBarat) 
			{
				parked.remove(jenisMobil);
				System.out.println(jenisMobil + " keluar melalui pintu BARAT");
			}
			
		}	
	}
	
	/**
	 * method untuk mendefinisikan mobil mogok
	 * @param jenisMobil
	 * @param mobilMogok
	 */
	public static void mogok(String jenisMobil, ArrayList<String> mobilMogok)
	{
		if(!mobilMogok.contains(jenisMobil))
			mobilMogok.add(jenisMobil);
	}
	
	/**
	 * method untuk mendefinisikan mobil yang telah diservis
	 * @param jenisMobil
	 * @param mobilMogok
	 */
	public static void servis(String jenisMobil, ArrayList<String> mobilMogok)
	{
		if(mobilMogok.contains(jenisMobil))
			mobilMogok.remove(jenisMobil);
	}
}
