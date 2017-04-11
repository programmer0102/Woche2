package de.fhac.rn;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClientThread extends Thread {
	private Socket socket;

	public EchoClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			while (true) {
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				System.out.println(dataInputStream.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}