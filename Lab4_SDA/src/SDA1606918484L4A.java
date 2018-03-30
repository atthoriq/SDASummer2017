import java.util.*;
import java.io.*;

/**
 * Lab 4 SDA - Quicksort
 * @author Muhammad At Thoriq
 * 1606918484
 * 5/10/2017
 */
public class SDA1606918484L4A 
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
		int[] numbers = new int[n];
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		// input dimasukkan ke dalam array
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] sorted = quicksort(numbers, 0, numbers.length-1);
		for(int i = 0; i < sorted.length-1; i++)
			System.out.print(sorted[i] + " ");
		System.out.println(sorted[sorted.length-1]);
		
	}
	
	/**
	 * Method quicksot
	 * @param arr -> array yang ingin di sort
	 * @param low -> index terendah
	 * @param high -> index tertinggi
	 * @return array of integer
	 */
	public static int[] quicksort(int[] arr, int low, int high)
	{
		int i = low;
		int j = high;
		int pivot = arr[low+((high-low)/2)];
		
		if(arr.length > 1)
		{
			while(i <= j)
			{
				
				while(arr[i] < pivot)
				{
					i++;
				}
				
				while(arr[j] > pivot)
				{
					j--;
				}
				
				if(i <= j)
				{
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					
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
		return arr;
	}
}
