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
    private Receiver receiver;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Client(Socket socket) {
        this.socket = socket;
        scanner = new Scanner(System.in);
        running = true;
    }

    public void sendToServer(String message) throws IOException {
        var outToServer = new DataOutputStream(socket.getOutputStream());
        if(message.endsWith("/exit")){
            scanner.close();
        }
        outToServer.writeUTF(message);
    }

    public String waitForNewMessage() throws IOException {
        var inFromServer = new DataInputStream(socket.getInputStream());
        return inFromServer.readUTF();
    }

    public void stop() throws IOException {
        this.socket.close();
    }

    public boolean isRunning() {
        return running;
    }

    public String promptForNewMessage() {
        String message = scanner.nextLine();
        return message;
    }

    public void processReceivedMessage(String message) {
        System.out.println(message);
        if (message.contains("/exit")) {
            running = false;
        }
    }

    private void startReceiver() {
        receiver = new Receiver(socket, this);
        receiver.start();
    }


    public static void main(String[] args) throws Exception {
        /* var scanner = new Scanner(System.in);*/
        var clientSocket = new Socket("localhost", 5678);
        //var outToServer = new DataOutputStream(clientSocket.getOutputStream());

        Client client = new Client(clientSocket);
        client.startReceiver();
        while (client.isRunning()) {
            String message = client.promptForNewMessage();
            client.sendToServer(message);
        }
        client.stop();
    }
}