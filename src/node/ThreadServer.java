
package node;

import node.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author renanfeldman
 */
public class ThreadServer 
{
    private Thread connection;
    static final int PORT_NUMBER = 3000;
    static private int tCount = 0;
    
    public ThreadServer() {
    }
    
    static public void decTcount ()
    {
        tCount--;
        System.out.println("Thread Count = " + tCount);
    }
    
    static public int getTnumber()
    {
            return tCount;
    }
    
    public void run () throws IOException
    {
        
        try 
        {
        ServerSocket ss = new ServerSocket(PORT_NUMBER);
        while (true)
        {
            Socket socket = ss.accept();
            connection = new Thread(new Server (socket));
            connection.start();
            tCount++;
            System.out.println("Thread Count = " + tCount);
        }
        }
        
        catch (Exception e)
        {
            System.out.println("Trouble with a connection " + e);
        }
        
    }
    

            
}
