package clientOS;


import java.io.*;
import java.net.*;

import objectTypes.Room;
public class ClientOS {
	
	
	
	private int port;
	private String serverAddress;
	private Socket socket = null;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ClientOS() {
		this.port = 6789;
		this.serverAddress = "127.0.0.1";
	}

	public ClientOS(int port, String serverAdress){
		this.port = port;
		this.serverAddress = serverAdress;

	}
	
	public Object sendObjectAndGetResponse(Object obj) {
		while(true) {
			try {
				socket = new Socket(serverAddress, port);
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				
				oos.writeObject(obj);
				Object tmpObject = ois.readObject();
				return tmpObject;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
