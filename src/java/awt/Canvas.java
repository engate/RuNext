package java.awt;

/**
 * @author Brandon
 */
import bottutorial.Client;
import java.awt.image.BufferStrategy;
import java.awt.peer.CanvasPeer;
import javax.accessibility.*;
import loaders.ClientPool;

public class Canvas extends Component implements Accessible {

    private Client client = null; //Hold a reference to the Client that owns this canvas..
    private static final String base = "canvas";
    private static int nameCounter = 0;
    private static final long serialVersionUID = -2284879212465893870L;

    public Canvas() {
        super();
    }

    public Canvas(GraphicsConfiguration config) {
        this();
        setGraphicsConfiguration(config);
    }

    @Override
    void setGraphicsConfiguration(GraphicsConfiguration gc) {
        synchronized (getTreeLock()) {
            CanvasPeer peer = (CanvasPeer) getPeer();
            if (peer != null) {
                gc = peer.getAppropriateGraphicsConfiguration(gc);
            }
            super.setGraphicsConfiguration(gc);
        }
    }
    
    @Override
    public Graphics getGraphics() {
        if (this.client == null) {
            this.client = ClientPool.getClient(this); //Retrieve the client from the ClientPool :)
        }
        
        if (client != null) {
            //Call our Draw Graphics function for this specific client. This lets us do the drawing/double-buffering..
            return client.drawGraphics((Graphics2D) super.getGraphics());
        }
        return super.getGraphics();
    }

    @Override
    String constructComponentName() {
        synchronized (Canvas.class) {
            return base + nameCounter++;
        }
    }

    @Override
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null) {
                peer = getToolkit().createCanvas(this);
            }
            super.addNotify();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);
    }

    @Override
    public void update(Graphics g) {
        g.clearRect(0, 0, width, height);
        paint(g);
    }

    @Override
    boolean postsOldMouseEvents() {
        return true;
    }

    @Override
    public void createBufferStrategy(int numBuffers) {
        super.createBufferStrategy(numBuffers);
    }

    @Override
    public void createBufferStrategy(int numBuffers, BufferCapabilities caps) throws AWTException {
        super.createBufferStrategy(numBuffers, caps);
    }

    @Override
    public BufferStrategy getBufferStrategy() {
        return super.getBufferStrategy();
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTCanvas();
        }
        return accessibleContext;
    }

    protected class AccessibleAWTCanvas extends AccessibleAWTComponent {

        private static final long serialVersionUID = -6325592262103146699L;

        @Override
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.CANVAS;
        }
    }
}