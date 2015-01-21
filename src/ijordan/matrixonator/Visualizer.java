package ijordan.matrixonator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Provides a interface into Matrixonator to support logging / Step-by-Step
 * visualization of the Matrixonator application
 * 
 * @author Ewan
 */
public class Visualizer {

	private static ServerSocket vSocket; // Interface Socket
	private static Socket client; // Client to send data to
	private static PrintWriter vOut; // Writer to Socket

	private static final int DEFAULT_PORT = 9876; // Default port number
	private static Thread clientSniffer = new Thread(
			new VisualizerClientSniffer());
	
	private static byte packetOrder = 0;
	
	private static final byte PACKET_END = 0xF;

	/**
	 * Starts up the visualizing server
	 * 
	 * @param port
	 *            (Use -1 for default)
	 * @return True on success, false otherwise
	 */
	public static boolean start(int port) {
		// Checking for default port
		if (port == -1) {
			port = DEFAULT_PORT;
		}

		// Setup the connection, wait for the client and setup a output stream
		// to client
		try {
			vSocket = new ServerSocket(port);
			clientSniffer.start();
			return true;
		}

		// Catch all various exceptions
		catch (UnknownHostException e) {
			System.out.println("Error starting visualizer (HOST)...\n");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error starting visualizer (SOCKET)...\n");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("Error starting visualizer (UNKN)...\n");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Send a message over the Visualizer
	 * 
	 * @param data
	 * @return True on success
	 */
	public static boolean send(String data) {
		try {
			if (vOut == null) {
				return false;
			}
			
			byte[] buffer = new byte[4];
			buffer[0] = packetOrder;
			
			if (packetOrder+ 1 > 3) { packetOrder =0;}
			else {packetOrder++;}
			
			buffer[1] = 0;
			buffer[2] = 4;
			buffer[3] = PACKET_END;
			
			vOut.print(buffer);
			vOut.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Closes connections to any clients
	 */
	public static void stop() {
		if (vOut == null) {
			return;
		}
		vOut.print(">CLOSING...");
		vOut.flush();
		try {
			vSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the client to the visualizer and closes sniffer thread
	 * 
	 * @param newclient
	 */
	protected static void addClient(Socket newclient) {
		try {
			if (client != null) {
				return;
			}
			client = newclient;
			vOut = new PrintWriter(client.getOutputStream(), true);
			clientSniffer.join();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the ServerSocket object for sniffer thread
	 * 
	 * @return Server object
	 */
	protected static ServerSocket getServer() {
		return vSocket;
	}

}
