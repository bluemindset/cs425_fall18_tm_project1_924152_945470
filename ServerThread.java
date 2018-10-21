
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
               while(true){
                InputStream input =  new BufferedInputStream(socket.getInputStream(),5120000);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = new BufferedOutputStream( socket.getOutputStream(), 5120000);
               
                PrintWriter writer = new PrintWriter(output, true);
                /*Server has to read from the socket the ID of the user*/
                /*Must also print Welcome with the ID */
                /*This array of string is for getting the request*/
                ArrayList<String> request = new ArrayList<String>();
                String line = new String();
                /*Create the response*/
                    do{
                        line = reader.readLine();
                            if (line!=null){
                                String linetoenter = new String(line);
                                request.add(linetoenter);
                                System.out.println(line);
                            }
                     }while ((line!=null)&&!(line.startsWith("ID")));
                String userId ="";
                /*Extract the ID from the request*/
                if(line!=null)
                   userId = line.replaceAll("\\D+","");
                /*Create the response which is the welcome message & payload*/
                String response = new String("Welcome User: "+userId); 
                writer.println(response);            /*Write the response the output stream*/
                Random rand = new Random(); 
                int payload_size = rand.nextInt(2000) + 300; /*Between 300 - 2000 KBs*/
                for (int i = 0 ; i < payload_size;i++){ 
                    byte[] payload = new  byte[1024];        /*Create the payload of that size mesaured above*/
                    new Random().nextBytes(payload);                /*Fill it it with random bytes*/
                   output.write(payload);
                   // output.flush();
                }
                //output.write(payload);

            }
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }   
}