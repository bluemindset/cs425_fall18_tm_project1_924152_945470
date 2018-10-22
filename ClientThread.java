
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
    static long overall_latency=0;
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

	            /*Start by calculating the time the request is being sent */
	        	long startTime = System.nanoTime();
	            
	            if(socket.isConnected())
	            	writer.println(request);

	            BufferedInputStream input =  new BufferedInputStream(socket.getInputStream(),5120000);
	           	Scanner scanner = new Scanner(input);

	            
	            //BufferedReader BufferedReaderer = new BufferedReader(new InputStreamReader(input));
	            /*Extract the response from the server */
	          	String  welcome_msg="";
	          	String  contect_msg="";
	          	if (scanner.hasNext())
	          	welcome_msg = scanner.nextLine();
	          	if (scanner.hasNext())
	          	contect_msg= scanner.nextLine();
	            System.out.println(welcome_msg);
	            System.out.println(contect_msg);
				
                String offset_ = contect_msg.replaceAll("\\D+","");
                	int offset=0;
                if (!offset_.equals("")){
                	 offset = Integer.parseInt(offset_);
					char c = scanner.next().charAt(offset-1);
	          	}
	          	
 				/*End time is when the response is received*/ 
     			long endTime = System.nanoTime();
     			long duration = (endTime - startTime);
     			overall_latency += duration;
	            ++RTT;
	            ++num_requests; 
        	}      	
	            writer.println("end");
	           // socket.close();
	        user++ ;
           // String path = new String("latency"+ user + ".txt");
            String path = new String("latency.txt");
            
            try{
                PrintWriter logger = new PrintWriter(path, "UTF-8");
                logger.println("Latency (nanos):"+overall_latency+" - RTTs  "+RTT); 
               	logger.close();
             
            } catch (IOException ex) {
                System.out.println("File exception: " + ex.getMessage());
                ex.printStackTrace();
            }
     		System.out.println("Latency is "+overall_latency+ " RTTs are: "+RTT);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
