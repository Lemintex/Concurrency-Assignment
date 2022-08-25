//2715375

import java.io.IOException;
import java.net.*;


public class C_receiver extends Thread {
    //COORDINATOR BUFFER
    private C_buffer buffer;

    //RECEIVER PORT
    private int port;

    //NODE SOCKETS
    private ServerSocket serverSocket;
    private Socket socketFromNode;

    //CONNECTION_R OBJECT
    private C_Connection_r connect;

    public C_receiver(C_buffer b, int p) {
        buffer = b;
        port = p;
    }

    public void run() {
        //CREATE SOCKET THAT LISTENS FOR NODES
        try {
            System.out.println("Waiting for connection");
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error creating server socket: " + e);
            System.exit(1);
        }

        while (true) {
            try {
                //ACCEPT INCOMING CONNECTION
                socketFromNode = serverSocket.accept();
                System.out.println("C_receiver: Coordinator has received a request...");

                //CREATE THREAD TO HANDLE REQUEST
                connect = new C_Connection_r(socketFromNode, buffer);
                connect.start();
            } catch (IOException e) {
                System.err.println("Exception when creating a connection " + e);
            }
        }

    }//end run

}