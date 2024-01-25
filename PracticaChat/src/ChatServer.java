import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ChatServer extends Thread {


    private final Socket socket;
    private User currentUsername;

    public ChatServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ColorManager cm = new ColorManager();
        DataInputStream dis = null;
        DataOutputStream dos = null;
        boolean notification = true;

        try {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
            writeDataOutputStream(dos, "Welcome to chat !!!");
            register(dos, dis, cm);
            sendPublicMessage(currentUsername.getUsername() + " entered chat !!!", cm,notification);

            // Se utiliza un bucle infinito para recibir mensajes continuamente
            while (true) {

                String message = dis.readUTF();

                if (message.equalsIgnoreCase("exit")) {
                    // Si el cliente envía "exit", salimos del bucle
                    Server.nickUsers.remove(currentUsername.getUsername());
                    notification = true;
                    sendPublicMessage(currentUsername.getUsername() + " left chat", cm,notification);
                    break;
                }

                System.out.println(currentUsername.getUsername() + " say: " + message);

                if (message.startsWith("@")) {
                    // Extraer el nombre de remitente
                    int spaceIndex = message.indexOf(' ');
                    if (spaceIndex != -1) {
                        String receiver = message.substring(1, spaceIndex);
                        String privateMessage = message.substring(spaceIndex + 1);
                        sendPrivateMessage(dos, receiver, privateMessage, cm, false);
                    }
                } else {
                    notification=false;
                    sendPublicMessage(message, cm,notification);
                }
            }

        } catch (IOException e) {

            System.out.println(currentUsername.getUsername() + " disconnected unexpectedly.");

        } finally {
            // Cierre de recursos
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
                Server.nickUsers.remove(currentUsername.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void sendPublicMessage(String message, ColorManager cm, Boolean notification) throws IOException {
        String publicMessage = "";
        for (Map.Entry<String, User> entry : Server.nickUsers.entrySet()) {
            if (!currentUsername.getUsername().equalsIgnoreCase(entry.getKey())) {
                if (!notification) {
                    publicMessage = publicMessage(cm, message);
                } else {
                    publicMessage = notification(cm, message);
                }

                writeSocket(entry.getValue().getSocket(), publicMessage);
            }
        }
        Server.chatHistory.add(publicMessage + "\n");
    }

    private String publicMessage(ColorManager cm, String publicMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentUsername.getColor()).append("[ ").append(currentUsername.getUsername()).
                append(" ]: ").append(publicMessage);
        return sb.toString();
    }

    private String notification(ColorManager cm, String publicMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentUsername.getColor()).append(publicMessage);
        return sb.toString();
    }

    private void sendPrivateMessage(DataOutputStream dos, String receiver, String privateMessage, ColorManager cm, boolean chat) throws IOException {
        User user = Server.nickUsers.get(receiver);
        if (user != null) {
            writeSocket(user.getSocket(), privateMessage(cm, privateMessage,chat));
        } else {
            writeDataOutputStream(dos, "[ " + receiver + " ] doesn`t exist or is left");
        }
    }

    private String privateMessage(ColorManager cm, String privateMessage, Boolean chat) {
        StringBuilder sb = new StringBuilder();
        if (!chat) {
            sb.append(currentUsername.getColor()).append("[ ").append(currentUsername.getUsername()).
                    append(" PRIVATE ]: ").append(privateMessage);
        } else {
            // Mostrar mensajes privados en el historial sin el prefijo
            sb.append(privateMessage);
        }
        return sb.toString();
    }


    //enviar mensage segun socket
    private static void writeSocket(Socket socket, String message) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        writeDataOutputStream(dataOutputStream, message);
    }

    //registrar usuario, para evitar dos usuario con el mismo nombre
    private void register(DataOutputStream dos, DataInputStream dis, ColorManager cm) throws IOException {
        writeDataOutputStream(dos, "Enter your username:");
        String username = null;

        while (true) {
            username = dis.readUTF();
            System.out.println("User enter the name: " + username);
            synchronized (ChatServer.class) {
                if (!Server.nickUsers.containsKey(username)) {
                    String color = cm.assignColor();

                    User user = new User(username, socket, color);
                    currentUsername = user;
                    Server.nickUsers.put(username, user);
                    break;
                } else {
                    writeDataOutputStream(dos, "The username you entered already exists, please re-enter: ");
                }
            }
        }
        writeDataOutputStream(dos, username+", you become a user in this chat :)");
        //para recupera mensajes
        //hay que retocar!!!
        if (username != null) {
            // Obtener los últimos 50 mensajes o todos si hay menos de 50
            int startIdx = Math.max(0, Server.chatHistory.size() - 50);
            List<String> recentMessages = Server.chatHistory.subList(startIdx, Server.chatHistory.size());

            // Enviar cada mensaje al usuario registrado
            for (String message : recentMessages) {
                sendPrivateMessage(dos, username, message, cm,true);
            }
        }

    }

    //enviar mensaje a cliente
    private static void writeDataOutputStream(DataOutputStream dos, String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }
}
