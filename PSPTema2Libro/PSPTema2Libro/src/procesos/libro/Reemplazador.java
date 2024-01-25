package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.IOException;
import java.util.List;

public class Reemplazador {

    public static void main(String[] args) {

        int lineaInicio = Integer.parseInt(args[0]);
        int lineaFin = Integer.parseInt(args[1]);

        try {
            List<String> lineas = LibroUtils.totalLineas();

            for (int i = lineaInicio; i <= lineaFin; i++) {
                String linea = lineas.get(i);
                linea = linea.replaceAll("Alice", "PACO");
                System.out.println(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
