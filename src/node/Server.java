package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import node.ThreadServer;


public class Server implements Runnable
{
    
    private HashMap fxTable;
    
    private ServerSocket serverSocket;
    private Socket socket;
    // streams for communication to client
    
    private InputStream is;
    private OutputStream os;
    
    private PrintWriter toClient;
    private BufferedReader fromClient;
    
    // use a high numbered non-dedicated port
    private static final int PORT_NUMBER = 3000;
    
    private static final String MESSAGE_TO_CLIENT = "Hello client. This is the server.";
    private static final String CLIENT_QUITTING = "End"; //sent by client
    private static final String USER_NOT_FOUND = "Cannot find"; // sent by server
    private static final String USERS_NUM = "usersnum"; // from client
    private static final String SHOW = "show"; // from client
    private static final String QUOTE = "quote"; // from client
    private static final String HELPREQUEST = "help";
    private static final String HELP = "You can choose from the following options: \n\n Either ASK for a quote for GBPUSD or other FX rates \n \n OR Click to see the number of current users connected \n \n OR Click QUIT to end the session" ; 
    private static final String DM = "DMDMDM";
    
    
    // constructor
    public Server(Socket s)
    {
        socket = s;
        
        fxTable = this.setUpTable();
        
        System.out.println("...Server starting up");
    }
    
    

     public void run ()
     {
          try {
                    
                openStreams();
                
                processRequests();
                
                closeStreams();
  
                socket.close( ); 
                

            }
            catch (Exception e) 
            {
            System.out.println("Trouble with a connection " + e); 
            }
          ThreadServer.decTcount(); // update the num of thread
          
       }
    
        

    
    
    private void processRequests() throws IOException
    {
        //System.out.println("Server is executing its hello method");
        //toClient.println(MESSAGE_TO_CLIENT);
        //System.out.println("done talking to client in hello method");
        
        
        String request;
        String reply;
        String replyUsers;
        
        request = fromClient.readLine();
        System.out.println("...request is " + request);
        
        while (!(request.equals(CLIENT_QUITTING)))
        {
            
           
            if (request.equals(USERS_NUM))
           
            {
                System.out.println("...replying num of threads");
                replyUsers = "Num of users = " + ThreadServer.getTnumber();
                toClient.println(replyUsers);
            }
            
            else if (request.equals(HELPREQUEST))
                {
                   System.out.println("...replying for help with \n" + HELP + "\n" + DM);
                   reply = HELP + "\n" + DM;
                   toClient.println(reply); 
                 }
  
            else 
            {
                System.out.println("...replying for quote");
                reply = (String) fxTable.get(request);
                    if (reply == null)
                    {
                    reply = "typo or not here\n";
                    }
                    toClient.println(reply);
            }
           
           request = fromClient.readLine();
        }
        
        
   } 
        
    
    
 
    // set up streams for communicating with the client
    private void openStreams() throws IOException
    {
        final boolean AUTO_FLUSH = true;
        is = socket.getInputStream();
        fromClient = new BufferedReader(new InputStreamReader(is));
        os = socket.getOutputStream();
        toClient = new PrintWriter(os, AUTO_FLUSH);
        System.out.println("...Streams set up");
    }

    // close output streams to client
    private void closeStreams() throws IOException
    {
        toClient.close();
        os.close();
        fromClient.close( ); 
        is.close( );
        System.out.println("...Streams closed down");
    }
    
    
    
    // set up name database and add sample data
    private HashMap <String, String> setUpTable ()
    {
    HashMap <String, String> fTable = new HashMap <String, String>();
        
        fTable.put("GBPUSD", "1.55"); 
        fTable.put("GBPEUR", "1.43"); 
        fTable.put("GBPHKK", "120.00"); 

    return fTable; 
    }

    
    
} // end class

