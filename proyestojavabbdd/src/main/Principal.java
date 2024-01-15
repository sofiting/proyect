package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import gestion.DBManager;
import gestion.GestionFichero;
import gestion.GestionJugador;
import juego.Jugador;
import juego.JugadorHumano;
import juego.Partida;
import juego.Ranking;

/**
 * @author TINGMEI HUANG
 * @version 2.0 Este es la versión base de datos
 */

public class Principal {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		conectarbbdd();
		eligeOpcion();

	}

	/**
	 * Este es para conectar base de datos y el proyecto
	 */
	public static void conectarbbdd() {
		DBManager.loadDriver();
		DBManager.connect();
		if (DBManager.isConnected()) {
			System.out.println("ESTÁS CONECTADO");
		}
	}

	/**
	 * Menú principal para mostrar las funciones principal
	 */
	public static void menuPrincipal() {
		System.out.println("*********** BIENVENIDA AL ERES MÁS LISTO QUE UNA COMPUTADORA *********" + "\n");
		System.out.println("* 		  (SI ERES JUGADOR NUEVO ,  LEAS AYUDA ANTES DE TODO) 		 *" + "\n");
		System.out.println("*                 1. AYUDA 											 *" + "\n");
		System.out.println("*                 2. ENTRENAMIENTO									 *" + "\n");
		System.out.println("*                 3. JUGAR PARTIDA 									 *" + "\n");
		System.out.println("*                 4. RANKING 										 *" + "\n");
		System.out.println("*                 5. HISTÓRICO 										 *" + "\n");
		System.out.println("*                 6. JUGADORES 									 *" + "\n");
		System.out.println("*                 7. ESTADÍSTICA PREGUNTA					    	 *" + "\n");
		System.out.println("*                 8. SALIR DEL JUEGO 								 *" + "\n");
		System.out.println("**********************************************************************" + "\n");

	}

	/**
	 * Método que ejecuta la opción elegida: 1.mostrar ayuda ( un poco de
	 * introducción) 2.modo entrenamiento , se permite practicar solo , sin registro
	 * 3.jugar partida , jugar solo , o con otros , este apunta resultado en ranking
	 * y histórico 4.ranking : mostrar , buscar tu posición de ranking, ver los top
	 * 3 5.mostrar historico de todas las partias 6.manipulación de jugadores:
	 * listar,añadir,eliminar 7.salir del juego, termina TODO
	 */
	public static void eligeOpcion() {
		boolean opcionCorrecto = false;
		List<String> ayuda = new ArrayList<>();
		Ranking objRanking = new Ranking();

		while (!opcionCorrecto) {
			try {
				menuPrincipal();
				System.out.println("	" + "ELIGES UNA DE LAS OPCIONES(SOLO ACEPTA OPCIÓN NUMERICO) :" + "\n");
				int opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					try {
						ayuda = GestionFichero.leerFichero("src/gestion/ayudajuego");
						for (String s : ayuda) {
							System.out.println(s);
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("FICHERO NO SE ENCUENTRA, RUTA INCORRECTA");
						e.printStackTrace();
					}
					break;
				case 2:
					// modo entrenamiento
					Partida p2 = new Partida();
					Jugador j = new JugadorHumano("");
					p2.entrenamiento(j);

					break;
				case 3:
					// jugar partida
					Partida p1 = new Partida();
					p1.iniciarPartida();
					p1.jugarPartida();
					try {
						p1.finalizarPartida();
					} catch (IOException e) {
						System.out.println("FICHERO NO SE ENCUENTRA, RUTA INCORRECTA");
						e.printStackTrace();
					}

					break;
				case 4:
					objRanking.opcionRanking();
					break;
				case 5:
					System.out.println("********************** HISTORICO DE LAS PARTIDAS ************************");
					DBManager.printTablaHistorico();
					break;
				case 6:
					GestionJugador.opcionJugador();
					break;
				case 7:
					System.out.println("//////////////////////// CANTIDAD DE PREGUNTAS HAN SIDO ELEGIDOS ////////////////////////"+"\n");
					System.out.println("CANTIDAD DE PREGUNTA MATE: "+DBManager.countPregunta(1));
					System.out.println("CANTIDAD DE PREGUNTA LENGUA: "+DBManager.countPregunta(2));
					System.out.println("CANTIDAD DE PREGUNTA INGLÉS: "+DBManager.countPregunta(3));
					System.out.println();
					break;
				case 8:				
					opcionCorrecto = true;
					DBManager.close();
					System.out.println("GRACIAS POR JUGAR NUESTRO JUEGO, TE VEMOS PRONTO ヽ(ﾟ▽ﾟ)乂(ﾟ▽ﾟ)ﾉ");					
					break;
				default:
					System.out.println("x_x SOLO ACEPTA OPCIÓN : 1,2,3,4,5,6");
				}
			} catch (InputMismatchException in) {
				System.out.println("x_x SOLO ACEPTA VALOR INTRODUCIDO DE TIPO NÚMERO ENTERO");
			} catch (NullPointerException n) {
				System.out.println("x_x ERROR ,VALOR NULO!!.");
			} catch (Exception e) {
				System.out.println("x_x HA PRODUCIDO UN ERROR");
				e.printStackTrace();
			}
			sc.nextLine();
		}
		sc.close();
	}

}
