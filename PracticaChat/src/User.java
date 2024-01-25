import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    Socket socket;

    String color;

    public User(String username, Socket socket, String color) {
        this.username = username;
        this.socket = socket;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

