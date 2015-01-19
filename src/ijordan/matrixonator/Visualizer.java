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
	
	
	//Starts up visualizing socket
	public static boolean start(int port)
	{
		if (port == -1) { port = DEFAULT_PORT; }
		
		try
		{
			vSocket = new ServerSocket(port);
			client = vSocket.accept();
			vOut = new PrintWriter(client.getOutputStream(), true);
			return true;
		}
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
	
	
	//Sends message out to User
	public static boolean send(String data)
	{
		try
		{ vOut.print(data); vOut.flush(); return true; }
		catch (Exception e) 
		{ e.printStackTrace(); return false; }
	}
	
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
