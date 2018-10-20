
import java.io.*;
import java.net.*;
 
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

            while ((line = reader.readLine()) != null) {
              System.out.println(line);
              // Don't need counterOfReadLines, just use playerNames.size().
              request.add(line);
            }
            /*Extract the ID from the request*/
            String userId;
            for(String line:request){
                if (line.indexOf('I')&&line.indexOf('D'))
                     userId = line.replaceAll("\\D+","");
            }
                System.out.println(userId);
         
         //       writer.println("Server: " + reverseText);
 
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}