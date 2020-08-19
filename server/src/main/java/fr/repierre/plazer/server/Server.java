package fr.repierre.plazer.server;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.packets.IncomingPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread {

    private static Server instance;
    boolean running;
    private DatagramSocket server;

    public Server(int port) {
        super("plazer-server");
        this.running = true;
        instance = this;
        try {
            this.server = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] buff = new byte[8192];
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                server.receive(packet);
                IncomingPacket intPacket = IncomingPacket.Packets.craft(packet);
                if (intPacket != null) {
                    try {
                        intPacket.handle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Navigator.getInstance().getDriver().quit();
    }

    public void setRunning(boolean b) {
        this.running = b;
    }

    public DatagramSocket getServer() {
        return server;
    }
}
