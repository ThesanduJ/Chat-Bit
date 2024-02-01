package lk.ijse.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//to avoid creating server use singleton design pattern
public class Server implements Runnable {

    private static Server server;
    private final ServerSocket serverSocket;

    private Server() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        System.out.println("Server Started...");

    }

    public static Server getServerSocket() throws IOException {
        return server==null ? server = new Server() : server;
    }


    @Override
    public void run() {
        while(!serverSocket.isClosed()){
            System.out.println(("Listening..."));
            try {
                Socket accepted = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(accepted);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
