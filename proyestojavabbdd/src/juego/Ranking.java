package juego;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import gestion.DBManager;
import utilidad.Utilidad;

public class Ranking {
	private static Scanner sc = new Scanner(System.in);
	private List<Jugador> jugadorHumano;

	public Ranking(List<Jugador> jugadorHumano) {
		this.jugadorHumano = jugadorHumano;
	}

	public Ranking() {
	}

	/**
	 * menú de ranking
	 */
	public void menuRanking() {
		System.out.println("********************************************");
		System.out.println("*                 MENÚ RANKING             *");
		System.out.println("*           1. MOSTRAR RANKING             *");
		System.out.println("*           2. BUSCAR TU NÚMERO RANKING    *");
		System.out.println("*           3. VER LOS TOPS 3 DE RANKING   *");
		System.out.println("*           4. VOLVER AL MENÚ PRINCIPAL    *");
		System.out.println("********************************************");
	}

	/**
	 * las funcionalidades principales de menú ranking:
	 */
	public void opcionRanking() {
		String res = " ", name = " ";
		boolean acepta = false;

		boolean opcion = false;
		while (!opcion) {
			menuRanking();
			System.out.println("   	ELIGES UNA OPCIÓN, INTRODUCES(número): ");
			res = sc.next();
			switch (res) {
			case "1":
				DBManager.printTablaRanking();
				break;
			case "2":
				while (!acepta) {
					System.out.println("INTRODUCE TU NOMBRE: (sin espacio, minuscula) ");
					name = sc.next();
					if (!Utilidad.comprobarCaracteres(name)) {
						System.out.println("FORMATO DE NOMBRE INTRODUCIDO NO ACEPTA EN EL SISTEMA");
					} else {
						DBManager.buscarRanking(name);
						acepta = true;
					}
					sc.nextLine();
				}
				acepta = false;
				break;
			case "3":
				DBManager.verTopX();
				break;
			case "4":
				opcion = true;
				break;
			default:
				System.out.println("HAS ELEGIDO UNA OPCIÓN QUE NO ESTÁ VÁLIDO");
			}

		}
		sc.nextLine();
	}

	/**
	 * Este es para actializar tabla ranking después de cada partida
	 * 
	 * @throws IOException
	 */
	public void actualizarRanking() throws IOException {

		// si la tabla esta vacio , inserta y ya esta , sino si esta en
		// la tabla update punto sino insert
		if (!DBManager.tablaVacio("ranking")) {
			for (Jugador j : jugadorHumano) {
				if (DBManager.existsRankingPersona(j.getNombre())) {
					DBManager.updateRanking(j.getNombre(), j.getPunto());
				} else {
					DBManager.insertJugador(j.getNombre(), j.getPunto());
				}
			}
		} else {
			for (Jugador j : jugadorHumano) {
				DBManager.insertJugador(j.getNombre(), j.getPunto());
			}
		}
	}

	/**
	 * @return the sc
	 */
	public static Scanner getSc() {
		return sc;
	}

	/**
	 * @param sc the sc to set
	 */
	public static void setSc(Scanner sc) {
		Ranking.sc = sc;
	}

	/**
	 * @return the jugadorHumano
	 */
	public List<Jugador> getJugadorHumano() {
		return jugadorHumano;
	}

	/**
	 * @param jugadorHumano the jugadorHumano to set
	 */
	public void setJugadorHumano(List<Jugador> jugadorHumano) {
		this.jugadorHumano = jugadorHumano;
	}

}
