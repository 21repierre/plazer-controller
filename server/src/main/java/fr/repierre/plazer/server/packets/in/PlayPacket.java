package fr.repierre.plazer.server.packets.in;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.packets.IncomingPacket;

import java.net.InetAddress;

public class PlayPacket extends IncomingPacket {

    public PlayPacket(InetAddress from, int port, byte[] data) {
        super(from, port, data);
    }

    @Override
    public void handle() {
        boolean state = data[0] != 0;
        Navigator.getInstance().getCurrentService().play(state);

        new fr.repierre.plazer.server.packets.out.PlayPacket(destination, port, state).send();
    }
}
