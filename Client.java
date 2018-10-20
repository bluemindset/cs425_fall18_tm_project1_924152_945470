
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

        InetAddress ip = InetAddress.getLocalHost() ;
        String ipaddress = ip.getHostAddress();

        try (Socket clientSocket = new Socket(hostname, port)) {
            int num_requests=0;
            ClientThread user = new ClientThread();
            
             while (current_users < N_users ) {
                Socket socket = clientSocket.accept();
                System.out.println("New user ID: "+current_users);
                new ClientThread(socket,++current_users,ip,port).start();
            }
           
            
            socket.close(); 
            }catch (UnknownHostException ex) {
 
                System.out.println("Server not found: " + ex.getMessage());
 
            } catch (IOException ex) {
    
                System.out.println("I/O error: " + ex.getMessage());
            }
   }
}
