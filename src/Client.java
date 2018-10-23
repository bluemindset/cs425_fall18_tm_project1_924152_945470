
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */



public class Client {
 
    public static void main(String[] args) throws UnknownHostException,IOException  {

        if (args.length < 4) return;
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int N_users = Integer.parseInt(args[2]);
        int current_users = 0 ;
        int reps = Integer.parseInt(args[3]);
        InetAddress ip = InetAddress.getLocalHost() ;
        String ipaddress = ip.getHostAddress();

        /*While repetetions are not 0 run the program*/
        while(reps>0){
            while (current_users < N_users ) {
                /*Open a new Socket*/
                Socket clientS = new Socket(hostname, port);
                    /*Open a new client thread*/     
                    new ClientThread(clientS,++current_users,ipaddress,port).start(); //new user
            }
            reps--;
        }
    }
}
