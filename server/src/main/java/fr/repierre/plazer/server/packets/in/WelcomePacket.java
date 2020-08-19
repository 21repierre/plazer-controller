package fr.repierre.plazer.server.packets.in;

import fr.repierre.plazer.server.packets.Client;
import fr.repierre.plazer.server.packets.IncomingPacket;

import java.net.InetAddress;

public class WelcomePacket extends IncomingPacket {

    public WelcomePacket(InetAddress address, int port, byte[] data) {
        super(address, port, data);
    }

    @Override
    public void handle() {
        System.out.println("received welcome");

        Client.getClients().stream().filter(client -> client.getAddress().equals(destination) && client.getPort() == port).findFirst().ifPresentOrElse(client -> {
            client.setLastReceivedKeepAlive(System.currentTimeMillis());
            System.out.println("exist");
        }, () -> {
            Client client = new Client(this.destination, this.port);
            System.out.println("new");
        });
    }
}
