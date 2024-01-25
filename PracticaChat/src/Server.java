import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static List<String> chatHistory = new ArrayList<>();
    public static Map<String, User> nickUsers = new HashMap<>();

    //formato cuando recupera 50 mensaje;
    // cuando uno sale por fuerza xx is offline , notifica a los demas???

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            int listeningPort = 6565;
            System.out.println("SERVER: Creating server socket and binding on port: " + listeningPort);

            serverSocket = new ServerSocket(listeningPort);
            System.out.println("SERVER: Server socket created and bound");

            System.out.println("SERVER: Accepting connections. Server is blocked, waiting for incoming connections\n");

            while (true) {
                Socket newSocket = serverSocket.accept();
                System.out.println("SERVER: Connection received. New socket created. Port: " + newSocket.getPort());
                ChatServer chatServer = new ChatServer(newSocket);
                chatServer.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("SERVER: Closing the server socket");
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("SERVER: Server closed");
        }
    }


}
