package procesos.libro.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LibroUtils {

    public static Path path = Paths.get("C:\\Users\\huang\\psp\\PSPTema2Libro\\out\\production\\PSPTema2Libro");
    public static String rutaAlice = "C:\\Users\\huang\\psp\\PSPTema2Libro\\src\\procesos\\libro\\alice29.txt";
    public static String ficheroSalida = "salidaLibroAlicia.txt";




    public static List<String> totalLineas() throws IOException {
        return Files.readAllLines(Path.of(rutaAlice));
    }
}
