package ijordan.matrixonator;

import java.net.Socket;

/**
 * Client searcher thread to accept new clients for the Visualizer
 * 
 * @author Ewan
 *
 */
public class VisualizerClientSniffer implements Runnable {

	@Override
	public void run() {
		try {
			// Threads the blocking accept method until a client is found,
			// passes it to the visualizer then
			// the thread.join will be called an no more clients will be
			// accepted
			Socket newClient = Visualizer.getServer().accept();
			Visualizer.addClient(newClient);
		} catch (Exception e) {
			System.out.println("Error accepting client...");
			e.printStackTrace();
		}

	}

}
