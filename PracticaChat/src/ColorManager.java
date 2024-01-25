import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ColorManager {

    // Códigos ANSI para colores de texto
    /*
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[97m";
    public static final String GRAY = "\u001B[37m";
    public static final String BROWN = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    */
    public static final String RESET = "\u001B[0m";
    public static final List<String> COLORS = new ArrayList<>(List.of(
            "\u001B[31m", "\u001B[32m", "\u001B[93m",
            "\u001B[34m", "\u001B[35m", "\u001B[36m",
            "\u001B[97m", "\u001B[37m", "\u001B[33m",
            "\u001B[35m", "\u001B[91m", "\u001B[92m"
    ));

    public static List<String>usedColor=new ArrayList<>();

    public ColorManager() {
    }

    // Método para asignar color a un usuario y asociar su socket
    public String assignColor() {
            String color = availableColor();
            if (color != null) {
                usedColor.add(color);
                return color;
            }
        return RESET;
    }

    private String availableColor() {
        for (String color : COLORS) {
           if(!usedColor.contains(color)){
               return color;
           }
        }
        return null;
    }

    // Método para imprimir texto coloreado
    public String printTextColor(User user,String text) {
        return "[ "+user.getColor() +" ]: " + text + RESET;
    }
}
