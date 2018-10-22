
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
    static int user=0;
    public int getIdUser(){
        return this.id;
    }
	private Socket socket;
 	static int RTT;
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
			long startTime = System.nanoTime();
            int num_requests=0;
            System.out.println("New user ID: "+getIdUser());
			
           // Socket socket = new Socket(hostname, port);

			    BufferedOutputStream output = new BufferedOutputStream( socket.getOutputStream(), 5120000);
	            PrintWriter writer = new PrintWriter(output, true);
				/*Trying to create the request in which the client will send*/ 
			while (num_requests<30){	
	            StringBuilder request = new StringBuilder();	
	            request.append("HELLO");
	            request.append("\n"+this.ipAddress+" "+this.port);
				//request.append("\nrequest: "+ num_requests);/*This is for testing purposes*/
	            request.append("\nID " + getIdUser());

	            if(socket.isConnected())
	            	writer.println(request);

	            BufferedInputStream input =  new BufferedInputStream(socket.getInputStream(),5120000);
	           	Scanner scanner = new Scanner(input);

	            
	            //BufferedReader BufferedReaderer = new BufferedReader(new InputStreamReader(input));
	            /*Extract the response from the server */
	          	String  welcome_msg="";
	          	String  contect_msg="";
	          	welcome_msg = scanner.nextLine();
	          	contect_msg= scanner.nextLine();
	            System.out.println(welcome_msg);
	            System.out.println(contect_msg);
				
                String offset_ = contect_msg.replaceAll("\\D+","");
                int offset = Integer.parseInt(offset_);
				char c = scanner.next().charAt(offset-1);
	          	//System.out.print(c);
	            ++RTT;
	            ++num_requests; 
        	}      	
	            writer.println("end");
	            socket.close();
     		long endTime = System.nanoTime();
     		long duration = (endTime - startTime);
			user++ ;
            String path = new String("latency"+ user + ".txt");
            try{
                PrintWriter logger = new PrintWriter(path, "UTF-8");
                logger.println("\nCLatency (nanos):"+duration+" - RTTs  "+RTT); 
               	logger.close();
             
            } catch (IOException ex) {
                System.out.println("File exception: " + ex.getMessage());
                ex.printStackTrace();
            }
     		System.out.println("Latency is "+duration+ " RTTs are: "+RTT);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
