package fr.repierre.plazer.server.packets.in;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.packets.IncomingPacket;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SetSongPacket extends IncomingPacket {

    public SetSongPacket(InetAddress address, int port, byte[] data) {
        super(address, port, data);
    }

    @Override
    public void handle() {
        ByteBuffer buff = ByteBuffer.wrap(this.data, 0, 4);
        int songId = buff.getInt();
        String tmp = new String(this.data, 4, this.data.length - 4).replaceAll("\0", "");
        System.out.println(tmp);
        Navigator.getInstance().getCurrentService().setSong(tmp, songId);
    }
}
