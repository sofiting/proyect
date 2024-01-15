package mastermindgame;

public class ResultadoIntento {

	private String combinacion;
	private String resultado;

	public ResultadoIntento(String combinacion, String resultado) {
		this.combinacion = combinacion;
		this.resultado = resultado;
	}

	public void mostrarResul() {
		System.out.println("COMBINACIÓN: " + this.getCombinacion() + " RESULTADO: " + this.getResultado());
	}

	public String getCombinacion() {
		return combinacion;
	}

	public void setCombinacion(String combinacion) {
		this.combinacion = combinacion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
