import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;
// Masukkan program yang Anda ingin import dibawah kalimat ini

// Masukkan program yang Anda ingin import diatas kalimat ini

/**
	*
	* Template kode yang Anda bisa pakai untuk menyelesaikan soal Tutorial 1B
	* Penggunaan template bersifat opsional namun mahasiswa sangat disarankan untuk menggunakan template ini
	* Jangan mengubah nama kelas dan menghapus kode import diatas. Pengubahan dapat menyebabkan program mengalami error.
	* @author Jahns Christian Albert
	*
*/
public class SDA1718L1B_Jumat {
	
	/**
		*
		* Method main untuk mengambil input dan menampilkan output
		* Jangan mengubah method ini. Pengubahan method dapat menyebabkan program mengalami error
		*
	*/
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		
		st = new StringTokenizer(in.readLine());
		
		for(int i = 0; i < n; i++){
			
			arr[i] = Long.parseLong(st.nextToken());
			
		}
		
		System.out.println(solution(arr,k));
		
	}
	
	/**
		*
		* Method untuk menyelesaikan soal 1B
		* To do : Lengkapi method ini
		* @param array dari bilangan bulat (long) yang ingin dicari jumlah pasangan yang "berpasangan"
		* @param sebuah bilangan bulat positif sebagai key
		* @return jumlah pasangan yang berpasangan (long)
		*
	*/
	public static long solution(long[] arr, long k){
		
		/*
		 * arrayModulo adalah array yang memiliki panjang k dan indexnya sebagai hasil setiap
		 * angka dalam arr dimodulokan dengan k.
		 * Index merupakan hasil modulo tersebut.
		 * 
		 */
		long pairCard = 0;
		long[] arrayModulo = new long[(int)k];
		
		for(int i=0; i<arr.length; i++) {
			int index = (int) (arr[i]%k);
			pairCard += arrayModulo[(int) ((k-index)%k)];
			arrayModulo[index]++;	
		}

		return pairCard;
	}
	
}