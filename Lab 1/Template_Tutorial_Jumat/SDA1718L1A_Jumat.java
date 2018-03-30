import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
// Masukkan program yang Anda ingin import dibawah kalimat ini

// Masukkan program yang Anda ingin import diatas kalimat ini

/**
	*
	* Template kode yang Anda bisa pakai untuk menyelesaikan soal Tutorial 1A
	* Penggunaan template bersifat opsional namun mahasiswa sangat disarankan untuk menggunakan template ini
	* Jangan mengubah nama kelas dan menghapus kode import diatas. Pengubahan dapat menyebabkan program mengalami error.
	* @author Jahns Christian Albert
	*
*/
public class SDA1718L1A_Jumat {
	
	/**
		*
		* Method main untuk mengambil input dan menampilkan output
		* Jangan mengubah method ini. Pengubahan method dapat menyebabkan program mengalami error
		*
	*/
	public static void main(String[] args) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int key = Integer.parseInt(in.readLine());
		String str = in.readLine();
		
		while(str != null){
			
			System.out.println(decrypt(str, key));
			str = in.readLine();
			
		}
		
	}
	
	/**
		*
		* Method untuk mendekripsi string
		* To do : Lengkapi method ini
		* @param sebuah string yang ingin didekripsi
		* @param sebuah bilangan bulat positif sebagai key
		* @return sebuah string yang sudah didekripsi
		*
	*/
	public static String decrypt(String str, int key){
		
		char[] chArr = str.toCharArray();
		
		// Lengkapi method dibawah kalimat ini
		
		// Lengkapi method diatas kalimat ini
		
		String res = new String(chArr);
		return res;
		
	}
	
}