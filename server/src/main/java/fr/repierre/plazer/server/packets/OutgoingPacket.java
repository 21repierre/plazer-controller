package fr.repierre.plazer.server.packets;

import fr.repierre.plazer.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class OutgoingPacket extends Packet {

    private final byte id;
    protected final byte[] data = new byte[8184];

    public OutgoingPacket(int id, InetAddress destination, int port) {
        super(destination, port);
        this.id = (byte) id;
    }

    public final void send() {
        try {
            byte[] toSend = new byte[8192];
            toSend[0] = id;
            System.arraycopy(data, 0, toSend, 8, 8184);
            DatagramPacket send = new DatagramPacket(toSend, toSend.length, destination, port);
            Server.getInstance().getServer().send(send);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
