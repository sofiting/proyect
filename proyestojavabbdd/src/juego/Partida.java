package juego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import gestion.DBManager;
import gestion.GestionJugador;
import main.Principal;
import utilidad.Utilidad;

public class Partida {

	private static List<Jugador> jugadores;
	private List<Pregunta> preguntas;
	private int ronda;
	private Scanner teclado;
	private final int max_jugador = 4;

	/**
	 * controla numero de jugador no puede ser mayor que 4
	 * 
	 * @param jugadores
	 * @param preguntas
	 * @param ronda
	 * @throws IllegalArgumentException cuando numero de jugador es mayor que 4
	 */
	public Partida(List<Jugador> jugadores, List<Pregunta> preguntas, int ronda) {
		super();
		if (jugadores.size() <= max_jugador) {
			this.jugadores = jugadores;
			this.preguntas = preguntas;
			this.ronda = ronda;
			teclado = new Scanner(System.in);
		} else {
			throw new IllegalArgumentException("Máximo 4 jugadores en una partida");
		}
	}

	public Partida() {
		teclado = new Scanner(System.in);
		jugadores = new ArrayList<>();
		preguntas = new ArrayList<>();
	}

	/**
	 * inicio de la partida , prepara numero de jugador que juega esa partida ,
	 * ronda que quiere, y preparar las preguntas
	 */
	public void iniciarPartida() {

		try {
			// eleccion de num jugador,cpu
			pedirInformacion();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FICHERO jugadoresregistrados no se encuentrado , no se puede dar alta");
		}
		// eleccion de num ronda
		this.ronda = pedirRonda();

		// generar preguntas y add en list
		this.preguntas.add(new PreguntaMate());
		this.preguntas.add(new PreguntaLetra());
		this.preguntas.add(new PreguntaIngles());

		// añadir tres tipos de preguntas a la lista de pregunta , numaleatorio
		// list: mate, letra,ingles
	}

	/**
	 * empieza a jugador la partida, mostrar pregunta y jugador responde
	 */
	public void jugarPartida() {
		int cont = 0;
		String s = "";
		// orden aleatorio de jugador para responder
		Collections.shuffle(jugadores);
		System.out.println("========================= EMPIEZA PARTIDA ===============================");

		// bucle de ronda
		while (cont < this.ronda) {
			// bucle de jugadores
			for (Jugador j : jugadores) {

				int indexPregunta = Utilidad.generaNumeroAleatorio(this.preguntas.size() - 1, 0);
				Pregunta preguntaAleatorio = this.preguntas.get(indexPregunta);

				if (preguntaAleatorio instanceof PreguntaMate) {
					DBManager.insertaPregunta(1);
				} else if (preguntaAleatorio instanceof PreguntaLetra) {
					DBManager.insertaPregunta(2);
				} else if (preguntaAleatorio instanceof PreguntaIngles) {
					DBManager.insertaPregunta(3);
				}

				System.out.println("///////////////////////////////////////////////////////////////");
				System.out.println("");
				System.out.println("TURNO DE " + j.getNombre() + ": ");
				System.out.println("");
				preguntaAleatorio.mostrarPregunta();
				System.out.println("");
				j.responder(preguntaAleatorio);
				System.out.println("");
			}
			System.out.println("********************************************************************");

			// mostrar resultado de cada ronda
			if (cont == this.ronda - 1) {
				System.out.println("--------------- RESULTADO FINAL DE LA PARTIDA  : ------------------");
			} else {
				System.out.println("---------- RESULTADO DEL RONDA " + (cont + 1) + " :----------------");
			}

			for (Jugador j1 : this.jugadores) {
				s += j1.getNombre() + " " + j1.getPunto() + " ";
				System.out.println(j1.mostrarJugador());
			}
			cont++;
		}

	}

