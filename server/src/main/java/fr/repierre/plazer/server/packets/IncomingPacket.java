package fr.repierre.plazer.server.packets;

import fr.repierre.plazer.server.packets.in.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class IncomingPacket extends Packet {

    protected byte[] data = new byte[8184];

    public IncomingPacket(InetAddress address, int port, byte[] data) {
        super(address, port);
        System.arraycopy(data, 8, this.data, 0, 8184);
    }

    public abstract void handle();

    public enum Packets {
        WELCOME(0, WelcomePacket.class),
        KEEPALIVE(1, KeepAlive.class),
        SEARCH(2, SearchPacket.class),
        PLAY(3, PlayPacket.class),
        SET_SONG(4, SetSongPacket.class)
        ;

        private final byte identifier;
        private final Class<? extends IncomingPacket> packet;

        Packets(int i, Class<? extends IncomingPacket> packet) {
            this.identifier = (byte) i;
            this.packet = packet;
        }

        public static IncomingPacket craft(DatagramPacket packet) {
            byte[] data = packet.getData();
            for (Packets value : values()) {
                if (value.identifier == data[0]) {
                    try {
                        Constructor<? extends IncomingPacket> constructor = value.getPacket().getConstructor(InetAddress.class, int.class, byte[].class);
                        return constructor.newInstance(packet.getAddress(), packet.getPort(), data);
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        public byte getIdentifier() {
            return identifier;
        }

        public Class<? extends IncomingPacket> getPacket() {
            return packet;
        }

    }
}
