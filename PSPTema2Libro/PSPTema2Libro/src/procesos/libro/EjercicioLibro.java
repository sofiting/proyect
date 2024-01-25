package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class EjercicioLibro {
    public static void main(String[] args) {

        //TINGMEI HUANG
        //Este programa ejecutará en el sistema operativo window 10 con intellij de open jdk de versión 19
        //La clase Reemplazador,Transformador,EjercicioLibroVariable no especifica en qué paquete hay que poner , las he dejado en paquete libro

        String txtSalida = LibroUtils.ficheroSalida;
        Path ruta = LibroUtils.path;
        String nombreClase1 = "procesos.libro.Mayusculas";
        String nombreClase2 = "procesos.libro.Minusculas";
        String nombreClase3 = "procesos.libro.Reemplazador";
        String nombreClase4 = "procesos.libro.Transformador";


    // Escribo estos dos lineas para no borrar salidaLibroAlicia.txt manualmente
        File archivo = new File(txtSalida);
        archivo.delete();

        try {

            ProcessBuilder builder1 = new ProcessBuilder("java", nombreClase1, "0", "719");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // PRUEBA DE LA CLASE Reemplazar Alice por PACO

            // ProcessBuilder builder1 = new ProcessBuilder("java", nombreClase3, "0", "719");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //PRUEBA DE LA CLASE Transformador

            // ProcessBuilder builder1 = new ProcessBuilder("java", nombreClase4, "0", "719","mayuscula");
            // ProcessBuilder builder1 = new ProcessBuilder("java", nombreClase4, "0", "719","minuscula");
            // ProcessBuilder builder1 = new ProcessBuilder("java", nombreClase4, "0", "719","Bruce Willis");

            builder1.directory(new File(String.valueOf(ruta)));
            builder1.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));
            builder1.redirectError(ProcessBuilder.Redirect.INHERIT);



            ProcessBuilder builder2 = new ProcessBuilder("java", nombreClase2, "720", "1441");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // PRUEBA DE LA CLASE Reemplazar Alice por PACO

            // ProcessBuilder builder2 = new ProcessBuilder("java", nombreClase3, "720", "1441");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //PRUEBA DE LA CLASE Transformador

            // ProcessBuilder builder2 = new ProcessBuilder("java", nombreClase4, "720", "1441","mayuscula");
            // ProcessBuilder builder2 = new ProcessBuilder("java", nombreClase4, "720", "1441","minuscula");
            // ProcessBuilder builder2 = new ProcessBuilder("java", nombreClase4, "720", "1441","Bruce Willis");

            builder2.directory(new File(String.valueOf(ruta)));
            builder2.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));
            builder2.redirectError(ProcessBuilder.Redirect.INHERIT);




            ProcessBuilder builder3 = new ProcessBuilder("java", nombreClase1, "1442", "2162");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // PRUEBA DE LA CLASE Reemplazar Alice por PACO

           // ProcessBuilder builder3 = new ProcessBuilder("java", nombreClase3, "1442", "2162");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //PRUEBA DE LA CLASE Transformador

            // ProcessBuilder builder3 = new ProcessBuilder("java", nombreClase4, "1442", "2162","mayuscula");
            // ProcessBuilder builder3 = new ProcessBuilder("java", nombreClase4, "1442", "2162","minuscula");
            // ProcessBuilder builder3 = new ProcessBuilder("java", nombreClase4, "1442", "2162","Bruce Willis");


            builder3.directory(new File(String.valueOf(ruta)));
            builder3.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));
            builder3.redirectError(ProcessBuilder.Redirect.INHERIT);



            ProcessBuilder builder4 = new ProcessBuilder("java", nombreClase2, "2163", "2883");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // PRUEBA DE LA CLASE Reemplazar Alice por PACO

            // ProcessBuilder builder4 = new ProcessBuilder("java", nombreClase3, "2163", "2883");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //PRUEBA DE LA CLASE Transformador

            // ProcessBuilder builder4 = new ProcessBuilder("java", nombreClase4, "2163", "2883","mayuscula");
            // ProcessBuilder builder4 = new ProcessBuilder("java", nombreClase4, "2163", "2883","minuscula");
            // ProcessBuilder builder4 = new ProcessBuilder("java", nombreClase4, "2163", "2883","Bruce Willis");

            builder4.directory(new File(String.valueOf(ruta)));
            builder4.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));
            builder4.redirectError(ProcessBuilder.Redirect.INHERIT);




            ProcessBuilder builder5 = new ProcessBuilder("java", nombreClase1, "2884", "3608");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // PRUEBA DE LA CLASE Reemplazar Alice por PACO

            // ProcessBuilder builder5 = new ProcessBuilder("java", nombreClase3, "2884", "3608");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //PRUEBA DE LA CLASE Transformador

            // ProcessBuilder builder5 = new ProcessBuilder("java", nombreClase4, "2884", "3608","mayuscula");
            // ProcessBuilder builder5 = new ProcessBuilder("java", nombreClase4, "2884", "3608","minuscula");
            // ProcessBuilder builder5 = new ProcessBuilder("java", nombreClase4, "2884", "3608","Bruce Willis");

            builder5.directory(new File(String.valueOf(ruta)));
            builder5.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));
            builder5.redirectError(ProcessBuilder.Redirect.INHERIT);

            System.out.println("Iniciando Procesos...");

            Process process1 = builder1.start();
            System.out.println("Proceso 1: 0 a linea 720 -> ha terminado");
            process1.waitFor();

            Process process2 = builder2.start();
            System.out.println("Proceso 2: 721 a linea 1442 -> ha terminado");
            process2.waitFor();

            Process process3 = builder3.start();
            System.out.println("Proceso 3: 1443 a 2163 -> ha terminado");
            process3.waitFor();

            Process process4 = builder4.start();
            System.out.println("Proceso 4: 2164 a 2884 -> ha terminado");
            process4.waitFor();

            Process process5 = builder5.start();
            System.out.println("Proceso 5: 2885 a 3609 -> ha terminado");
            process5.waitFor();

            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
