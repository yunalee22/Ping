package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread {
	
	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;

	public Server(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sccVector = new Vector<ServerClientCommunicator>();
		this.start();
	}
	
	public Vector<ServerClientCommunicator> getSccVector() {
		return sccVector;
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		scc.interrupt();
		scc.closeSocket();
		sccVector.remove(scc);
	}
	
	public void run() {
		try {
			// Wait for connections
			while (true) {
				System.out.println("Waiting for connection...");
				Socket s = ss.accept();
				System.out.println("Connection from: " + s.getInetAddress());
				
				// Create a new server thread
				try {
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					sccVector.add(scc);
				} catch (IOException ioe) {
					System.out.println("IOE creating new SCC: " + ioe.getMessage());
				}
			}
		} catch (IOException ioe) {
			System.out.println("IOE in server thread: " + ioe.getMessage());
		} finally {
			if (ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOE closing server socket: " + ioe.getMessage());
				}
			}
		}
	}
	
	public void sendToAllClients (ServerClientCommunicator st, Response r) {
		for (ServerClientCommunicator st1 : sccVector) {
			if (st1 != st) {
				st1.sendResponse(r);
			}
		}
	}
	
	public static void main(String [] args) {
		new Server(6788);
	}
}
