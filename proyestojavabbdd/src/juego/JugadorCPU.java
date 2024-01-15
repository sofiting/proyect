package juego;

import java.util.ArrayList;
import java.util.List;

import utilidad.Utilidad;

public class JugadorCPU extends Jugador {

	public JugadorCPU(String nombre) throws IllegalArgumentException {
		super(nombre);
	}

	/**
	 * para mostrar nombre y punto de jugador
	 */
	public String mostrarJugador() {
		return super.mostrarJugador();
	}

	/**
	 * responder la pregunta, si es correcto punto+1
	 * 
	 * @param pregunta aleatoria
	 */
	@Override
	public void responder(Pregunta pregunta) {

		if (pregunta instanceof PreguntaMate) {
			super.setPunto(super.getPunto() + 1);
			System.out.println("SOY EXPERTO EN MATE, ASI QUE LO SACO PERFECTO");
		} else if (pregunta instanceof PreguntaIngles) {
			List<String> opciones = new ArrayList<>();
			opciones.add("A");
			opciones.add("B");
			opciones.add("C");
			opciones.add("D");

			String opcionElegido = opciones.get(Utilidad.generaNumeroAleatorio(opciones.size() - 1, 0));
			System.out.println("OPCIÓN ELEGIDO: " + opcionElegido);
			if (pregunta.comprobarRespuesta(opcionElegido)) {
				super.setPunto(getPunto() + 1);
			} else {
				System.out.println("HA FALLADO LA RESPUESTA ES: " + pregunta.getRespuesta());
			}

		} else if (pregunta instanceof PreguntaLetra) {
			System.out.println("NO ENTIENDO LENGUAJE HUMANO");
		}

	}

}
