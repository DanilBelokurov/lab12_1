import client.*;

import java.io.*;
import java.net.*;

public class ClientMain {
	public static void main(String[] args) throws IOException {
		Client client = new Client(new Socket("localhost", 5555));
		client.connect();
		client.messaging();
	}
}