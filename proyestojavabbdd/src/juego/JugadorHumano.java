package juego;

import java.util.Scanner;

public class JugadorHumano extends Jugador {

	public JugadorHumano(String nombre) {
		super(nombre);

	}

	public JugadorHumano(String nombre, int punto) {
		super(nombre, punto);
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
		Scanner sc = new Scanner(System.in);
		try {
			String res = sc.nextLine();
			if (pregunta.comprobarRespuesta(res)) {
				super.setPunto(getPunto() + 1);
				System.out.println("Muy bien , respuesta correcta");
			} else {
				System.out.println("Has fallado, la respuesta correcto es: " + pregunta.getRespuesta());
			}
		} catch (NumberFormatException n) {
			System.out.println(
					"solo introduce el número para responder ," + "\n" + " lamentamente está incorrecta la respuesta");
		}
	}

}
