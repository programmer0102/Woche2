package de.fhac.rn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	public static void main(String[] args) throws Throwable {
		Socket socket = new Socket("localhost", 5000);
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		Scanner scanner = new Scanner(System.in);
		dataOutputStream.writeUTF(scanner.nextLine());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		System.out.println(dataInputStream.readUTF());
		scanner.close();
	}
}