package fr.repierre.plazer.server.packets.out;

import fr.repierre.plazer.server.packets.OutgoingPacket;

import java.net.InetAddress;

public class SearchPacket extends OutgoingPacket {

    public SearchPacket(InetAddress destination, int port, String result) {
        super(2, destination, port);
        byte[] bts = result.getBytes();
        System.arraycopy(bts, 0, this.data, 0, bts.length);
    }
}
