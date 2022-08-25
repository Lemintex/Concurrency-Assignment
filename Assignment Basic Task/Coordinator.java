//2715375

import java.net.*;

public class Coordinator {

    public static void main(String args[]) {
        //COORDINATOR SOCKET
        int port = 7000;

        //NEW COORDINATOR
        Coordinator c = new Coordinator();

        //COORDINATOR OBJECTS
        C_buffer b = new C_buffer();
        C_receiver crec = new C_receiver(b, port);
        C_mutex mtx = new C_mutex(b, port + 1);

        //RUN THE C_RECEIVER AND C_MUTEX OBJECT SHARING A C_BUFFER OBJECT
        crec.start();
        mtx.start();
        try {
            InetAddress c_addr = InetAddress.getLocalHost();
            String c_name = c_addr.getHostName();
            System.out.println("Coordinator address is " + c_addr);
            System.out.println("Coordinator host name is " + c_name + "\n\n");
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Error in coordinator");
        }

        //ALLOWS DEFINING PORT ON LAUNCH
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
    }
}