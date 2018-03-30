import java.util.*;
import java.io.*;
import java.awt.*;

/**
 * Lab 4 SDA - YugiAhh
 * @author Muhammad At Thoriq
 * 1606918484
 * 6/10/2017
 */
public class SDA1606918484L4B 
{
	private static ArrayList<Card> cards = new ArrayList<>();
	
	/**
	 * Method main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String str = in.readLine();
		
		while( str != null )
		{
			String[] command = str.split(" ");
			
			if(command[0].equalsIgnoreCase("pick"))
			{
				pick(command[1], Integer.parseInt(command[2]));
			}
			else if(command[0].equalsIgnoreCase("attack"))
			{
				attack();
			}
			else if(command[0].equalsIgnoreCase("defense"))
			{
				defense();
			}
			else if(command[0].equalsIgnoreCase("see"))
			{
				seeCard();
			}
			
			str = in.readLine();
		}
		
	}
	
	/**
	 * Method pick kartu
	 * @param nama -> nama kartu yang diambil
	 * @param power -> power kartu yang diambil
	 */
	public static void pick(String nama, int power)
	{
		cards.add(new Card(nama,power));
		System.out.println(nama + " dengan power " + power + " diambil");
		
		quicksort(cards, 0, cards.size()-1);
	}
	
	/**
	 * Method attack yang mengeluarkan kartu dengan power terbesar
	 */
	public static void attack()
	{
		if(cards.size() != 0)
		{
			String name = cards.get(0).name;
			int power = cards.get(0).power;
			System.out.println(name + " " + power + " dikeluarkan");
			
			cards.remove(0);
		}
		
		else
		{
			System.out.println("Tidak bisa melakukan Attack");
		}
	}
	
	/**
	 * Method defense yang mengeluarkan 3 kartu terendah
	 */
	public static void defense()
	{
		if(cards.size() < 3)
		{
			System.out.println("Tidak bisa melakukan Defense");
			return;
		}
		
		for(int i = 0; i < 3; i++)
		{
			String name = cards.get(cards.size()-1).name;
			int power = cards.get(cards.size()-1).power;
			System.out.println(name + " " + power + " dikeluarkan");
			
			cards.remove(cards.size()-1);
		}
	}
	
	/**
	 * Method mencetak kartu yang ada sesuai urutan power dari yang terbesar
	 */
	public static void seeCard()
	{
		if(cards.size() == 0)
		{
			System.out.println("Kartu kosong");
			return;
		}
		
		for(Card c: cards)
		{
			String name = c.name;
			int power = c.power;
			System.out.println(name + " " + power);
		}
	}
	
	/**
	 * Method sorting kartu
	 * @param arr -> arraylist yang ingin diurutkan
	 * @param low -> index terendah array yang ingin diurutkan
	 * @param high -> index tertinggi array yang ingin diurutkan
	 */
	public static void quicksort(ArrayList<Card> arr, int low, int high)
	{
		int i = low;
		int j = high;
		Card pivot = arr.get((low + high)/2);
		
		if(arr.size() > 1)
		{
			while(i <= j)
			{
				
				while(arr.get(i).compareTo(pivot) < 0)
				{
					i++;
				}
				
				while(arr.get(j).compareTo(pivot) > 0)
				{
					j--;
				}
				
				if(i <= j)
				{
					Card temp = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j, temp);
					
					i++;
					j--;
				}
				
			}
			if(low < j)
			{
				quicksort(arr, low, j);
			}
			if(i < high)
			{
				quicksort(arr, i, high);
			}
		}
	}
}

/**
 * Kelas objek kartu yang memiliki atribut nama dan power.
 * Implementasi Comparable agar bisa dibandingkan
 * @author Muhammad At Thoriq
 * 1606918484
 * 6/10/2017
 */
class Card implements Comparable<Card>
{
	public String name;
	public Integer power;
	
	public Card(String name, int power)
	{
		this.name = name;
		this.power = power;
	}

	@Override
	public int compareTo(Card other) 
	{
		// TODO Auto-generated method stub
		if(this.power != other.power)
		{
			return -(this.power.compareTo(other.power));
		}
		else
		{
			return this.name.compareTo(other.name);
		}
	}
}
