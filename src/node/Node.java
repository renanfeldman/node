
package node;

import java.io.IOException;

/**
 *
 * @author renanfeldman
 */
public class Node {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try
        {
        
        StartServer();
        StartClient();
        }
        
        catch (Exception e) 
            {
            System.out.println("Something to fix " + e); 
            }
        
    }



   public static void StartClient() throws IOException
   {
      Client client1 = new Client();
      client1.run();

   }

  public static void StartServer() throws IOException
   {
      ThreadServer server1 = new ThreadServer();
      server1.run();
   } 
   
   
   
   
   
}
