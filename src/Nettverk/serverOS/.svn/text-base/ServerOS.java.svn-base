package serverOS;

import java.io.IOException;
import java.net.*;


public class ServerOS {
	private ServerSocket welcomeSocket;
	private int port;
	
	public ServerOS(int port) {
		this.port = port;
	}
	
	public void listen() {
		try {
			welcomeSocket = new ServerSocket(port);
			System.out.println("SERVER-OS INITIATED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			ClientManagerOS clientManagerOS;
			Socket clientSocket;
			try {
				
				clientSocket = welcomeSocket.accept();
				System.out.println("request from: " + clientSocket.getInetAddress().toString());
				clientManagerOS = new ClientManagerOS(clientSocket);
				Thread t = new Thread(clientManagerOS);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	protected void finalize(){
		     try{
		        welcomeSocket.close();
		    } catch (IOException e) {
		        System.out.println("Could not close socket");
		        System.exit(-1);
		    }
		  }
}
