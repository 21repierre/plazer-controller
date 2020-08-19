package fr.repierre.plazer.server.packets;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    private static final List<Client> clients = new CopyOnWriteArrayList<>();
    private final InetAddress address;
    private final int port;
    private long lastReceivedKeepAlive = System.currentTimeMillis();
    private long lastSentKeepAlive = System.currentTimeMillis();

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        clients.add(this);
    }

    public static List<Client> getClients() {
        return clients;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public long getLastReceivedKeepAlive() {
        return lastReceivedKeepAlive;
    }

    public void setLastReceivedKeepAlive(long lastReceivedKeepAlive) {
        this.lastReceivedKeepAlive = lastReceivedKeepAlive;
    }

    public long getLastSentKeepAlive() {
        return lastSentKeepAlive;
    }

    public void setLastSentKeepAlive(long lastSentKeepAlive) {
        this.lastSentKeepAlive = lastSentKeepAlive;
    }
}
