package de.fhac.rn.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
    Socket socket;
    Client client;

    public Receiver(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    public void processReceivedMessage(String message) {
        System.out.println(message);
        if (message.contains("/exit")) {
            client.setRunning(false);
        }
    }

    public String waitForNewMessage() throws IOException {
        var inFromServer = new DataInputStream(socket.getInputStream());
        return inFromServer.readUTF();
    }

    public void run() {
        String returnMessage = null;
        while (client.isRunning()) {
            try {
                returnMessage = this.waitForNewMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.processReceivedMessage(returnMessage);
        }
        try {
            client.stop();
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
