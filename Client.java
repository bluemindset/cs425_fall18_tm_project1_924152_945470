
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */



public class Client {
 
    public static void main(String[] args) throws UnknownHostException  {
        if (args.length < 3) return;
    

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int N_users = Integer.parseInt(args[2]);
        int current_users = 0 ;


        System.out.println(InetAddress.getLocalHost().getHostAddress());


        try (Socket socket = new Socket(hostname, port)) {
            
            socket.close(); 
            }catch (UnknownHostException ex) {
 
                System.out.println("Server not found: " + ex.getMessage());
 
            } catch (IOException ex) {
    
                System.out.println("I/O error: " + ex.getMessage());
            }
   }
}
