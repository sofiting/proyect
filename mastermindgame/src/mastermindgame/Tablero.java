package mastermindgame;

import java.util.ArrayList;
import java.util.List;

import utilidades.Utilidades;

public class Tablero {

	private List<String>color;
	private List<String> espigas;
	private String colorOcultado;

	public Tablero() {
		this.color=new ArrayList<>();
		color.add("roja");
		color.add("azul");
		color.add("verde");
		color.add("amarilla");
		color.add("rosa");
		color.add("blanca");
		color.add("negra");
		color.add("marron");
		
		this.espigas = new ArrayList<>();
		this.colorOcultado=generaClavijas();
	}

	public String generaClavijas() {
        List<String> listaColores = new ArrayList<>(this.color); // Crear una copia de la lista original
		List<String> aleatorio = new ArrayList<>();
		String color="";

		for (int i = 0; i < 5; i++) {
			int numAleatorio = Utilidades.generaNumeroAleatorio(listaColores.size()-1, 0);
			aleatorio.add(listaColores.get(numAleatorio));
			//este a lo mejor puede influir la partida siguiente
			listaColores.remove(numAleatorio);
			///duda
			color=color+aleatorio.get(i)+" ";
		}
		
		return color;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getEspigas() {
		return espigas;
	}

	public void setEspigas(List<String> espigas) {
		this.espigas = espigas;
	}

	public String getColorOcultado() {
		return colorOcultado;
	}

	public void setColorOcultado(String colorOcultado) {
		this.colorOcultado = colorOcultado;
	}

}
