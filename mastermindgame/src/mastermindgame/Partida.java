package mastermindgame;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import utilidades.Utilidades;

public class Partida {

	public static Scanner sc = new Scanner(System.in);
	private Jugador jugador;
	private Tablero tablero;
	private List<ResultadoIntento> resultadoIntento;
	private int ronda;

	public Partida() {
		super();
		this.jugador = new Jugador();
		this.tablero = new Tablero();
		this.resultadoIntento = new ArrayList<>();
	}

	public void iniciar() {
		boolean correcto = false;

		System.out.println("--------------------------- EMPEZAMOS LA PARTIDA ---------------------------");

		while (!correcto) {
			try {
				System.out.println("INTRODUCE EL NÚMERO DE LA PARTIDA QUE QUIERE JUGAR:");
				int numPartida = sc.nextInt();
				if (numPartida >= 1) {
					this.setRonda(numPartida);
					correcto = true;
				}
			} catch (InputMismatchException in) {
				System.out.println("SOLO ACEPTA NÚMERO ENTERO");
			}
			sc.nextLine();
		}
	}

	public void jugar() {
		String respuesta = this.tablero.getColorOcultado();
		String adivina = "";
		String adivinaMinuSinTilde = "";
		int i = 0, cont = 0;
		int puntoTotal = 0;
		boolean correcto = false;

		while (i < this.getRonda()) {

			// HAY QUE COMENTAR!!!
			System.out.println("respuesta correcta " + respuesta);

			System.out.println("PARTIDA " + (i + 1) + ":");
			while (cont < 12 && !correcto) {
				mostrarIntentos();
				System.out.println("INTENTO " + (cont + 1) + ":");
				System.out.println(
						"ADIVINA 5 COLORES SEPARADOS CON ESPACIO Y SUS POSICIONES CORRECTAS (minus, sin tilde):");
				System.out.println("COLORES VÁLIDOS SON: roja, azul, verde, amarilla, rosa, blanca, negra, marron");
				adivina = sc.nextLine();
				adivinaMinuSinTilde = Utilidades.palabraSinTildeMinus(adivina);

				correcto = realizarIntento(respuesta, adivinaMinuSinTilde, correcto);
				
				if (!correcto) {
					cont++;
				}
				System.out.println();
			}

			if (!correcto) {
				System.out.println("LA RESPUESTA CORRECTA ES: " + respuesta);
			} else {
				puntoTotal += cuentaPunto(cont);
				this.jugador.setPunto(puntoTotal);
			}
			this.resultadoIntento.clear();
			i++;
			correcto = false;
			cont = 0;
		}
	}

	public boolean realizarIntento(String respuesta, String adivinaMinuSinTilde, boolean correcto) {
		int coloresCorrectos;
		int posicionesCorrectas;
		if (validarColor(adivinaMinuSinTilde)) {
			coloresCorrectos = colorCorrecto(respuesta, adivinaMinuSinTilde);
			posicionesCorrectas = posicionCorrecta(respuesta, adivinaMinuSinTilde);

			ResultadoIntento resultado = new ResultadoIntento(adivinaMinuSinTilde,
					contarEspigas(coloresCorrectos, posicionesCorrectas, 5));
			this.resultadoIntento.add(resultado);

			if (posicionesCorrectas == 5) {
				System.out.println("ENHORABUENA, RESPUESTA ACERTADA !!!");
				correcto = true;
			} else {
				mostrarPistas(coloresCorrectos, posicionesCorrectas);
			}
		}
		return correcto;
	}

	public void mostrarPistas(int coloresCorrectos, int posicionesCorrectas) {
		System.out.println("PISTAS: ");
		System.out.println("Colores correctos: " + coloresCorrectos);
		System.out.println("Posiciones correctas: " + posicionesCorrectas);
		System.out.println(contarEspigas(coloresCorrectos, posicionesCorrectas, 5));
	}

	public void terminar() {
		System.out.println("LA PUNTUACIÓN QUE HAS CONSEGUIDO ES: " + this.jugador.getPunto());
	}

	public boolean validarColor(String resColor) {
		List<String> coloresValidos = this.tablero.getColor();
		String[] res = resColor.split(" ");

		if (res.length > 5) {
			System.out.println("SOLO INTRODUCE 5 COLORES.");
			return false;
		}

		for (String elemento : res) {
			if (!coloresValidos.contains(elemento)) {
				System.out.println("EL COLOR " + elemento
						+ " NO EXISTE , SOLO ACEPTAMOS: roja, azul, verde, amarilla, rosa, blanca, negra, marron");
				return false;
			}
		}

		return true;
	}

	public int colorCorrecto(String respuesta, String resUsuario) {
		String[] respPalabras = respuesta.split("\\s");
		String[] resUPalabras = resUsuario.split("\\s");

		int coloresCorrectos = 0;

		for (String usuario : resUPalabras) {
			boolean encontrado = false;
			for (String res : respPalabras) {
				if (res.equals(usuario)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				coloresCorrectos++;
			}
		}

		return coloresCorrectos;
	}

	public int posicionCorrecta(String respuesta, String resUsuario) {
		String[] respPalabras = respuesta.split("\\s");
		String[] resUPalabras = resUsuario.split("\\s");

		int posicionesCorrectas = 0;

		for (int i = 0; i < respPalabras.length; i++) {
			if (i < resUPalabras.length && respPalabras[i].equals(resUPalabras[i])) {
				posicionesCorrectas++;
			}
		}

		return posicionesCorrectas;
	}

	public String contarEspigas(int coloresCorrectos, int posicionesCorrectas, int respuesta) {
		StringBuilder resultado = new StringBuilder();

		for (int i = 0; i < posicionesCorrectas; i++) {
			resultado.append("N");
			coloresCorrectos--;
		}

		for (int i = 0; i < coloresCorrectos; i++) {
			resultado.append("B");
		}

		if (coloresCorrectos == 0 && posicionesCorrectas == 0) {
			System.out.println("NINGUNO ES CORRECTO NI COLOR NI POSICIÓN");
		}

		return resultado.toString();
	}

	public void mostrarIntentos() {
		if (!this.resultadoIntento.isEmpty()) {
			for (ResultadoIntento r : this.resultadoIntento) {
				r.mostrarResul();
			}
		}
	}

	public int cuentaPunto(int cont) {

		return (12 - cont);
	}

	public static Scanner getSc() {
		return sc;
	}

	public static void setSc(Scanner sc) {
		Partida.sc = sc;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public List<ResultadoIntento> getResultadoIntento() {
		return resultadoIntento;
	}

	public void setResultadoIntento(List<ResultadoIntento> resultadoIntento) {
		this.resultadoIntento = resultadoIntento;
	}

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

}
