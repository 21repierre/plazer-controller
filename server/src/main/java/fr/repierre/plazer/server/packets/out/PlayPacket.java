package fr.repierre.plazer.server.packets.out;

import fr.repierre.plazer.server.packets.OutgoingPacket;

import java.net.InetAddress;

public class PlayPacket extends OutgoingPacket {

    public PlayPacket(InetAddress destination, int port, boolean state) {
        super(3, destination, port);
        this.data[0] = (byte) (state ? 0x1 : 0x0);
    }
}
