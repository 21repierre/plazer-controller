package fr.repierre.plazer.server;

import fr.repierre.plazer.server.packets.Client;
import fr.repierre.plazer.server.packets.out.KeepAlive;
import fr.repierre.plazer.server.packets.out.StatusPacket;

import java.util.ArrayList;
import java.util.List;

public class ClientWatcher extends Thread {

    @Override
    public void run() {
        while (Server.getInstance().running) {
            List<Client> delete = new ArrayList<>();
            try {
                Client.getClients().forEach(client -> {
                    if (System.currentTimeMillis() - client.getLastReceivedKeepAlive() > 60 * 1000) {
                        delete.add(client);
                        System.out.println(client.getLastReceivedKeepAlive());
                        return;
                    }
                    if (System.currentTimeMillis() - client.getLastSentKeepAlive() > 20 * 1000) {
                        new KeepAlive(client.getAddress(), client.getPort()).send();
                        client.setLastSentKeepAlive(System.currentTimeMillis());
                    }
                    new StatusPacket(client.getAddress(), client.getPort()).send();
                });
                Client.getClients().removeAll(delete);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
