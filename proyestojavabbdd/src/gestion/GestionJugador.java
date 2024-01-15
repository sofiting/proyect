package gestion;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import juego.Jugador;
import juego.JugadorCPU;
import juego.JugadorHumano;
import utilidad.Utilidad;

public class GestionJugador {

	private static Scanner teclado = new Scanner(System.in);;

	/**
	 * Este es para comprobar si el proceso de añadir el jugador está todo correcto
	 * 
	 * @param nombre
	 * @return true proceso de añadir exitoso , el contrario return false;
	 */
	public static boolean anadirJugador(String nombre) {
		String nombreSinTildeMinus = "";

		// solo acepta el nombre introducido de a-zA-Z los demás no coge el sistema
		if (Utilidad.comprobarCaracteres(nombre)) {
			// comprobar si existe la persona en la tabla ranking
			if (!DBManager.existsRankingPersona(nombre)) {
				// convertir el nombre a minúscula sin tilde
				nombreSinTildeMinus = Utilidad.palabraSinTildeMinus(nombre);

				// insertar jugador a la tabla
				if (DBManager.insertJugador(nombreSinTildeMinus, 0)) {
					System.out.println("HAS REGISTRADO CON EL NOMBRE: " + nombreSinTildeMinus);
					return true;
				}
			} else {
				System.out.println("JUGADOR " + nombre + " YA EXISTE");
				return false;
			}

		} else {
			System.out.println("NOMBRE INTRODUCIDO EN FORMATO INCORRECTO");
		}
		return false;
	}

	/**
	 * para eliminar jugador del sistema y su posicion ranking si hay
	 * 
	 * @param nombre
	 * @return true proceso de eliminar exitoso , el contrario return false;
	 */
	public static boolean eliminarJugador(String nombre) {

		// comprobar si el jugador existe,leer el fichero jugadores registrados
		// me devuelve una lista de jugador , lo elimina , y vuelvo a escribir
		// la lista
		if (DBManager.existsRankingPersona(nombre) == true) {
			if (DBManager.deleteJugador(nombre)) {
				System.out.println("JUGAODR " + nombre + " HA SIDO ELIMINADO EN EL SISTEMA");
				return true;
			}
		} else {
			System.out.println(nombre + " NO EXISTE EN EL SISTEMA");
		}
		return false;
	}

	/**
	 * menu con las operaciones que puede hacer con los jugadores
	 */
	public static void submenu() {
		System.out.println("********************************************");
		System.out.println("*           MENÚ GESTION JUGADORES         *");
		System.out.println("*           1. LISTAR JUGADOR              *");
		System.out.println("*           2. AÑADIR JUGADOR    		   *");
		System.out.println("*           3. ELIMINAR JUGADOR   		   *");
		System.out.println("*           4. VOLVER AL MENÚ PRINCIPAL    *");
		System.out.println("********************************************");
	}

	/**
	 * opciones que corresponde el submenu
	 * 
	 * @see submenu
	 */
	public static void opcionJugador() {
		String res = " ";
		boolean opcion = false;
		while (!opcion) {
			submenu();
			System.out.println("   	ELIGES UNA OPCIÓN, INTRODUCES(número): ");
			res = teclado.next();
			switch (res) {
			case "1":
				DBManager.listarJugador();
				break;
			case "2":
				System.out.println("INTRODUCE EL NOMBRE DE JUGADOR QUE DESEAS AÑADIR: (NO PUEDE CONTENER ESPACIO)");

				if (anadirJugador(teclado.next())) {
					System.out.println("AÑADIDIR JUGADOR CON ÉXITO");
				} else {
					System.out.println("ERROR , NO HA PODIDO AÑADIR JUGADOR");
				}
				break;
			case "3":
				System.out.println(
						"INTRODUCE EL NOMBRE (MINUSCULA) DE JUGADOR QUE DESEAS ELIMINAR: (NO PUEDE CONTENER ESPACIO)");
				if (eliminarJugador(teclado.next())) {
					System.out.println("ELIMINAR JUGADOR CON ÉXITO");
				} else {
					System.out.println("NO HA PODIDO ELIMINAR JUGADOR");
				}
				break;
			case "4":
				opcion = true;
				break;
			default:
				System.out.println("HAS ELEGIDO UNA OPCIÓN QUE NO ESTÁ VÁLIDO");
			}
			teclado.nextLine();
		}
	}

	/**
	 * para crear jugadorCPU y lo añade dentro de la lista de los jugadores de esa
	 * partida
	 * 
	 * @param res1      respuesta de jugador : la cantidad de cpu a crear
	 * @param jugadores de esa partida solamente
	 * @return List<Jugador> jugadorCPU de esa partida
	 */
	public static List<Jugador> crearCPU(int res1, List<Jugador> jugadores) {

		for (int i = 0; i < res1; i++) {
			jugadores.add(new JugadorCPU("CPU" + (i + 1)));
		}
		return jugadores;
	}

	/**
	 * crear jugadorHumano y lo añade dentro de la lista de los jugadores de esa
	 * partida
	 * 
	 * @param res2 cantidad de jugadorHumano a crear
	 * @return List<Jugador> jugaodres de esa partida solamente
	 * @return jugadorCPU de esa partida
	 * @throws IOException
	 */
	public static List<Jugador> crearHumano(int res2, List<Jugador> jugadores) throws IOException {
		int cont = 0;

		while (cont < res2) {

			System.out.println("¿ ERES JUGADOR NUEVO ( S / N ) ?");

			String answer = teclado.next();
			switch (answer.toUpperCase()) {
			case "S":
				// jugadorNuevo:
				try {

					String nombreSinTildeMinus = pedirNombre();
					cont = tratoNuevoJugador(nombreSinTildeMinus, jugadores, cont);

				} catch (IllegalArgumentException e) {
					System.out.println("ERROR EN EL VALOR DE LA ENTRADA");
				}
				break;
			case "N":
				// jugadorAntiguo :

				try {
					String nombreSinTildeMinus = pedirNombre();
					cont = tratoAntiguoJugador(nombreSinTildeMinus, jugadores, cont);

				} catch (IllegalArgumentException e) {
					System.out.println("ERROR EN EL VALOR DE LA ENTRADA");
				}
				break;
			default:
				System.out.println("SOLO RESPONDE S Ó N ");
				System.out.println("");
			}
			teclado.nextLine();
		}
		return jugadores;
	}

