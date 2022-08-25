//2715375

import java.net.*;
import java.io.*;

public class Node {
    //TEXT COLOURS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    //NODE REGISTER
    private Socket nodeSocket;

    //REGISTER PRINT WRITER
    private PrintWriter pr = null;

    //TOKEN SOCKETS
    private ServerSocket nodeServerSocket;
    private Socket nodeTokenReturn;

    //COORDINATOR INFO
    String c_host = "127.0.0.1";
    int c_request_port = 7000;
    int c_return_port = 7001;

    //HOST ADDRESS
    String n_host = "127.0.0.1";

    public Node(String nam, int por) {
        String n_host_name = nam;
        int n_port = por;
        System.out.println("Node " + n_host_name + ":" + n_port + " of DME is active ....");


        try {
            //REGISTERS NODE TO COORDINATOR
            nodeSocket = new Socket(c_host, c_request_port);

            //SENDS NODE INFORMATION WITH REGISTRATION
            pr = new PrintWriter(nodeSocket.getOutputStream());
            pr.println(n_host);
            pr.println(n_port);
            pr.flush();
            nodeSocket.close();

            //OPEN A SERVER SOCKET USED FOR RECEIVING THE TOKEN
            nodeServerSocket = new ServerSocket(n_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                //ONCE THE TOKEN IS BEING SENT FROM COORDINATOR, ACCEPT
                nodeServerSocket.accept();
                System.out.println("Node " + n_host + " received token on port " + c_request_port);

                //TURNS LIGHT GREEN, THEN RED
                System.out.println(ANSI_GREEN + "TRAFFIC LIGHT GREEN" + ANSI_RESET);
                Thread.sleep(5000);
                System.out.println(ANSI_RED + "TRAFFIC LIGHT RED" + ANSI_RESET);

                //RETURNS TOKEN NOW NODE LIGHT IS RED
                nodeTokenReturn = new Socket(c_host, c_return_port);
                System.out.println("Node " + n_host + " returning token on port " + c_return_port);
            } catch (IOException | InterruptedException e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }


    public static void main(String args[]) {

        String n_host_name = "";
        int n_port;

        // port and millisec (average waiting time) are specific of each node
        if ((args.length < 1) || (args.length >= 2)) {
            System.out.print("Usage: Node [port number]");
            System.exit(1);
        }

        //GET IP AND PORT NUMBER OF BODE
        try {
            InetAddress n_inet_address = InetAddress.getLocalHost();
            n_host_name = n_inet_address.getHostName();
            System.out.println("node hostname is " + n_host_name + ":" + n_inet_address);
        } catch (UnknownHostException e) {
            System.out.println(e);
            System.exit(1);
        }

        n_port = Integer.parseInt(args[0]);
        System.out.println("node port is " + n_port);

        Node n = new Node(n_host_name, n_port);
    }
}

