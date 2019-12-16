package client;

import io.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	protected Socket socket;
	protected String name;
	private Sender sender;
	private Receiver receiver;

	public Client(Socket socket, String name) throws IOException {
		this.socket = socket;
        this.name = name;
        this.receiver = new Receiver(socket, new InputOutput());
	}

	public Client(Socket socket) throws IOException {
		this.socket = socket;
		this.sender = new Sender(socket, new InputOutput());
		this.receiver = new Receiver(socket, new InputOutput());
    }
    
    public void connect() throws IOException {
		System.out.println((new DataInputStream(socket.getInputStream())).readUTF());
		(new DataOutputStream(socket.getOutputStream())).writeUTF((new Scanner(System.in)).nextLine());
    }
    
    public void messaging() throws IOException {
		this.receiver.start();
		this.sender.start();
	}

	public String getName() {
		return name;
	}

	public Socket getSocket() {
		return socket;
	} 

	class Sender extends Thread {

		private Socket socket;
		private InputOutput inputoutput;
		private DataOutputStream out;

		public Sender(Socket socket, InputOutput inputoutput) throws IOException {
			this.socket = socket;
			this.inputoutput = inputoutput;
			this.out = new DataOutputStream(socket.getOutputStream());
		}

		public void run() {
			try {
				while(true) {
					String message = this.inputoutput.read();
					out.writeUTF(message);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class Receiver extends Thread {
		private Socket socket;
		private InputOutput inputoutput;
		private DataInputStream in;

		public Receiver(Socket socket, InputOutput inputoutput) throws IOException {
			this.socket = socket;
			this.inputoutput = inputoutput;
			this.in = new DataInputStream(this.socket.getInputStream());
		}

		public void run() {
			try
			{
				while(true) {
					String message = in.readUTF();
					inputoutput.write(message);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}