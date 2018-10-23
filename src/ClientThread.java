
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
    static final int num_r= 10; /*These are the repetisions of each user*/
    static long overall_latency=0;/*This is the overall latency*/
    private Socket socket;
    static int RTT; /*Number of RTTs*/
    
    public int getIdUser(){
        return this.id;
    }
	public ClientThread(Socket socket,int id,String ipAddress,int port ) {
        this.socket = socket;
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
    }
     public static synchronized void increment_latency(long duration) {
        overall_latency += duration;
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
			/*Creating the output stream*/
			 BufferedOutputStream output = new BufferedOutputStream( socket.getOutputStream(), 5120);
	         PrintWriter writer = new PrintWriter(output, true);
				/*Trying to create the request in which the client will send*/ 
			 while (num_requests<num_r){	
	            /*Start by meseauring time*/
                /*Create the request*/
            	long startTime = System.nanoTime();
				StringBuilder request = new StringBuilder();	
	            request.append("HELLO");
	            request.append("\n"+this.ipAddress+" "+this.port);
	            request.append("\nID " + getIdUser());

	           /*Write the request if the connection holds*/  
	            if(socket.isConnected())
	            	writer.println(request);
                /*Creating the input stream*/
	            BufferedInputStream input =  new BufferedInputStream(socket.getInputStream(),5120);
	           	Scanner scanner = new Scanner(input);
                
                /*Extract the response from the server */
	          	String  welcome_msg="";
	          	String  contect_msg="";
	          	if (scanner.hasNext())
	          	welcome_msg = scanner.nextLine();
	          	if (scanner.hasNext())
	          	contect_msg= scanner.nextLine();
                /*Print the response so to prove that you have it */
	          	if (!welcome_msg.contains(".")&&!contect_msg.contains(".")){
	            	System.out.println(welcome_msg);
	            	System.out.println(contect_msg);
				}
                String offset_ = contect_msg.replaceAll("\\D+","");
                int offset=0;
                /*This is also a prove that the payload is caught up by our client*/
                if (!offset_.equals("")){
                	 offset = Integer.parseInt(offset_);
                	 char c = ' ';
                	 if (scanner.hasNext())
					 	c = scanner.next().charAt(offset-5);
					 if (c== '.')
					 	System.out.println("(Prove)payload is accepted");
	          	}
 				/*End time is when the response is received*/ 
     			long duration = System.nanoTime() - startTime;
     		     /*Add it to the general latency*/
                increment_latency(duration);
     			++RTT;
	            ++num_requests; 
        	}   
                /*Write a goodbye message*/   	
	            writer.println("end");
                /*Close the socket and the writer*/
	            socket.close();
	       		writer.close();
            try{            
                /*Put the results in a text file*/
                String path = new String("latency.txt");            
                PrintWriter logger = new PrintWriter(path, "UTF-8");
                logger.println("Latency (s):"+overall_latency/1000000000+" - RTTs  "+RTT); 
               	logger.close();             
            } catch (IOException ex) {
                System.out.println("File exception: " + ex.getMessage());
                ex.printStackTrace();
            }
     		System.out.println("Latency is "+(overall_latency/1000000000)+ " RTTs are: "+RTT);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
