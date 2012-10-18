package serverOS;

import java.io.*;
import java.net.*;


import connection.DbConnection;

public class ClientManagerOS implements Runnable{
	private Socket client;
	private DbConnection dbConnection;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ObjectManager objectManager;

	public ClientManagerOS(Socket client) {
		dbConnection = new DbConnection("hakonfam.dyndns.org", "Fellesprosjekt", "gruppe6", "entotre");
		this.client = client; //Send welcomeSocket.accept() som argument
		objectManager = new ObjectManager(dbConnection);
	}

	@Override
	public void run() {
			try {
				ois = new ObjectInputStream(client.getInputStream());
				oos = new ObjectOutputStream(client.getOutputStream());
				Object objectIn = ois.readObject();
				Object objectOut = objectManager.manageObject(objectIn);
				oos.writeObject(objectOut);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

	}
}
