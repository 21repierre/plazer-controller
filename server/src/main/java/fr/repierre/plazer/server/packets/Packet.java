package fr.repierre.plazer.server.packets;

import java.net.InetAddress;

public abstract class Packet {


    protected final InetAddress destination;
    protected final int port;

    public Packet(InetAddress destination, int port) {
        this.destination = destination;
        this.port = port;
    }

    public InetAddress getDestination() {
        return destination;
    }

    public int getPort() {
        return port;
    }
}
