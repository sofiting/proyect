package juego;

public abstract class Pregunta {

	private String enunciado;
	private String respuesta;

	public Pregunta(String enunciado, String respuesta) {
		this.enunciado = enunciado;
		this.respuesta = respuesta;
	}

	public Pregunta() {
	}

	/**
	 * para generar pregunta
	 * 
	 * @return String enunciado de la pregunta generada
	 */
	public abstract String generarPregunta();

	/**
	 * comprobar si la respuesta es correcta
	 * 
	 * @param res
	 * @return true o false
	 */
	public abstract boolean comprobarRespuesta(String res);

	/**
	 * para mostrar pregunta
	 */
	public abstract void mostrarPregunta();

	/**
	 * @return the enunciado
	 */
	public String getEnunciado() {
		return enunciado;
	}

	/**
	 * @param enunciado the enunciado to set
	 */
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
