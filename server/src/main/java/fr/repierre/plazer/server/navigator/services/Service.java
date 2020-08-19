package fr.repierre.plazer.server.navigator.services;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.packets.Client;
import fr.repierre.plazer.server.packets.out.StatusPacket;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collections;

public abstract class Service {

    protected final Navigator navigator = Navigator.getInstance();
    protected final ChromeDriver driver = Navigator.getInstance().getDriver();
    private final String name;
    public boolean ready = false;

    public Service(String name) {
        this.name = name;
    }

    public abstract void login();

    public abstract boolean needLogin();

    public abstract void init();

    public void loaded() {
    }

    public abstract void play(boolean status);

    public boolean playing() {
        return false;
    }

    public abstract void setSong(String identifier, int id);

    public String search(String s) {
        return "";
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return "";
    }

    public String getAuthor() {
        return "";
    }

    public abstract int getTime();

    protected final int strToTime(String str) {
        String[] splt = Arrays.stream(str.split(":")).sorted(Collections.reverseOrder()).toArray(String[]::new);
        int time = 0;
        int mult = 60;
        for (String s : splt) {
            int tmp = Integer.parseInt(s);
            time += mult / 60 * tmp;
            mult *= 60;
        }
        return time;
    }

    public abstract int getDuration();
}
