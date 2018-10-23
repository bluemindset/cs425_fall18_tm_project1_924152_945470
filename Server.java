
import java.io.*;
import java.net.*;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 */
public class Server {

    private static long beforeM;
    
    public static double getProcessCpuLoad() throws Exception {
        MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
        ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

        if (list.isEmpty())     return Double.NaN;

        Attribute att = (Attribute)list.get(0);
        Double value  = (Double)att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0)      return Double.NaN;
        // returns a percentage value with 1 decimal point precision
        return ((int)(value * 1000) / 10.0);
    }

public static double getMemoryUsageUtilization() {              
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeM;
        double persantage = (100.0 * actualMemUsed)/(Runtime.getRuntime().totalMemory()*1.0);
        return ((int)(persantage * 10) / 10.0);
    }
 public int  completed_requests=0;
    public static void main(String[] args) {
        if (args.length < 1) return;
        beforeM=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        int port = Integer.parseInt(args[0]);
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {  
 
            System.out.println("Server is listening on port " + port);
          
            while (true) {
            Socket socket = serverSocket.accept();
               
                System.out.println("Server thread created");
                new ServerThread(socket).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}