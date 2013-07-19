package node;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class Client 
{
    //Streams used for communicating with server
    private InputStream is;
    private OutputStream os;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Socket socket;    // Socket to server
    private static final int SERVER_PORT_NUMBER = 3000;
    private static final String DM = "DMDMDM";
    

    public void run()
    {
        

        connectToServer();

    }

    private void connectToServer()
    {
        try
        {
            
            final InetAddress SERVER_ADDRESS = InetAddress.getLocalHost();
            System.out.println("Attempting to contact " + SERVER_ADDRESS);

            socket = new Socket(SERVER_ADDRESS, SERVER_PORT_NUMBER);
            openStreams();
        }
        catch (IOException e)
        {
            String ls = System.getProperty("line.separator");
            System.out.println(ls + "Trouble contacting the server: " + e);

        }
    }

 
    private void openStreams() throws IOException
    {
        final boolean AUTO_FLUSH = true;
        is = socket.getInputStream();
        fromServer = new BufferedReader(new InputStreamReader(is));
        os = socket.getOutputStream();
        toServer = new PrintWriter(os, AUTO_FLUSH);
    }


    private void closeStreams() throws IOException
    {
        fromServer.close();
        is.close();
        toServer.close(); 
        os.close(); 
    }

   
 
    public void request(String aString) throws IOException
    {
        
        String reply = new String();
        
        toServer.println(aString);
        
        reply = fromServer.readLine();
        
        System.out.println("reply ->> " + reply);
        
       
     
    
     }
       

}

