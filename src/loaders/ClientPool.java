package loaders;

import bottutorial.Client;
import java.awt.Canvas;
import java.util.HashMap;

/**
 * @author Brandon
 */
public class ClientPool {

    private static ClientPool instance = new ClientPool();
    private static HashMap<Integer, Client> clients = new HashMap<>();

    private ClientPool() {
    }

    public static ClientPool getInstance() {
        return instance;
    }

    public static HashMap<Integer, Client> getClients() {
        return clients;
    }

    public static Client getClient(int hashCode) {
        synchronized (clients) {
            return clients.containsKey(hashCode) ? clients.get(hashCode) : null;
        }
    }

    public static Client getClient(Canvas canvas) {
        synchronized (clients) {
            int hashCode = canvas.getClass().getClassLoader().hashCode();
            return clients.containsKey(hashCode) ? clients.get(hashCode) : null;
        }
    }
}
