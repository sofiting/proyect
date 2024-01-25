package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.IOException;
import java.util.List;

public class Transformador {
    public static void main(String[] args) {

        int lineaInicio = Integer.parseInt(args[0]);
        int lineaFin = Integer.parseInt(args[1]);
        String argumentos = args[2];

        try {
            List<String> lineas = LibroUtils.totalLineas();

            for (int i = lineaInicio; i <= lineaFin; i++) {
                String linea = lineas.get(i);

                if (argumentos.equals("mayuscula")) {
                    System.out.println(linea.toUpperCase());
                } else if (argumentos.equals("minuscula")) {
                    System.out.println(linea.toLowerCase());
                } else {
                    linea = linea.replaceAll("Alice", args[2]);
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
