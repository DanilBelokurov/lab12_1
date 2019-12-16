import server.*;

import java.io.*;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		Server server = new Server(5555);
		server.connecting();
	}
}