	/**
	 * cuando uno quiere estrenar , sin darse alta también puede estrenar
	 * 
	 * @param j
	 */
	public void entrenamiento(Jugador j) {
		int cont = 0;
		// añadir jugador
		this.jugadores.add(j);
		// pedir ronda
		this.ronda = pedirRonda();
		// añadir preguntas
		this.preguntas.add(new PreguntaMate());
		this.preguntas.add(new PreguntaLetra());
		this.preguntas.add(new PreguntaIngles());
		System.out.println("================== EMPIEZA MODO ENTRENAMIENTO =========================");

		while (cont < this.ronda) {
			int indexPregunta = Utilidad.generaNumeroAleatorio(this.preguntas.size() - 1, 0);
			Pregunta preguntaAleatorio = this.preguntas.get(indexPregunta);

			if (preguntaAleatorio instanceof PreguntaMate) {
				DBManager.insertaPregunta(1);
			} else if (preguntaAleatorio instanceof PreguntaLetra) {
				DBManager.insertaPregunta(2);
			} else if (preguntaAleatorio instanceof PreguntaIngles) {
				DBManager.insertaPregunta(3);
			}
			
			System.out.println("///////////////////////////////////////////////////////////////");
			System.out.println("");
			preguntaAleatorio.mostrarPregunta();
			System.out.println("");
			j.responder(preguntaAleatorio);
			System.out.println("");
			cont++;
		}

		System.out.println("================== FIN MODO ENTRENAMIENTO =========================");

		if (j.getPunto() == 0) {
			System.out.println("TIENES QUE PRÁCTICAR MÁS , MUCHO ÁNIMO !!! PORCENTAJE DE ACIERTOS: 0%");
		} else if (j.getPunto() == this.ronda) {
			System.out.println("BRAVO!! HAS ACERTADO TODAS LAS PREGUNTAS!! PORCENTAJE DE ACIERTOS: 100%");
		} else {
			System.out.println("TIENES " + j.getPunto() + " ACERTADO , DE " + this.ronda
					+ " PREGUNTAS , PORCENTAJE DE ACIERTOS: " + (((double) j.getPunto() / this.ronda) * 100) + "%");
		}
		System.out.println();
	}

	/**
	 * cuando termina partida , almacenar el resultado al fichero historico y
	 * actualizar ranking, y mostrar quien es ganador o empate
	 * 
	 * @throws IOException
	 */
	public void finalizarPartida() throws IOException {
		List<Jugador> humano = new ArrayList<>();
		String s = "";

		System.out.println("========================= PARTIDA TERMINADA ===============================");
		System.out.println("");

		// almacenar historico de la partida
		int id = DBManager.selectMaxPartida();
		for (Jugador j : this.jugadores) {
			DBManager.insertarHistorico((id + 1), j.getNombre(), j.getPunto());
		}

		// ordenar los puntos de jugadores en esta partida
		Collections.sort(this.jugadores);

		int maxPunto = this.jugadores.get(0).getPunto();
		int cont = 0;

		// recorre los puntos de cada uno para si hay alguien tiene mismo punto
		for (Jugador j : this.jugadores) {
			if (j.getPunto() == maxPunto) {
				cont++;
			}
		}

		System.out.println("");
		System.out.println("///////////////////////  GANADOR DE ESTA PARTIDA ////////////////////////////");
		System.out.println("");

		// caso empate o caso de un solo ganador
		if (cont > 1) {

			System.out.println("EMPATE: Los siguientes jugadores empatan en el número 1 de esta partida:");
			for (Jugador j : this.jugadores) {
				if (j.getPunto() == maxPunto) {
					System.out.println(j.getNombre());
				}
			}

		} else if (cont == 1) {

			System.out.println(this.jugadores.get(0).getNombre() + " ES GANADOR DE ESTA PARTIDA");

		}

		System.out.println("");

		// sacar los jugadores humano de esta partida para ranking de luego
		for (Jugador j : jugadores) {
			if (j instanceof JugadorHumano) {
				humano.add((JugadorHumano) j);
			}
		}

		// actualizarRanking
		Ranking ranking = new Ranking(humano);
		ranking.actualizarRanking();

		System.out.println();
	}

