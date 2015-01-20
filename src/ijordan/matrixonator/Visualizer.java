package ijordan.matrixonator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Visualizer.java
 * ---------------
 * Provides a interface into Matrixonator to support logging / Step-by-Step visualization of
 * the Matrixonator application
 * @author Ewan
 */
public class Visualizer {
	
	private static ServerSocket vSocket;		//Interface Socket
	private static Socket client;				//Client to send data to
	private static PrintWriter vOut;  			//Writer to Socket
	
	private static final int DEFAULT_PORT = 9876;	//Default port number
	
	
	/**
	 * Starts up the visualizing server
	 * @param port (Use -1 for default)
	 * @return True on success, false otherwise
	 */
	public static boolean start(int port)
	{
		//Checking for default port
		if (port == -1) { port = DEFAULT_PORT; }
		
		//Setup the connection, wait for the client and setup a output stream to client
		try
		{
			vSocket = new ServerSocket(port);
			client = vSocket.accept();
			vOut = new PrintWriter(client.getOutputStream(), true);
			return true;
		}
		
		//Catch all various exceptions
		catch (UnknownHostException e)
		{
			System.out.println("Error starting visualizer (HOST)...\n");
			e.printStackTrace();
			return false;
		}
		catch (IOException e)
		{
			System.out.println("Error starting visualizer (SOCKET)...\n");
			e.printStackTrace();
			return false;	
		}
		catch (Exception e)
		{
			System.out.println("Error starting visualizer (UNKN)...\n");
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * Send a message over the Visualizer
	 * @param data
	 * @return True on success
	 */
	public static boolean send(String data)
	{
		try
		{ vOut.print(data); vOut.flush(); return true; }
		catch (Exception e) 
		{ e.printStackTrace(); return false; }
	}
	
	/**
	 * Closes connections to any clients
	 */
	public static void stop()
	{
		vOut.print(">CLOSING...");
		vOut.flush();
		try {
			vSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
