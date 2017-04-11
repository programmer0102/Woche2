package de.fhac.rn;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	public static void main(String[] args) throws Throwable {
		Socket socket = new Socket("localhost", 5000);
		EchoClientThread clientThread = new EchoClientThread(socket);
		clientThread.start();
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			dataOutputStream.writeUTF(scanner.nextLine());
		}
		scanner.close();
	}
}