	/**
	 * pedir número de jugador para dicha partida
	 * 
	 * @throws IOException
	 */
	public void pedirInformacion() throws IOException {
		int res1 = 0, res2 = 0;
		boolean maximo = false;

		while (!maximo) {
			try {
				System.out.println("¿ CON CUÁNTAS CPU QUIERES JUGAR EN ESTA PARTIDA ?");
				res1 = this.teclado.nextInt();
				if (res1 < max_jugador) {
					System.out.println("¿ CON CUÁNTAS JUGADOR HUMANO QUIERES JUGAR EN ESTA PARTIDA ? ");
					res2 = this.teclado.nextInt();
				}
				// si suma de cpu y humano es menor o igual al max_jugaodr 4
				if (res1 + res2 > 0 && res1 + res2 <= max_jugador) {
					maximo = true;
					// aqui hay que llamar crear jugador y cpu si respuesta no es 0
					if (res1 != 0) {
						GestionJugador.crearCPU(res1, this.jugadores);
					}
					if (res2 != 0) {
						GestionJugador.crearHumano(res2, this.jugadores);
					}
					// si suma de cpu y humano es mayor que max_jugaodr 4
				} else if (res1 + res2 > max_jugador) {
					System.out.println("ERROR, MÁXIMO 4 JUGADORES PARA UNA PARTIDA");
					res1 = 0;
					res2 = 0;
					// si suma de cpu y humano es 0
				} else if (res1 + res2 == 0) {
					System.out.println("ERROR , MÍNIMO 1 JUGADOR PARA UNA PARTIDA");
				}
			} catch (InputMismatchException in) {
				System.out.println("VALOR ENTRADA SOLO ACEPTA NÚMERO ENTERO");
			}
			this.teclado.nextLine();
		}
	}

	/**
	 * pedir ronda para dicha partida
	 * 
	 * @return int numero de ronda para dicha partida
	 */
	public int pedirRonda() {
		boolean opcion = false;
		while (!opcion) {
			System.out.println("////////////////////////////////////////////////////////////////////");
			System.out.println("ELIGES LA RONDA: " + "\n" + "1.Partida rápida" + "\n" + "2.Partida corta" + "\n"
					+ "3.Partida normal" + "\n" + "4.Partida larga");
			try {
				int elegirRonda = this.teclado.nextInt();
				if (elegirRonda == 1) {
					opcion = true;
					return 3;
				} else if (elegirRonda == 2) {
					opcion = true;
					return 5;
				} else if (elegirRonda == 3) {
					opcion = true;
					return 10;
				} else if (elegirRonda == 4) {
					opcion = true;
					return 20;
				} else {
					System.out.println("OPCIÓN NO VÁLIDA");
				}
			} catch (InputMismatchException in) {
				System.out.println("TIOPS DE DATOS INTRODUCIDOS NO ES CORRECTO, DEBE SER NÚMERO ENTERO");
				in.printStackTrace();
			}
			this.teclado.nextLine();
		}
		return 0;
	}

	/**
	 * @return the jugadores
	 */
	public static List<Jugador> getJugadores() {
		return jugadores;
	}

	/**
	 * @param jugadores the jugadores to set
	 */
	public static void setJugadores(List<Jugador> jugadores) {
		Partida.jugadores = jugadores;
	}

	/**
	 * @return the preguntas
	 */
	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	/**
	 * @param preguntas the preguntas to set
	 */
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	/**
	 * @return the ronda
	 */
	public int getRonda() {
		return ronda;
	}

	/**
	 * @param ronda the ronda to set
	 */
	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

	/**
	 * @return the teclado
	 */
	public Scanner getTeclado() {
		return teclado;
	}

	/**
	 * @param teclado the teclado to set
	 */
	public void setTeclado(Scanner teclado) {
		this.teclado = teclado;
	}

	/**
	 * @return the max_jugador
	 */
	public int getMax_jugador() {
		return max_jugador;
	}

}
