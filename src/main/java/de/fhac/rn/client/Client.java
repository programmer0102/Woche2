package de.fhac.rn.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        var scanner = new Scanner(System.in);
        var clientSocket = new Socket("localhost", 5678);
        var outToServer = new DataOutputStream(clientSocket.getOutputStream());
        var inFromServer = new DataInputStream(clientSocket.getInputStream());

        var message = scanner.nextLine();
        outToServer.writeUTF(message);
        var echoMessage = inFromServer.readUTF();
        System.out.println(echoMessage);

        scanner.close();
        clientSocket.close();
    }
}