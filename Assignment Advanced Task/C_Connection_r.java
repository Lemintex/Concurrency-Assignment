//2715375

import java.net.*;
import java.io.*;


//REACTS TO A NODE REGISTRATION
//STORES NODE INFO IN NODELIST

public class C_Connection_r extends Thread {

    //CLASS VARIABLES
    C_nodeList lst;

    Socket s;
    InputStreamReader in;
    BufferedReader bin;


    public C_Connection_r(Socket s, C_nodeList l) {
        this.s = s;
        this.lst = l;

    }


    public void run() {

        final int NODE = 0;
        final int PORT = 1;

        String[] request = new String[2];
        System.out.println("C_connection_r dealing with request from socket " + s);
        try {
            //READ REQUEST (IP AND PORT) FROM NODE REQUEST
            in = new InputStreamReader(s.getInputStream());
            bin = new BufferedReader(in);

            //SAVE IP AND PORT IN BUFFER OBJECT
            request[NODE] = bin.readLine();
            request[PORT] = bin.readLine();
            this.lst.saveRequest(request);

            //CLOSE SOCKET
            s.close();
            System.out.println("C_connection_r received and recorded request from " + request[NODE] + ":" + request[PORT] + "[SOCKET CLOSED]");
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

    }//END OF RUN

}//END OF CLASS
