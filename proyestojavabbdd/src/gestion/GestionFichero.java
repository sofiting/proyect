package gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import juego.PreguntaIngles;

public class GestionFichero {

	/**
	 * Este para leer fichero ayuda , diccionario
	 * 
	 * @param ruta
	 * @return List<String> lo que ha leído del fichero
	 * @throws FileNotFoundException si no se encuentra fichero
	 */
	public static List<String> leerFichero(String ruta) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(ruta));
		List<String> lista = new ArrayList<String>();

		while (sc.hasNext()) {
			lista.add(sc.nextLine());
		}
		sc.close();
		return lista;
	}

	/**
	 * Este para leer fichero inglés
	 * 
	 * @return List<PreguntaIngles> todas las preguntas ingles
	 * @throws FileNotFoundException si no se encuentra fichero
	 */
	public static List<PreguntaIngles> leerPreguntaIngles() throws FileNotFoundException {
		List<PreguntaIngles> lista = new ArrayList<PreguntaIngles>();
		Scanner sc = new Scanner(new File("src/gestion/ingles"));

		// leer la primer linea es enunciado, segundo es la respuesta correcta, otros
		// tres lineas son opciones, hago una lista de opciones y añadir respuesta
		// correcta dentro y desordenarlo y con estos constituye una pregunta ingles
		while (sc.hasNext()) {
			String enunciado = " ", respuesta = " ";
			List<String> opciones = new ArrayList<String>();
			enunciado = sc.nextLine();
			respuesta = sc.nextLine();
			for (int i = 0; i < 3; i++) {
				opciones.add(sc.nextLine());
			}
			opciones.add(respuesta);
			Collections.shuffle(opciones);
			lista.add(new PreguntaIngles(enunciado, respuesta, opciones));
		}
		sc.close();
		return lista;
	}

}