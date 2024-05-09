package org.example.proglab8;

import Network.Client;

public class ApplicationClient {
   private static Client client = new Client("192.168.1.47", 2448, 5000, 5);

    public static Client getClient() {
        return client;
    }
}
