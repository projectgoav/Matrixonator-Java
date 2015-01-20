package ijordan.matrixonator;

import java.net.Socket;

public class VisualizerClientSniffer implements Runnable {

	@Override
	public void run() {
		try {
			Socket newClient = Visualizer.getServer().accept();
			Visualizer.addClient(newClient);
		} catch (Exception e) {
			System.out.println("Error accepting client...");
			e.printStackTrace();
		}

	}

}
