
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
	//private Socket socket;
 
    public ClientThread(String hostname,int id,String ipAddress,int port ) {
      //  this.socket = socket;
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
			

            Socket socket = new Socket(hostname, port);

			while (num_requests<10){

	            

	            OutputStream output = socket.getOutputStream();
	            PrintWriter writer = new PrintWriter(output, true);
				/*Trying to create the request in which the client will send*/ 
	            StringBuilder request = new StringBuilder();	
	            request.append("HELLO\n\n");
	            request.append("\n\n"+this.ipAddress+" "+this.port);
	            request.append("\n\nID " + getIdUser());
				//request.append("\nrequest: "+ num_requests);/*This is for testing purposes*/
	            if(socket.isConnected())
	            	writer.println(request);

	            InputStream input = socket.getInputStream();
	           	Scanner scanner = new Scanner(input);
	      
	            //writer.close();
	            while (input.available()==0){
	            		//System.out.println("hiww");
	            	 input = socket.getInputStream();
	            	 scanner = new Scanner(input);
	            }
	            //BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	            /*Extract the response from the server */
	          	String  welcome_msg="";
	           // if (input.available()>0)
	          		welcome_msg = scanner.next() ;
	            //System.out.println(welcome_msg);
	            System.out.println(welcome_msg);
	            
	            //while ((line = reader.readLine()) != null)
	            ++num_requests; 
        	}      
	            socket.close();
     		
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
