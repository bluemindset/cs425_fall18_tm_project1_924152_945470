
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Random;
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    static int completed_requests;
    static final int SEC = 1000000000;
    static int user = 0;
    static long overall_duration=0;
    static long sec =0;
    static ArrayList<String> dataoutput = new ArrayList<String>();
    private Socket socket;
    //private PrintWriter logger;
    public ServerThread(Socket socket) {
        this.socket = socket;
       // this.logger = logger;
    }
 
    public void run() {
                
        try {
                
                InputStream input =  new BufferedInputStream(socket.getInputStream(),5120);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = new BufferedOutputStream( socket.getOutputStream(), 5120);
                PrintWriter writer = new PrintWriter(output, true);
                String end = new String();
                end = reader.readLine();
               while(!end.equals("")&&!end.equals("end")){
                long startTime = System.nanoTime();               

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
                Random rand = new Random(); 
                int payload_size = rand.nextInt(1) + 5; /*Between 300(307200 bytes) - 2000 (2048000 bytes)KBs*/
                payload_size*= 1;
                    char[] payload = new  char[payload_size];        /*Create the payload of that size mesaured above*/
                    for(int i=0;i < payload_size-1 ;i++){
                            payload[i]='.';
                                   /*Fill it it with  periods */
                        }
                    String response = new String("Welcome User: "+userId+"\nContent-Lenght: "+payload_size); 
                    writer.println(response);            /*Write the response the output stream*/
                    writer.println(payload);
                    writer.println("\nend");
                    if (socket.isConnected()) 
                    end =reader.readLine();
                
                    completed_requests++;
                 double proc=0.0;
                 double mem=0.0;
                    try{
                         mem = Server.getMemoryUsageUtilization();
                         proc= Server.getProcessCpuLoad();
                        
                    }catch(Exception ex1){
                        ex1.printStackTrace();
                        }
                    long endTime = System.nanoTime();

                    long duration = (endTime - startTime);
                    overall_duration += duration;
                    if (overall_duration >= SEC ){
                            sec++;
                            String logdata = new String("\nNo requests: "+completed_requests+" Seconds: "+sec+" CPU Load: "+proc+ " Memory Utilization: "+ mem );
                            dataoutput.add(logdata);
                            overall_duration=0;                            
                        }
                    }

            socket.close();
            writer.close();
            reader.close();
                } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
                }
                
            
            //System.out.println(dataoutput);
            String path = new String("throughput.txt");
            try{
                PrintWriter logger = new PrintWriter(path, "UTF-8");
                logger.println(dataoutput); 
                logger.close();
             
            } catch (IOException ex) {
                System.out.println("File exception: " + ex.getMessage());
                ex.printStackTrace();
            }
            
               
    }       
}