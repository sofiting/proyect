package juego;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import gestion.GestionFichero;
import utilidad.Utilidad;

public class PreguntaIngles extends Pregunta {

	private List<String> opciones;

	public PreguntaIngles(String enunciado, String respuesta, List<String> opciones) {
		super(enunciado, respuesta);
		this.opciones = opciones;
	}

	public PreguntaIngles() {
	}

	/**
	 * Este método genera pregunta inglés
	 * 
	 * @return String enunciado de la pregunta inglés
	 */
	@Override
	public String generarPregunta() {
		List<PreguntaIngles> preguntas = new ArrayList<PreguntaIngles>();

		PreguntaIngles pregunta = null;

		try {
			preguntas = GestionFichero.leerPreguntaIngles();
			// System.out.println(preguntas.toString());
			// aquí para sacar una pregunta aleatorio
			int n = Utilidad.generaNumeroAleatorio(preguntas.size() - 1, 0);
			pregunta = preguntas.get(n);

			// set enunciado y opciones dentro del objeto preguntaIgles
			// ya que estaba vacío
			super.setEnunciado(pregunta.getEnunciado());
			this.setOpciones(pregunta.getOpciones());

			// sacar la respuesta correcta de la pregunta lanzada,
			// y comprobar qué opción es la correcta
			String respuestaCorrecta = pregunta.getRespuesta();
			if (respuestaCorrecta.equals(pregunta.getOpciones().get(0))) {
				super.setRespuesta("A");
			} else if (respuestaCorrecta.equals(pregunta.getOpciones().get(1))) {
				super.setRespuesta("B");
			} else if (respuestaCorrecta.equals(pregunta.getOpciones().get(2))) {
				super.setRespuesta("C");
			} else if (respuestaCorrecta.equals(pregunta.getOpciones().get(3))) {
				super.setRespuesta("D");
			}
			// para hacer prueba se puede descomentar siguiente linea , para sacar resultado
			// dela pregunta
			//System.out.println("RESPUESTA ES " + pregunta.getRespuesta());
		} catch (FileNotFoundException e) {
			System.out.println("FICHERO INGLÉS NO SE ENCUENTRA");
			e.printStackTrace();
		}
		return super.getEnunciado();
	}

	/**
	 * Este es para mostrar pregunta inglés
	 */
	public void mostrarPregunta() {
		System.out.println(generarPregunta());
		System.out.println("");
		System.out.println("ELIGES (SOLAMENTE) UNA DE LAS SIGUIENTES OPCIONES: ");
		System.out.println("");
		for (int i = 0; i < opciones.size(); i++) {
			System.out.println((char) ('A' + i) + ")  " + opciones.get(i));
		}
	}

	/**
	 * Este es para comprobar la respuesta del jugador , da igual mayuscula o
	 * minuscula
	 * 
	 * @return true: si lo que introduce jugador es lo mismo que la respuesta
	 *         correcta; en caso contrario return false
	 */
	public boolean comprobarRespuesta(String res) {
		String resJugador = res.toUpperCase();
		if (super.getRespuesta().equalsIgnoreCase(resJugador))
			return true;
		return false;
	}

	/**
	 * @return the opciones
	 */
	public List<String> getOpciones() {
		return opciones;
	}

	/**
	 * @param opciones the opciones to set
	 */
	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}

}
