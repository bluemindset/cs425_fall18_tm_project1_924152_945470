
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Random;
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
           
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = this.socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            /*Server has to read from the socket the ID of the user*/
             //writer.println("hi");
            /*Must also print Welcome with the ID */
           

            /*This array of string is for getting the request*/
            ArrayList<String> request = new ArrayList<String>();
            String line = new String();
            /*Create the response*/
            do{
                line = reader.readLine();
                 String linetoenter = new String(line);
                 System.out.print(line);
                 request.add(linetoenter);
             
            }
            while (!(line.startsWith("ID")));
          

            /*Extract the ID from the request*/
            String userId ="";
            for(String l:request){
                if (l.startsWith("ID"))
                     userId = l.replaceAll("\\D+","");
            }

           
            /*Print testing if the user id the server got is correct*/
//            System.out.println("User ID :"+userId);


            
                /*Create the response which is the welcome message & payload*/
                StringBuilder response = new StringBuilder();
                response.append("Welcome user  "+userId); 
                writer.println(response);            /*Write the response the output stream*/


            //writer.close();
           //      output.close();
                Random rand = new Random(); 
                int payload_size = rand.nextInt(2048000) + 307200; /*Between 300 - 2000 KBs*/
                byte[] payload = new byte[payload_size];        /*Create the payload of that size mesaured above*/
                new Random().nextBytes(payload);                /*Fill it it with random bytes*/
               
                //if(socket.isConnected())
             
            //output.write(payload);
            
            socket.close();
                
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }
}