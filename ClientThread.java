
import java.io.*;
import java.net.*;
import java.util.*;

/*
*   Client Thread is one user.It consists of an ID 
*/
public class ClientThread extends Thread{ 
    private String id;

    public void setIdUser(String id){
        this.id = id;
    }

    public String getIdUser(){
        return this.id;
    }
	private Socket socket;
 
    public ClientThread(Socket socket,String id) {
        this.socket = socket;
    }
    public void run() {
        try {
            /* Client must create request which is consisted by the following: 
			* Hello string
			* Client IP
			* Port
			* ID (this)
             */
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            /*Must also print Welcome with the ID */
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            List<String> request = new ArrayList<>();
            String line;

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
