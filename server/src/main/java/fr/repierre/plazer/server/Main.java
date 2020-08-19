package fr.repierre.plazer.server;

import fr.repierre.plazer.server.navigator.Navigator;
import fr.repierre.plazer.server.navigator.services.Deezer;

public class Main {

    public static void main(String... args) {
        System.out.println("Plazer server loading up...");

        Navigator navigator = new Navigator();
        Server server = new Server(15987);
        server.start();
        ClientWatcher watcher = new ClientWatcher();
        watcher.start();
        try {
            navigator.setCurrentService(new Deezer());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //server.setRunning(false);
            //
        }
    }

}
