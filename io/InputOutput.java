package io;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputOutput {

	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public String read() throws IOException {
		String message = in.readLine();
		return message;
	}

	public synchronized void write(String message) {
		System.out.println(message);
    }
    
    public void close() throws IOException {
		this.in.close();
	}
}
