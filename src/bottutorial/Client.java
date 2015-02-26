package bottutorial;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import loaders.ClientApplet;

/**
 * @author Brandon
 */
public final class Client {

    private ClientApplet loader = null;
    private BufferedImage gameBuffer = null, paintBuffer = null;

    public Client(String World, int Width, int Height) {
        this.loader = new ClientApplet(World, false, Width, Height);
        
        //Create our Buffered that are the same size as the Applet/Applet's Canvas.
        this.gameBuffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        this.paintBuffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        
        //Start the applet.. This can always be done at any time you wish..
        //If you are implementing Tabs, then put this function call inside of a thread..
        this.getLoader().start();
    }

    public Canvas getCanvas() {
        //The canvas is Component 0 of our Applet which is in our Loader/JPanel.
        return this.getApplet().getComponentCount() > 0 ? (Canvas) this.getApplet().getComponent(0) : null;
    }

    public ClientApplet getLoader() {
        return this.loader;
    }

    public Applet getApplet() {
        //Our loader is our ClientApplet class which is really a JPanel with an applet in it.
        //Our loader's first component is the applet that we added to it (component 0 of our JPanel).
        return this.loader != null ? (Applet) this.loader.getComponent(0) : null;
    }

    public Graphics drawGraphics(Graphics2D G) {
        Graphics paintGraphics = paintBuffer.getGraphics(); //Get our paintGraphics GDI+ Handle.
        paintGraphics.drawImage(this.gameBuffer, 0, 0, null); //Draw the gameBuffer onto our paintBuffer.
        
        //Draw a string onto the paintBuffer..
        paintGraphics.setColor(Color.white);
        paintGraphics.drawString("Brandon-T: \nI hope you enjoy this tutorial so far.\nThis example demonstrates"
                + "Custom Drawing On Our Overriden Canvas", 100, 100);
        paintGraphics.dispose(); //Clean up GDI+ Handle.
        
        if (G != null) {
            G.drawImage(this.paintBuffer, 0, 0, null); //Draw our DebugBuffer onto the Canvas component/GameBuffer.
        }
        
        return this.gameBuffer.getGraphics(); //Return our gameBuffer that has all our drawings on it :)
    }
}