	/**
	 * Tratamiento que hace si es un jugador nuevo , si todo va bien , añade ese
	 * jugador a la lista de jugaodres de esa partida
	 * 
	 * @param nombreSinTildeMinus
	 * @param jugadores
	 * @param cont
	 * @return numero de jugador que ha conseguido de crear
	 */
	// jugadorNuevo:
	// pasa el nombre introducido a minuscula sin tilde para que no haya conflicto
	// después
	// comprobar si existe el jugador registrado en el sistema
	// si ya existe, vuelve a pregunta si es jugador nuevo
	// si no existe , le da la alta
	public static int tratoNuevoJugador(String nombreSinTildeMinus, List<Jugador> jugadores, int cont) {

		if (DBManager.existsRankingPersona(nombreSinTildeMinus)) {
			System.out.println("EL NOMBRE INTRODUCIDO YA EXISTE , INTRODUCE OTRO" + "\n");
		} else {

			if (darAlta(nombreSinTildeMinus, jugadores)) {
				System.out.println("HAS REGISTRADO CON EL NOMBRE: " + nombreSinTildeMinus + " EN NUESTRO JUEGO");
				cont++;
			}
			System.out.println("");
		}
		return cont;
	}

	/**
	 * Tratamiento que hace si es un jugador antiguo , si todo va bien , añade ese
	 * jugador a la lista de jugaodres de esa partida
	 * 
	 * @param nombreSinTildeMinus
	 * @param jugadores
	 * @param cont
	 * @return numero de jugador que ha conseguido de crear
	 */
	// jugadorAntiguo :
	// comprobar si el jugador existe en el sistema
	// comprobar si existe en la partida actual
	// si no existe le da opción de elegir de que si quiere dar la alta
	public static int tratoAntiguoJugador(String nombreSinTildeMinus, List<Jugador> jugadores, int cont) {
		if (DBManager.existsRankingPersona(nombreSinTildeMinus)) {

			if (existirJugadorPartida(nombreSinTildeMinus, jugadores)) {
				Jugador j1 = new JugadorHumano(nombreSinTildeMinus);
				jugadores.add(j1);
				cont++;
				System.out.println("BIENVENIDA DE NUEVO " + nombreSinTildeMinus + "\n");

			} else {
				System.out.println("JUGADOR " + nombreSinTildeMinus + " YA ESTÁ EN LA PARTIDA ACTUAL, ELIJAS OTRO");
			}
		} else {
			System.out.println("EL NOMBRE INTRODUCIDO NO EXISTE." + "\n");
			teclado.nextLine();
			System.out.println(" ¿ QUIERES QUE DAMOS LA ALTA DEL NOMBRE INTRODUCIDO COMO NUEVO JUGADOR ? (S/N)");
			String answer2 = teclado.next();
			if (answer2.equalsIgnoreCase("S")) {
				if (darAlta(nombreSinTildeMinus, jugadores)) {
					cont++;
				}
			}
		}

		return cont;
	}

	/**
	 * pedir nombre de jugador que sea formato correcto (a-zA-Z) y lo convierte a
	 * mins sin tilde
	 * 
	 * @return nombre mins sin tilde
	 */
	public static String pedirNombre() {
		System.out.println("INTRODUCE EL NOMBRE (sin tilde, minúscula) PARA EMPEZAR LA PARTIDA: ");
		String nombre = teclado.next();

		// este es para comprobar si cadena introducido está entre a-z ó A-Z , si no
		// cumple esto , va a seguir pidiendo un nombre que corresponda formato correcto
		while (!Utilidad.comprobarCaracteres(nombre)) {
			System.out.println("NOBRE INTRODUCIDO ESTÁ EN UN FORMATO INCORRECTO. INTENTA OTRA VEZ: ");
			nombre = teclado.next();
		}

		String nombreSinTildeMinus = Utilidad.palabraSinTildeMinus(nombre);
		return nombreSinTildeMinus;
	}

	/**
	 * para comprobar si el jugador está bien registrado en el sistema
	 * 
	 * @param nombre
	 * @param jugadores
	 * @return true , proceso correcto , en caso contrario return false
	 */
	public static boolean darAlta(String nombre, List<Jugador> jugadores) {
		boolean creado = false;
		if (!creado) {
			Jugador j1 = new JugadorHumano(nombre);
			jugadores.add(j1);

			DBManager.insertJugador(nombre, 0);

			System.out.println("PROCESO DE REGISTRAR EXITOSO ");
			System.out.println("BIENVENIDA " + nombre + " AL JUEGO" + "\n");
			return true;
		}
		return false;
	}

	/**
	 * comprobar si existe el jugador existe en el sistema
	 * 
	 * @param nombre
	 * @return @return true existe , false no existe
	 */
	public static boolean existirJugadorPartida(String nombre, List<Jugador> jugadores) {
		for (Jugador j : jugadores) {
			if (j.getNombre().equalsIgnoreCase(nombre)) {
				return false;
			}
		}
		return true;
	}

}
