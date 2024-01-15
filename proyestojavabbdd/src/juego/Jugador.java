package juego;

public abstract class Jugador implements Comparable<Jugador> {

	private String nombre;
	private int punto;

	/**
	 * constructor de jugador
	 * 
	 * @param nombre
	 * @throws IllegalArgumentException si el nombre contiene espacio
	 */
	public Jugador(String nombre) throws IllegalArgumentException {
		if (nombre.contains(" ")) {
			throw new IllegalArgumentException("el nombre no se puede contener espacio");
		}
		this.nombre = nombre;
		this.punto = 0;
	}

	public Jugador(String nombre, int punto) {
		this.nombre = nombre;
		this.punto = punto;
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * para prohibir que el nombre contenga el espacio
	 * 
	 * @param nombre
	 * @throws IllegalArgumentException si nombre contiene espacio
	 */
	public void setNombre(String nombre) throws IllegalArgumentException {
		if (nombre.contains(" ")) {
			throw new IllegalArgumentException("el nombre no se puede contener espacio");
		}
		this.nombre = nombre;
	}

	public int getPunto() {
		return punto;
	}

	/**
	 * para prohibir que el punto sea negativa
	 * 
	 * @param punto
	 * @throws IllegalArgumentException si punto es negativo
	 */
	public void setPunto(int punto) {
		if (punto < 0) {
			throw new IllegalArgumentException("punto no puede ser negativo");
		}
		this.punto = punto;
	}

	/**
	 * para mostrar nombre y punto de jugador
	 * 
	 * @return nombre y punto de jugador
	 */
	public String mostrarJugador() {
		return (" NOMBRE: " + this.getNombre() + " PUNTO: " + this.getPunto());
	}

	/**
	 * para responder la pregunta pasada por parametro
	 * 
	 * @param pregunta que toca aleatoriamente
	 */
	public abstract void responder(Pregunta pregunta);

	/**
	 * compara los puntos de jugadores
	 */
	@Override
	public int compareTo(Jugador otroJugador) {
		return Integer.compare(otroJugador.getPunto(), this.punto);
	}
}
