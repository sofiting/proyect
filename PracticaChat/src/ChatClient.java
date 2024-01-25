import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class ChatClient extends Thread {
    private DataInputStream dis;

    private boolean finishChat = false;

    public ChatClient(DataInputStream dis) {
        this.dis = dis;
    }

    @Override
    public void run() {
        while (!finishChat) {
            try {
                String menssaje = dis.readUTF();
                if(menssaje.equalsIgnoreCase("Enter your username:")){
                    System.out.println(menssaje);
                }else {
                    System.out.println("\t\t\t\t" + menssaje);
                }
            } catch (EOFException e) {
                // controla por cierre de clientsocket
                System.out.println("The connection to the server has been interrupted in a normal way.");
                break;
            } catch (IOException e) {
                if ("Connection reset".equals(e.getMessage())) {
                    System.out.println("The connection to the server has been unexpectedly reset.");
                    break;
                }
                if (!finishChat) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void stopChat() {
        finishChat = true;
    }
}
