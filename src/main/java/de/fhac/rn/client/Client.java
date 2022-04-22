package de.fhac.rn.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private boolean running;
    Scanner scanner;

    public Client(Socket socket) {
        this.socket = socket;
        scanner = new Scanner(System.in);
        running = true;
    }

    public void sendToServer(String message) throws IOException {
        var outToServer = new DataOutputStream(socket.getOutputStream());
        outToServer.writeUTF(message);
    }

    public String waitForNewMessage() throws IOException {
        var inFromServer = new DataInputStream(socket.getInputStream());
        return inFromServer.readUTF();
    }

    public void stop() throws IOException {
        this.socket.close();
        this.scanner.close();
    }

    public boolean isRunning() {
        return running;
    }

    public String promptForNewMessage() {
        String message = scanner.nextLine();
        return message;
    }

    public void processReceivdeMessage(String message) {
        System.out.println(message);
        if (message.contains("/exit")) {
            running = false;
        }
    }


    public static void main(String[] args) throws Exception {
        /* var scanner = new Scanner(System.in);*/
        var clientSocket = new Socket("localhost", 5678);
        //var outToServer = new DataOutputStream(clientSocket.getOutputStream());

        Client client = new Client(clientSocket);
        while (client.isRunning()) {
            String message = client.promptForNewMessage();
            client.sendToServer(message);
            String returnMessage = client.waitForNewMessage();
            client.processReceivdeMessage(returnMessage);
        }
        client.stop();
/*        var message = scanner.nextLine();
        outToServer.writeUTF(message);
        var echoMessage = inFromServer.readUTF();
        System.out.println(echoMessage);
        scanner.close();
        clientSocket.close();*/
    }
}