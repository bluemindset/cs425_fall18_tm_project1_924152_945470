
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
        if (args.length < 3) return;
    

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int N_users = Integer.parseInt(args[2]);
        int current_users = 0 ;

        InetAddress ip = InetAddress.getLocalHost() ;
        String ipaddress = ip.getHostAddress();
   
        while (current_users < N_users ) {

            Socket clientS = new Socket(hostname, port);
                 try{
                     Thread.sleep(1000);
                    }
                       catch(InterruptedException ex)
                    {
                      Thread.currentThread().interrupt();
                    }

                    new ClientThread(clientS,++current_users,ipaddress,port).start(); //new user

            }
    }
}
