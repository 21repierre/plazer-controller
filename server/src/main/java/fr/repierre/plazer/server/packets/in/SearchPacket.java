package fr.repierre.plazer.server.packets.in;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.packets.IncomingPacket;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class SearchPacket extends IncomingPacket {

    public SearchPacket(InetAddress from, int port, byte[] data) {
        super(from, port, data);
    }

    @Override
    public void handle() {
        String search = new String(this.data, 0, this.data.length).replaceAll("\0", "");
        if (search.isEmpty()) {
            new fr.repierre.plazer.server.packets.out.SearchPacket(destination, port, "[]").send();
            return;
        }
        JSONArray ret = new JSONArray();
        String result = Navigator.getInstance().getCurrentService().search(search);

        JSONArray main = new JSONObject(result).getJSONArray("data");
        main.toList().forEach(element -> {
            Map<Object, Object> elem = (HashMap) element;
            JSONObject object = new JSONObject();
            object.put("id", elem.get("id"));
            object.put("duration", elem.get("duration"));
            String title = (String) elem.get("title");
            if (title.length() > 40) title = title.substring(0, 37) + "...";
            object.put("title", title);
            object.put("link", elem.get("link"));
            JSONObject author = new JSONObject();
            JSONObject authorOld = new JSONObject((Map) elem.get("artist"));
            author.put("id", authorOld.get("id"));
            String authorName = authorOld.getString("name");
            if (authorName.length() > 40) authorName = authorName.substring(0, 37) + "...";
            author.put("name", authorName);
            author.put("picture", authorOld.get("picture_xl"));
            object.put("author", author);
            ret.put(object);
        });
        new fr.repierre.plazer.server.packets.out.SearchPacket(destination, port, ret.toString()).send();
    }

    private void oldWay() {
        String search = new String(this.data, 0, this.data.length).replaceAll("\0", "");
        System.out.println(search);
        String result = "}," + Navigator.getInstance().getCurrentService().search(search).substring(9);

        StringBuilder listStr = new StringBuilder();

        String[] splt = result.split("},\\{\"id\":");

        for (String line : splt) {
            if (line.isEmpty()) continue;
            String tmp = line.replaceFirst("[0-9]+,", "").replaceAll("\\/", "/");
            StringBuilder attrs = new StringBuilder();
            for (String s : tmp.split(",\"")) {
                String[] sSplit = s.split(":");
                if (sSplit.length < 2) continue;
                String key = sSplit[0];
                key = key.substring(0, key.length() - 1);

                if (key.equalsIgnoreCase("title") || key.equalsIgnoreCase("link") || key.equalsIgnoreCase("artist")) {
                    attrs.append(s).append(",\"");
                }
            }
            listStr.append("{\"").append(attrs.toString(), 0, attrs.length() - 2).append("}},");
        }
        listStr.deleteCharAt(listStr.length() - 1);
        System.out.println(listStr);
        String ret = "[" +
                listStr +
                "]";

        new fr.repierre.plazer.server.packets.out.SearchPacket(destination, port, ret).send();
    }

}
