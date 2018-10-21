
import java.io.*;
import java.net.*;
import java.util.*;

/*
*   Client Thread is one user.It consists of an ID 
*/
public class ClientThread extends Thread { 
    private int id;
    private String ipAddress;
    private int port;
    private String hostname;

    public int getIdUser(){
        return this.id;
    }
	private Socket socket;
 
    public ClientThread(Socket socket,int id,String ipAddress,int port ) {
        this.socket = socket;
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public void run()   {
        try {
            /* Client must create request which is consisted by the following: 
			* Hello string
			* Client IP
			* Port
			* ID (this)
             */
            int num_requests=0;
            System.out.println("New user ID: "+getIdUser());
			
           // Socket socket = new Socket(hostname, port);

			while (num_requests<1){
			    BufferedOutputStream output = new BufferedOutputStream( socket.getOutputStream(), 5120000);
	            PrintWriter writer = new PrintWriter(output, true);
				/*Trying to create the request in which the client will send*/ 
	            StringBuilder request = new StringBuilder();	
	            request.append("HELLO");
	            request.append("\n"+this.ipAddress+" "+this.port);
				//request.append("\nrequest: "+ num_requests);/*This is for testing purposes*/
	            request.append("\nID " + getIdUser());

	            if(socket.isConnected())
	            	writer.println(request);

	            InputStream input =  new BufferedInputStream(socket.getInputStream(),5120000);
	           	Scanner scanner = new Scanner(input);

	           /*Wait until input is entered from the server*/
	           // while (input.available()<10000){  		
	            //	 input = socket.getInputStream();
	            //}	
	            //System.out.println("hi");
	            scanner = new Scanner(input);
	            
	            //BufferedReader BufferedReaderer = new BufferedReader(new InputStreamReader(input));
	            /*Extract the response from the server */
	          	String  welcome_msg="";
	          	welcome_msg = scanner.nextLine();
	            System.out.println(welcome_msg);          
	            ++num_requests; 
        	}      
	            socket.close();
     		
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
