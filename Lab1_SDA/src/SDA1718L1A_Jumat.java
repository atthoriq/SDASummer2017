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
		
		/*
		 * key dimodulo 26 karena banyak alfabet sebanyak 26.
		 * base merupakan integer dari character patokan, pada kasus ini char A, untuk rumus mencari character dekripsi
		 */
		key = key%26;
		int base=0;
		
		/*
		 * iterasi seluruh array character dari string yang diinput.
		 * Lalu dekripsi dengan shifting setiap character huruf sebanyak key yang diinput.
		 * Lalu mengembalikan character yang dishifting kedalam array yang sama.
		 */
		for(int i=0; i<chArr.length; i++) {
			if(Character.isUpperCase(chArr[i])) base = (int) 'A';
			if(Character.isLowerCase(chArr[i])) base = (int) 'a';
			
			if(Character.isLetter(chArr[i]))
				chArr[i] = (char)(((int)chArr[i]-base - (key-26))%26 + base);
		}
		
		// Lengkapi method diatas kalimat ini
		
		String res = new String(chArr);
		return res;
		
	}
	
}