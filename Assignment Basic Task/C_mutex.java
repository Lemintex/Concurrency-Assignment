//2715375

import java.io.IOException;
import java.net.*;

public class C_mutex extends Thread {
    //COORDINATOR BUFFER
    C_buffer buffer;

    //TOKEN GRANTING SOCKET
    Socket s;

    //PORT FOR TOKEN REQUEST
    int port;

    //IP AND PORT OF REQUESTING NODE
    String n_host;
    int n_port;

    public C_mutex(C_buffer b, int p) {
        buffer = b;
        port = p;
    }


    public void go() {
        try {
            //CREATE SERVER SOCKET THAT WILL RECEIVE RETURNED TOKEN
            ServerSocket CoordinatorSocket = new ServerSocket(port);

            while (true) {
                //PRINT BUFFER CONTENT FOR DEBUGGING PURPOSES
//    			buffer.show();

                if (buffer.size() != 0) { //IF BUFFER IS NOT EMPTY
                    //GET FIRST NODE IP AND PORT FROM BUFFER
                    n_host = (String) buffer.get();
                    n_port = Integer.parseInt((String) buffer.get());

                    //GRANT TOKEN TO NODE
                    try {
                        s = new Socket(n_host, n_port);
                        System.out.println("Granted token to node " + n_port);
                    } catch (IOException e) {
                        System.err.println("CRASH: Mutex connecting to the node for granting the TOKEN" + e);
                    }

                    //RECEIVE TOKEN FROM NODE
                    try {
                        CoordinatorSocket.accept();
                    } catch (IOException e) {
                        System.err.println("CRASH: Mutex waiting for the TOKEN back" + e);
                    }
                }// ENDIF
            }// ENDWHILE
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public void run() {
        go();
    }
}
