package fr.repierre.plazer.server.packets.out;

import fr.repierre.plazer.server.packets.OutgoingPacket;

import java.net.InetAddress;

public class KeepAlive extends OutgoingPacket {

    public KeepAlive(InetAddress destination, int port) {
        super(1, destination, port);
    }
}
