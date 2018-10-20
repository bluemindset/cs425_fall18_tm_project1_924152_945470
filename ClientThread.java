
import java.io.*;
import java.net.*;
import java.util.*;

/*
*   Client Thread is one user.It consists of an ID 
*/
public class ClientThread extends Thread{ 
    private int id;
    private String ipAddress;
    private int port;

    public void setIdUser(String id){
        this.id = id;
    }

    public String getIdUser(){
        return this.id;
    }
	private Socket socket;
 
    public ClientThread(Socket socket,int id,String ipAddress,int port ) {
        this.socket = socket;
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public void run() {
        try {
            /* Client must create request which is consisted by the following: 
			* Hello string
			* Client IP
			* Port
			* ID (this)
             */
            int num_requests=0;

		//	while (num_requests<300){

           // }
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
			
            String line = writer. 
            
            

            while ((line = reader.readLine()) != null) {
              System.out.println(line);
              // Don't need counterOfReadLines, just use playerNames.size().
              request.add(line);
            }
       
 
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





}
