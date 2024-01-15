package juego;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import main.Principal;
import utilidad.Utilidad;

public class PreguntaMate extends Pregunta {

	public PreguntaMate() {

	}

	/**
	 * Este es para generar pregunta de mate
	 * 
	 * @return String expresión matematica
	 */
	@Override
	public String generarPregunta() {

		List<Integer> numero = new ArrayList<Integer>();
		List<String> operador = new ArrayList<String>();
		String expresion = " ";

//genera 4-8 digitos aleatorios
		int cantidadNumero = Utilidad.generaNumeroAleatorio(8, 4);

//genera valor aleatorio de 2 a 12
		for (int i = 0; i < cantidadNumero; i++) {
			numero.add(Utilidad.generaNumeroAleatorio(12, 2));
			// System.out.print(numero.get(i) + " ");
		}

//genera operadores aleatorio
		for (int i = 0; i < cantidadNumero - 1; i++) {
			switch (Utilidad.generaNumeroAleatorio(3, 1)) {
			case 1:
				operador.add("+");
				break;
			case 2:
				operador.add("-");
				break;
			case 3:
				operador.add("*");
				break;
			}
		}

//concadena numero y operadoresS
		for (int i = 0; i < cantidadNumero; i++) {
			expresion += numero.get(i);
			if (i < operador.size()) {
				expresion += operador.get(i);
			}
		}

		return expresion;
	}

	/**
	 * Este es para mostrar pregunta matematica
	 */
	@Override
	public void mostrarPregunta() {
		super.setEnunciado(generarPregunta());
		System.out.println("Calcula el resultado de la expresión: " + "\n" + super.getEnunciado());
		// para hacer prueba se puede descomentar siguiente linea , para sacar resultado
		// dela pregunta
		//System.out.println("respuesta: " + generaRespuesta());
	}

	/**
	 * Este es para obtener la respuesta
	 * 
	 * @return int resultado de la expresion generada en el metodo generarPregunta()
	 */
	public int generaRespuesta() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		try {
			Object result = engine.eval(super.getEnunciado());

			if (result instanceof Integer) {
				Integer res = (Integer) result;
				super.setRespuesta(result.toString());
				return res;
			}
		} catch (ScriptException e) {
			System.err.println("Error evaluando la expresión: " + e.getMessage());
		}
		return 0;
	}

	/**
	 * Este es para comprobar la respuesta del jugador , convertir la respuesta a
	 * entero
	 * 
	 * @return true: si lo que introduce jugador es lo mismo que la respuesta
	 *         correcta; en caso contrario return false
	 */
	@Override
	public boolean comprobarRespuesta(String resultado) {
		int resJugador = Integer.parseInt(resultado);
		if (generaRespuesta() == resJugador)
			return true;
		return false;
	}

}
