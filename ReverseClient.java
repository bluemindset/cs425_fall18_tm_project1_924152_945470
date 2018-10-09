
import java.net.*;
import java.util.Scanner;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */

/*
*   User class consists of an ID 
*/
public class User{ 
    private int id;

    public setId(int id){
        this.id = id;
    }
    public getId(){
        return this.id;
    }
}


public class ReverseClient {
 
    public static void main(String[] args) {
        if (args.length < 3) return;
    

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int N_users = Integer.parseInt(args[2]);
        int nums_Request = Integer.parseInt(args[3]);
        int current_users = 0 ;

        try (Socket socket = new Socket(hostname, port)) {
            while(current_users != N_users ){
                User user = new User();
                
                user.setId(current_users);

                while (nums_Request>0){
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);
     
                    String helloStr = new String("HELLO");/*This is the hello sting*/
            
                    writer.println(helloStr);
                }
                current_users++;
                }
            }
            socket.close(); 
            catch (UnknownHostException ex) {
 
                System.out.println("Server not found: " + ex.getMessage());
 
            } catch (IOException ex) {
    
                System.out.println("I/O error: " + ex.getMessage());
            }
   }
}
