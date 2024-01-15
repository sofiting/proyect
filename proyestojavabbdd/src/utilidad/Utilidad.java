package utilidad;

import java.text.Normalizer;

public class Utilidad {

	/**
	 * Este es para generar un número aleatorio
	 * 
	 * @param max
	 * @param min
	 * @return un número aleatorio que está dentro del rango entre max y min
	 */
	public static int generaNumeroAleatorio(int max, int min) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	/**
	 * Este es para quitar tilde de una palabra y la convierte en minúscula
	 * 
	 * @param palabra
	 * @return una palabra sin tilde y minúscula
	 */
	public static String palabraSinTildeMinus(String palabra) {
		String palabraSinTilde = " ", palabraSinTiledeMinus = " ";
		palabraSinTilde = Normalizer.normalize(palabra, Normalizer.Form.NFD).replaceAll("[\u0300-\u0301]", "");
		palabraSinTiledeMinus = palabraSinTilde.toLowerCase();
		return palabraSinTiledeMinus;
	}

	/**
	 * Este es para comprobar si cadena introducido está entre a-z ó A-Z y que la
	 * longitud es menor o igual al 20
	 * 
	 * @param cadena
	 * @return true: cuando la cadena contienen las letras que están dentro del
	 *         rango de [a-zA-Z] , en caso contrario return false
	 */
	public static boolean comprobarCaracteres(String cadena) {
		return cadena.matches("[a-zA-Z]+") && cadena.length() <= 20;
	}

}
