
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
            /*Server has to read from the socket the ID of the user*/
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            /*Must also print Welcome with the ID */
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            List<String> request = new ArrayList<>();
            String line;


            /*Create the response*/
            while ((line = reader.readLine()) != null) {
            System.out.println(line);
              request.add(line);
            }
            /*Extract the ID from the request*/
             
            String userId ="";
            for(String l:request){
                if (l.startsWith("ID"))
                     userId = l.replaceAll("\\D+","");
            }
            System.out.println("User ID :"+userId);
            StringBuilder response = new StringBuilder();
            response.append("Welcome user  "+ userId );
            Random rand = new Random();
            int payload_size = rand.nextInt(2048000) + 307200; /*Between 300 - 2000 KBs*/
            byte[] payload = new byte[payload_size];
            new Random().nextBytes(payload); /*Fill it it with random bytes*/

            writer.println(response.toString());
            output.write(payload);
            socket.close();
            
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }
}