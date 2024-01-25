package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class EjercicioLibroVariable {
    public static void main(String[] args) {

        //TINGMEI HUANG
        //Este programa ejecutará en el sistema operativo window 10 con intellij de open jdk de versión 19
        //La clase Reemplazador,Transformador,EjercicioLibroVariable no especifica en qué paquete hay que poner , las he dejado en paquete libro


        String txtSalida = LibroUtils.ficheroSalida;
        Path ruta = LibroUtils.path;

        // Escribo estos dos lineas para no borrar salidaLibroAlicia.txt manualmente
        File archivo = new File(txtSalida);
        archivo.delete();


        Scanner sc = new Scanner(System.in);

        System.out.println("introduce el numero de proceso deseado:");
        int numeroProceso = sc.nextInt();

        sc.nextLine(); //limpiar buffer



            try {
                int longitudParte = 0;
                int longitudTotal = LibroUtils.totalLineas().size();

                longitudParte = longitudTotal / numeroProceso;

                int fin;

        for (int i = 0; i < numeroProceso; i++) {

               int inicio = i * longitudParte;

               if (i == numeroProceso - 1) {
                    fin = longitudTotal - 1;
               } else {
                    fin = (i + 1) * longitudParte - 1;
               }

                ProcessBuilder builder = new ProcessBuilder("java",nombreClase(randomOption()), String.valueOf(inicio),String.valueOf(fin));

                builder.directory(new File(String.valueOf(ruta)));

                builder.redirectOutput(ProcessBuilder.Redirect.appendTo(archivo));

                builder.redirectError(ProcessBuilder.Redirect.INHERIT);

                Process process = builder.start();

                process.waitFor();
            }

            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomOption() {
        Random random = new Random();
        int opcion = random.nextInt(3);

        // 0: mayúscula, 1: minúscula, 2: reemplazador

        switch (opcion) {
            case 0:
                return "mayusculas";
            case 1:
                return "minusculas";
            case 2:
                return "reemplazador";
            default:
                return "";
        }
    }

    public static String nombreClase(String operacion) {
        switch (operacion) {
            case "mayusculas":
                return "procesos.libro.Mayusculas";
            case "minusculas":
                return "procesos.libro.Minusculas";
            case "reemplazador":
                return "procesos.libro.Reemplazador";
            default:
                return "";
        }
    }

}


