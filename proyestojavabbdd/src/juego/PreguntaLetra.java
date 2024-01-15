package juego;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import gestion.GestionFichero;
import main.Principal;
import utilidad.Utilidad;

public class PreguntaLetra extends Pregunta {

	public PreguntaLetra() {
	}

	/**
	 * Este método es para generar una pregunta letra aleatoriamente
	 * 
	 * @return String
	 */
	@Override
	public String generarPregunta() {
		String palabra = " ", palabraOcultada = " ", palabraSinTildeMinus = " ";
		List<String> lista = new ArrayList<String>();

		try {

			lista = GestionFichero.leerFichero("src/gestion/diccionario");

			// si tocara palabra de 3 letras , se genera otra palabra
			while (palabra.length() < 3) {
				palabra = lista.get(Utilidad.generaNumeroAleatorio(lista.size() - 1, 0));
			}
			// para hacer prueba se puede descomentar siguiente linea , para sacar resultado
			// dela pregunta
			//System.out.println(palabra);

			// convertir la palabra a minúscula sin tilde
			palabraSinTildeMinus = Utilidad.palabraSinTildeMinus(palabra);
			// set esta palabra como respuesta antes de ocultar alguna letra
			super.setRespuesta(palabraSinTildeMinus);
			// dividir la palabra a char array
			char ch[] = palabraSinTildeMinus.toCharArray();

			// número de letras a ocultar (redondeo hacia abajo)
			int letraOculta = palabraSinTildeMinus.length() / 3;
			List<Integer> numOculto = new ArrayList<Integer>();

			// Generar índices de las letras a ocultar
			while (numOculto.size() < letraOculta) {
				int indice = Utilidad.generaNumeroAleatorio(ch.length - 1, 0);
				if (!numOculto.contains(indice)) {
					numOculto.add(indice);
					ch[indice] = '*';
				}
			}
			// convertir char array con letras ocultada a String
			palabraOcultada = new String(ch);

		} catch (FileNotFoundException e) {
			System.out.println("EL FICHERO DIRECCIONARIO NO SE ENCUENTRA");
			e.printStackTrace();
		}
		return palabraOcultada;
	}

	/**
	 * Este es para mostrar pregunta letra
	 */
	@Override
	public void mostrarPregunta() {
		System.out.println("Adivinar la palabra (introduce la palabra entera minúscula sin tilde para responder) : ");
		String generarPregunta = generarPregunta();
		System.out.println(generarPregunta);
		super.setEnunciado(generarPregunta);
	}

	/**
	 * Este es para comprobar la respuesta del jugador , da igual mayuscula o
	 * minuscula
	 * 
	 * @return true: si lo que introduce jugador es lo mismo que la respuesta
	 *         correcta; en caso contrario return false
	 */
	@Override
	public boolean comprobarRespuesta(String res) {
		String resJugador = Utilidad.palabraSinTildeMinus(res);
		if (super.getRespuesta().equalsIgnoreCase(resJugador)) {
			return true;
		}
		return false;
	}
}
