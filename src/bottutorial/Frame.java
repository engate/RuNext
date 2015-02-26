package bottutorial;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import javax.swing.*;
import loaders.ClientPool;
import org.pushingpixels.substance.internal.utils.HashMapKey;

/**
 * @author Brandon
 */
public class Frame extends JFrame {
    
    private JLabel SplashScreen = null;
    private static final HashMap<Integer, Client> clients = ClientPool.getClients();
    JTabbedPane jtp = new JTabbedPane();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    public Frame(String Name) {
        this.setTitle(Name);
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0, 0)); //Remove Gaps.. 0 horizontal, 0 vertical.
        
        //Create a splashscreen image from a URL..
        try {
            this.addSplashScreen(new ImageIcon(new URL("http://i.imgur.com/f18lzq5.png")));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

       /* this.getContentPane().add(jtp);

        jtp.addTab("Tab1", jp1);
        jtp.addTab("Tab2", jp2);
*/
        JMenuBar bar = new JMenuBar();
        bar.setAlignmentX(0);
        bar.setAlignmentY(0);
        bar.setMaximumSize(new Dimension(1000,50));
        JMenu menu = new JMenu("Input");
        bar.add(menu);
        JMenuItem item = new JMenuItem("Test", KeyEvent.VK_B);
        menu.add(item);
        this.add(bar);

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Performing action..");

                //mouse click on mute

                System.out.println(getInnerPackClasses());


            }
        });

        this.pack(); //Make all components fit snug into the JFrame.
        this.centerWindow(); //Center it on screen.
        this.setVisible(true);
    }
    public Hashtable<String, byte[]> getInnerPackClasses() {

        Hashtable<String, byte[]> innerPackClasses = new Hashtable<String, byte[]>();

        try {
            Field field= BotLoader.client.getApplet().getClass().getDeclaredField("p");
            field.setAccessible(true);

            Object clzz = field.get(BotLoader.client.getApplet().getClass());

            Field tab1 = BotLoader.client.getLoader().getClass().getClassLoader().loadClass("h").getDeclaredField("h");
            tab1.setAccessible(true);

            Field tab2 = BotLoader.client.getLoader().getClass().getClassLoader().loadClass("g").getDeclaredField("l");
            tab2.setAccessible(true);

            innerPackClasses.putAll((Hashtable<String, byte[]>) tab1.get(clzz));
            innerPackClasses.putAll((Hashtable<String, byte[]>) tab2.get(clzz));

        } catch (Exception e) {
        }

        return innerPackClasses;
    }
    private void centerWindow() {
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int X = (int) ((ScreenSize.getWidth() - this.getWidth()) / 2);
        int Y = (int) ((ScreenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(X, Y);
    }

    public final void addSplashScreen(ImageIcon Img) {
        JLabel lab = new JLabel();

        lab.setSize(200,200);
        lab.setText("Loading client :)");
        //jp1.add(lab);
    }

    public final void removeSplashScreen() {
        if (this.SplashScreen != null) {
            this.remove(this.SplashScreen);
            this.SplashScreen = null;
        }
    }
    public void addClient(Client client) {
        synchronized(clients) {  //To support multi-threading if you add Tabs.
            clients.put(client.getCanvas().getClass().getClassLoader().hashCode(), client); //First we add the canvas's HASHCODE to the ClientPool.
            this.removeSplashScreen();  //Obviously have to remove the splashscreen before adding the client to the frame.
            add(client.getLoader(), BorderLayout.CENTER); //Add the JPanel that contains the applet to this JFrame.
            this.pack(); //Make it fit snug into the JFrame.
        }
    }
}
