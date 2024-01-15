package utilidades;

import java.text.Normalizer;

public class Utilidades {

	public static int generaNumeroAleatorio(int max, int min) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
	
	public static String palabraSinTildeMinus(String palabra) {
		String palabraSinTilde = " ", palabraSinTiledeMinus = " ";
		palabraSinTilde = Normalizer.normalize(palabra, Normalizer.Form.NFD).replaceAll("[\u0300-\u0301]", "");
		palabraSinTiledeMinus = palabraSinTilde.toLowerCase();
		return palabraSinTiledeMinus;
	}
}
