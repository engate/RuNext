package bottutorial;

import java.applet.Applet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brandon
 */
public class BotLoader {
    public static Client client = null;
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Frame F = new Frame("RuneBuddy");
        Utilities.sleep(1000); //Sleep 1000 so we can show the splash screen.. You don't have to..
        client = new Client("http://world44.runescape.com", 800, 600);

        //client.getApplet().getClass().getMethod("stop").invoke(client.getApplet(), null);

        while (client.getCanvas() == null) { //wait for our Canvas to initialize..
            Utilities.sleep(100);
        }
        F.addClient(client);
        System.out.println(client.getApplet().getClass().getSimpleName());
        for(Method cl : client.getApplet().getClass().getDeclaredMethods())
            System.out.println(cl.getName());
    }


    }
