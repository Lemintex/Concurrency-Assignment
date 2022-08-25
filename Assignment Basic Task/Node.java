//2715375

import java.net.*;
import java.io.*;

public class Node {
    //TEXT COLOURS
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    //TOKEN REQUEST SOCKET
    private Socket nodeSocket;

    //SOCKET PRINTWRITER
    private PrintWriter pr = null;

    //TOKEN SOCKETS
    private ServerSocket nodeServerSocket;
    private Socket nodeTokenReturn;

    //COORDINATOR INFO
    String c_host = "127.0.0.1";
    int c_request_port = 7000;
    int c_return_port = 7001;

    //NODE HOST ADDRESS
    String n_host = "127.0.0.1";

    public Node(String nam, int por, int sec) {
        int sleep = sec;
        String n_host_name = nam;
        int n_port = por;

        //SLEEP VARIABLES
        int max = sleep * 2, min = sleep / 2;
        long time = 0;
        System.out.println("Node " + n_host_name + ":" + n_port + " of DME is active...");

        //OPEN A SERVER SOCKET USED FOR RECEIVING THE TOKEN
        try {
            nodeServerSocket = new ServerSocket(n_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                //RANDOM TIME LIGHT WILL BE GREEN
                time = (long) (Math.random() * (max - min + 1) + min);

                //REQUESTS TOKEN FROM COORDINATOR
                nodeSocket = new Socket(c_host, c_request_port);

                //SENDS NODE INFORMATION WITH REQUEST
                pr = new PrintWriter(nodeSocket.getOutputStream());
                pr.println(n_host);
                pr.println(n_port);
                pr.flush();
                nodeSocket.close();
                System.out.println("Node " + n_host + " requesting token on port " + c_request_port);

                //ONCE THE TOKEN IS BEING SENT FROM COORDINATOR, ACCEPT
                nodeServerSocket.accept();
                System.out.println("Node " + n_host + " received token on port " + c_request_port);

                //TURNS LIGHT GREEN, THEN RED
                System.out.println(ANSI_GREEN + "TRAFFIC LIGHT GREEN" + ANSI_RESET);
                Thread.sleep(time);
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
        if ((args.length <= 1) || (args.length > 2)) {
            System.out.print("Usage: Node [port number] [millisecs]");
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
        //GET NODE FROM ARGS
        n_port = Integer.parseInt(args[0]);
        System.out.println("node port is " + n_port);

        //CREATE NODE
        Node n = new Node(n_host_name, n_port, Integer.parseInt(args[1]));
    }
}

