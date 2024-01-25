import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("CLIENT: Creating client socket");
            Socket clientSocket = new Socket();
            System.out.println("CLIENTE: Establishing the connection");
            InetSocketAddress addr = new InetSocketAddress("localhost", 6565);
            clientSocket.connect(addr);

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            ChatClient chatClient = new ChatClient(dis);
            chatClient.start();

            String message=null;

            do{
                message=sc.nextLine();
                writeDataOutputStream(dos,message);


            }while(!message.equalsIgnoreCase("exit"));

            chatClient.stopChat();

            sc.close();
            dis.close();
            dos.close();
            clientSocket.close();

            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeDataOutputStream(DataOutputStream dos, String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }
}
