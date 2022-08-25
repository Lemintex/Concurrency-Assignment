//2715375

import java.io.IOException;
import java.net.*;

public class C_mutex extends Thread {
    //NODELIST
    C_nodeList lst;

    //TOKEN GRANTING SOCKET
    Socket s;

    //PORT OF MUTEX
    int port;

    //LIGHT TRACKER
    int light = 0;
    boolean node = false;

    //IP AND PORT OF REQUESTING NODE
    String n_host;
    int n_port;

    public C_mutex(C_nodeList l, int p) {
        lst = l;
        port = p;
    }


    public void go() {
        try {
            //CREATE SERVER SOCKET THAT WILL RECEIVE RETURNED TOKEN
            ServerSocket CoordinatorSocket = new ServerSocket(port);

            while (true) {
                //PRINT BUFFER CONTENT FOR DEBUGGING PURPOSES
//    			lst.show();
                node = !node;
                light = 0;
                if (node){
                    light = 2;
                }
                if (lst.size() == 4) { //IF TWO LIGHTS ARE REGISTERED

                    //GET NODE IP AND PORT FROM BUFFER
                    n_host = (String) lst.get(light);
                    n_port = Integer.parseInt((String) lst.get(light+1));

                    //GRANT TOKEN TO NODE
                    try {
                        s = new Socket(n_host, n_port);
                        System.out.println("Granted token to node " + n_port);
                    } catch (java.io.IOException e) {
                        System.err.println("CRASH: Mutex connecting to the node for granting the TOKEN" + e);
                    }

                    //RECEIVE TOKEN FROM NODE
                    try {
                        CoordinatorSocket.accept();
                    } catch (IOException e) {
                        System.err.println("CRASH: Mutex waiting for the TOKEN back" + e);
                    }
                }//ENDIF
            }//ENDWHILE
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public void run() {
        go();
    }
}
