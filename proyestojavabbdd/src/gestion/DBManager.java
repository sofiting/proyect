package gestion;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import juego.Ranking;
import main.Principal;
import utilidad.Utilidad;

import java.sql.ResultSet;

public class DBManager {

	// Conexión a la base de datos
	private static Connection conn = null;

	// Configuración de la conexión a la base de datos
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "juegoDamGGM";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
			+ "?serverTimezone=UTC";
	private static final String DB_USER = "dam2223";
	private static final String DB_PASS = "dam2223";
	private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
	private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

	// Configuración de la tabla ranking
	private static final String DB_R = "ranking";
	private static final String DB_R_SELECT = "SELECT * FROM " + DB_R;

	// Configuración de la tabla histórico
	private static final String DB_H = "histórico";
	private static final String DB_H_SELECT = "SELECT * FROM " + DB_R;
	private static final String DB_H_ID = "id_partida";

	private static final String nickname = "nickname";
	private static final String puntuacion = "puntuacion";

	//////////////////////////////////////////////////
	// MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
	//////////////////////////////////////////////////
	;

	/**
	 * Intenta cargar el JDBC driver.
	 * 
	 * @return true si pudo cargar el driver, false en caso contrario
	 */
	public static boolean loadDriver() {
		try {
			System.out.print("Cargando Driver...");
			// Class.forName("com.mysql.cj.jdbc.Driver"); ON LINUX
			// Class.forName("com.mysql.jdbc.Driver"); ON WINDOWS
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("OK!");
			return true;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Intenta conectar con la base de datos.
	 *
	 * @return true si pudo conectarse, false en caso contrario
	 */
	public static boolean connect() {
		try {
			System.out.print("Conectando a la base de datos...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("OK!");
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Comprueba la conexión y muestra su estado por pantalla
	 *
	 * @return true si la conexión existe y es válida, false en caso contrario
	 */
	public static boolean isConnected() {
		// Comprobamos estado de la conexión
		try {
			if (conn != null && conn.isValid(0)) {
				System.out.println(DB_MSQ_CONN_OK);
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(DB_MSQ_CONN_NO);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public static void close() {
		try {
			System.out.print("Cerrando la conexión...");
			conn.close();
			System.out.println("OK!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	//////////////////////////////////////////////////
	// MÉTODOS DE TABLA
	//////////////////////////////////////////////////

	// un metodo de insersion despues de tipo de pregunta
	// insert into pregunta values (' (variable)');
	public static void insertaPregunta(int tipo) {
		try {

			String sql = "INSERT INTO pregunta VALUES (" + tipo + ");";
			// System.out.println(sql);

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			int nr = stmt.executeUpdate(sql);
			
			stmt.close();
		} catch (SQLException e) {
			System.out.println("ERROR EN INSERCIÓN DE LA TABLA PREGUNTA");
			e.printStackTrace();
		}
	}

	// un metodo de select count(*) from pregunta where tipo =
	// "preguntaIngles"(variable);
	public static int countPregunta(int tipo) {
		int cantidad = 0;

		try {

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// realiza la consulta SQL
			String sql = "SELECT * FROM pregunta WHERE tipo = " + tipo + ";";
			// System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			// Si no hay primer registro entonces no existe pregunta
			if (!rs.first()) {
				return 0;
			}
			
			if(rs != null) {
				do {
					cantidad++;
				}while(rs.next());
				
			}

			// Todo bien, devolvemos el jugador
			return cantidad;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * select la informacion de toda la tabla y ordenada
	 * 
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param tabla                nombre de la tabla que quieres mostrar
	 * @param criterioOrdenacion   con que quieres oedenar
	 * @param sentidoOrden         des o asc
	 * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
	 */
	public static ResultSet selectTabla(int resultSetType, int resultSetConcurrency, String tabla,
			String criterioOrdenacion, String sentidoOrden) {
		try {
			Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);

			String query = "SELECT * FROM " + tabla;

			if (criterioOrdenacion != null) {
				query += " ORDER BY " + criterioOrdenacion + " " + sentidoOrden + ";";
			}
			ResultSet rs = stmt.executeQuery(query);
			// stmt.close();
			return rs;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * Este para mostrar todas las información de la tabla
	 * 
	 * @param tabla nombre de la tabla que quieres mostrar
	 * @return ResultSet (del tipo indicado) con la tabla
	 */
	public static ResultSet selectTabla(String tabla) {
		return selectTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, tabla, null, null);
	}

	/**
	 * Este es para comprobar si la tabla está vacío
	 * 
	 * @param tabla nombre de la tabla que quieres comprobar
	 * @return true está vacío , en caso contrario , return false
	 */
	public static boolean tablaVacio(String tabla) {
		try {

			ResultSet rs = selectTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, tabla, null, null);

			if (!rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("ERROR EN LA COMPROBAR TABLA VACÍO");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * select nombre que está dentro de la ranking ,obtener resultado de la persona
	 * seleccionado si no existe es null
	 * 
	 * @param nombre
	 * @return resultado de la persona seleccionado si no existe es null
	 */
	public static ResultSet getRanking(String nombre) {
		try {

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// realiza la consulta SQL
			String sql = DB_R_SELECT + " WHERE " + nickname + "='" + nombre + "';";
			// System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			// Si no hay primer registro entonces no existe el jugador
			if (!rs.first()) {
				return null;
			}

			// Todo bien, devolvemos el jugador
			return rs;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

	}
	///////////////////////////////////////////////
	// Métodos jugadores
	///////////////////////////////////////////////

	/**
	 * listar jugadores existentes , si no hay ninguno muestra mensaje de que está
	 * vacío
	 */
	public static void listarJugador() {
		try {
			ResultSet rs = selectTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, DB_R, null, null);

			if (!rs.next()) {
				System.out.println("LA TABLA ESTÁ VACÍA , TODAVÍA NO HAY NINGÚN JUGADOR");
			} else {
				do {
					String n = rs.getString(nickname);
					System.out.println(n + "\t");
				} while (rs.next());
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * insertar un jugador en el sistema
	 * 
	 * @param nombre
	 * @param punto
	 * @return true si ha insertado correctamente el jugador en el sistema , return
	 *         false , en caso contrario
	 */
	public static boolean insertJugador(String nombre, int punto) {
		try {
			String sql = "INSERT INTO " + DB_R + " (nickname, puntuacion) VALUES ('" + nombre + "'," + punto + ")";
			// System.out.println(sql);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// EN LA VARIABLE NR ESTARIAN EL NÚMERO DE REGISTROS QUE SE HAN ACTUALIZADO.
			int nr = stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * eliminar un jugador en el sistema
	 * 
	 * @param nombre
	 * @return true si ha eliminado correctamente el jugador en el sistema , return
	 *         false , en caso contrario
	 */
	public static boolean deleteJugador(String nombre) {
		try {

			// Obtenemos la persona
			ResultSet rs = getRanking(nombre);

			// Si no existe el Resultset
			if (rs == null) {
				System.out.println("ERROR. ResultSet null.");
				return false;
			}

			// Si existe y tiene primer registro, lo eliminamos
			if (rs.first()) {
				rs.deleteRow();
				rs.close();
				System.out.println("OK!");
				return true;
			} else {
				System.out.println("ERROR. ResultSet vacío.");
				return false;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * comprobar si existe la persona en el ranking
	 * 
	 * @param nombre
	 * @return true existe la persona en la tabla , return false no existe
	 */
	public static boolean existsRankingPersona(String nombre) {

		try {
			// Obtenemos el jugador
			ResultSet rs = getRanking(nombre);

			// Si rs es null, se ha producido un error
			if (rs == null) {
				return false;
			}

			// Si no existe primer registro
			if (!rs.first()) {
				rs.close();
				return false;
			}

			// Todo bien, existe el jugador
			rs.close();
			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	///////////////////////////////////////////////
	// Métodos ranking
	///////////////////////////////////////////////

	/**
	 * select la tabla ranking
	 */
	public static void printTablaRanking() {
		try {
			ResultSet rs = selectTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, DB_R, puntuacion,
					"DESC");

			if (!rs.next()) {
				System.out.println("LA TABLA RANKING ESTÁ VACÍA");
			} else {
				do {
					String n = rs.getString(nickname);
					int e = rs.getInt(puntuacion);
					System.out.println(n + "\t" + e);
				} while (rs.next());
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * actualizar el ranking
	 * 
	 * @param nombre , nombre de jugador
	 * @param punto  , punto que ha ganado por una partida recien hecha
	 */
// un metodo que selecciona puntuacion que tiene el jugador en la tabla
// pasa su punto de esa partida 
// genera un variable que suma los dos , al final hacer un update en la tabla
// update ranking set puntuacion = puntoAc where nickname = nombre
	public static void updateRanking(String nombre, int punto) {
		try {
			int puntoAnti = selectPunto(nombre);
			int puntoAc = punto + puntoAnti;

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE " + DB_R + " SET " + puntuacion + " = " + puntoAc + " WHERE " + nickname + " = '"
					+ nombre + "' ;";
			int nr = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * obtener los punto de l jugadoe si existe
	 * 
	 * @param nombre , nombre del jugaodr
	 * @return punto que tiene anteriormente en las partidas anteriores, si no
	 *         existe return 0
	 */
	public static int selectPunto(String nombre) {
		int punto = 0;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT " + puntuacion + " FROM " + DB_R + " WHERE " + nickname + " = '" + nombre + "' ;";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				punto = rs.getInt(puntuacion);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punto;
	}

	// SI tiene empate no se puede poner exactamente quien es el primero !!!

	/**
	 * buscar la posicion de la persona dentro del ranking
	 * 
	 * @param nombre
	 */
	public static void buscarRanking(String nombre) {
		String sql = "";
		try {
			String nombreSinTildeMayus = Utilidad.palabraSinTildeMinus(nombre);
			Boolean jugadorExiste = existsRankingPersona(nombreSinTildeMayus);

			if (jugadorExiste != null && jugadorExiste) {
				sql = "SELECT subquery.row" + " FROM (" + "   SELECT (@row := @row + 1) AS row, nickname"
						+ "   FROM ranking, (SELECT @row := 0) r" + "   ORDER BY puntuacion DESC" + ") AS subquery"
						+ " WHERE subquery.nickname = '" + nombreSinTildeMayus + "'";

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					int posicion = rs.getInt("row");
					System.out.println(nombreSinTildeMayus + " está en el número " + posicion + " del ranking");
				}

				rs.close();
				stmt.close();
			} else {
				System.out.println(nombreSinTildeMayus + " NO EXISTE TODAVÍA");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * PARA VER LOS 3 PRIMEROS DE LA RANKING
	 */
	public static void verTopX() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = DB_R_SELECT + " ORDER BY " + puntuacion + " DESC LIMIT 3;";
			ResultSet rs = stmt.executeQuery(sql);

			int contador = 0;

			while (rs.next()) {
				String nickname = rs.getString("nickname");
				int puntuacion = rs.getInt("puntuacion");
				System.out.println("NOMBRE: " + nickname + " PUNTO: " + puntuacion);
				contador++;
			}

			if (contador == 0) {
				System.out.println("LA TABLA RANKING TODAVÍA ESTÁ VACÍA");
			} else if (contador < 3) {
				System.out.println("Todavía no hay suficientes registros. Registros disponibles: " + contador);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println("VER TOP 3 HA PRODUCIDO UN ERROR");
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////
	// Métodos historico
	///////////////////////////////////////////////

	/**
	 * mostrar la tabla histórico
	 */
	public static void printTablaHistorico() {
		try {
			ResultSet rs = selectTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, DB_H, null, null);

			if (!rs.next()) {
				System.out.println("LA TABLA HISTORICO ESTÁ VACÍA");
			} else {
				do {
					int id = rs.getInt(DB_H_ID);
					String n = rs.getString(nickname);
					int e = rs.getInt(puntuacion);
					System.out.println(id + "\t" + n + "\t" + e);
				} while (rs.next());
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * insertar historico
	 * 
	 * @param id     , id de la partida
	 * @param nombre , nombre del jugador
	 * @param punto  , punto que ha conseguido en esa partida
	 */
	public static void insertarHistorico(int id, String nombre, int punto) {
		// select max(id_partida) from historico;
		// if id_partida conseguido es 0 pues id_partida en el bucle de la primera
		// iteracion es 1
		// si es != 0 , max(id_partida) +1
		try {
			String sql = "INSERT INTO " + DB_H + " VALUES (" + id + ",'" + nombre + "'," + punto + ");";
			// System.out.println(sql);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int nr = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println("ERROR EN INSERTAR TABLA HISTORICO");
			e.printStackTrace();
		}

	}

	/**
	 * para sacar el maximo id_partida de la tabla
	 * 
	 * @return int max , maximo numero de id_partida existente en la tabla historico
	 */
	public static int selectMaxPartida() {
		int max = 0;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT MAX(" + DB_H_ID + ") AS max_id_partida FROM " + DB_H + ";";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// resulset empieza por 1
				max = rs.getInt(1);
			} else {
				max = 0;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("ERROR SELECCIONAR MAX ID PARTIDA");
			e.printStackTrace();
		}

		return max;
	}

}