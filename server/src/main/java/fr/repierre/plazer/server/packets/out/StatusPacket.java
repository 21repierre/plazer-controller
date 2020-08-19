package fr.repierre.plazer.server.packets.out;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.navigator.services.Service;
import fr.repierre.plazer.server.packets.OutgoingPacket;
import org.json.JSONObject;

import java.net.InetAddress;

public class StatusPacket extends OutgoingPacket {

    public StatusPacket(InetAddress destination, int port) {
        super(4, destination, port);
        Service service = Navigator.getInstance().getCurrentService();
        JSONObject main = new JSONObject();
        JSONObject song = new JSONObject();

        JSONObject state = new JSONObject();
        state.put("play", service.playing());
        state.put("time", service.getTime());
        state.put("duration", service.getDuration());
        song.put("state", state);

        song.put("title", service.getTitle());
        song.put("author", service.getAuthor());
        song.put("plateform", service.getName());

        main.put("song", song);
        /*byte[] toSend = ("{" +
                "\"song\": {" +
                "\"state\": {" +
                "\"play\":" + service.playing() +
                "}," +
                "\"title\":\"" + service.getTitle() + "\"," +
                "\"author\":\"" + service.getAuthor() + "\"," +
                "\"plateform\":\"" + service.getName() + "\"" +
                "}" +
                "}").getBytes();*/
        byte[] toSend = main.toString().getBytes();
        System.arraycopy(toSend, 0, this.data, 0, toSend.length);
    }
}
