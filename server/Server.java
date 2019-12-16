package server;

import java.net.*;
import java.util.*;
import java.io.*;
import client.*;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<Client> clients = new ArrayList<Client>();

	public Server(int portNumber) throws IOException {
		serverSocket = new ServerSocket(portNumber);
	}

	public void connecting() throws IOException {
		while (true) {
            Socket clientSocket = serverSocket.accept();
            
            (new DataOutputStream(clientSocket.getOutputStream())).writeUTF("Input name to connect: ");
            
            String name = (new DataInputStream((clientSocket.getInputStream()))).readUTF();
            
			Client client = new Client(clientSocket, name);
			ClientThread clientThread = new ClientThread(client);
			clients.add(client);
			clientThread.start();
		}
	}

	class ClientThread extends Thread {

        Client client;
        
		public ClientThread(Client client) {
			this.client = client;
		}

		public void run() {
			try {
				while(true) {
                    DataInputStream in = new DataInputStream(client.getSocket().getInputStream());
                    
                    String message = in.readUTF();
                    
					if (message.startsWith("@senduser")) {
                        int start = 10;
                        
						while(message.charAt(start) != ' ')
                            start++;
                            
                        String name = message.substring(10, start);
                        
						for (Client c : clients) {
							if(c.getName().equals(name)) {
								DataOutputStream output =  new DataOutputStream(c.getSocket().getOutputStream());
								output.writeUTF("From " + client.getName() + "in private:  " + message.substring(start + 1, message.length()));
							}
						}
					}
					else {
						for (Client c : clients) {
							if(!c.getSocket().equals(client.getSocket()))
								(new DataOutputStream(c.getSocket().getOutputStream())).writeUTF("From " + client.getName() + ":  " + message);
						}	
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}