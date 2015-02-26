package bottutorial;

/**
 * @author Brandon
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class Utilities {
    private static final Random random = new Random();
    private static Utilities instance = new Utilities();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss");
    
    private Utilities() { //Private constructor so the user can't create instances.
    }
    
    /* Returns the one and only instance of this class */
    public static Utilities getInstance() {
        return instance;
    }
    
    /* Generate random number between Min and Max */
    public static int random(int Min, int Max) {
        return random.nextInt(Math.abs(Max - Min)) + Min;
    }

    /* Convenient Function For sleeping.. */
    public static void sleep(int Time) {
        try {
            Thread.sleep(Time);
        } catch (InterruptedException Ex) {
            System.out.println(Ex.getMessage());
        }
    }

    public static void sleepRandom(int Min, int Max) {
        sleep(random(Min, Max));
    }

    private static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static void screenShot(BufferedImage Img) {
        try {
            File Directory = new File("ScreenShots/");
            if (!Directory.exists()) {
                Directory.mkdir();
            }
            
            if (Directory.exists()) {
                File Picture = new File("ScreenShots/" + dateFormat.format(getDate()) + ".png");
                ImageIO.write(Img, "png", Picture);
            }
        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
    } 
    
    public static void downloadFile(URL Address, String Path) throws IOException {
        URLConnection Connection = Address.openConnection();
        try (FileOutputStream OutFile = new FileOutputStream(Path); InputStream IStream = Connection.getInputStream()) {
            byte[] Buffer = new byte[1024];
            for (int Read = 0; (Read = IStream.read(Buffer)) != -1;) {
                OutFile.write(Buffer, 0, Read);
            }
        }
    }
    
    public static String downloadPage(URL Address) throws IOException {
        try {
            URLConnection Connection = Address.openConnection();
            Connection.addRequestProperty("Protocol", "HTTP/1.1");
            Connection.addRequestProperty("Connection", "keep-alive");
            Connection.addRequestProperty("Keep-Alive", "200");
            Connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");
            byte[] Buffer = new byte[Connection.getContentLength()];
            try (DataInputStream Stream = new DataInputStream(Connection.getInputStream())) {
                Stream.readFully(Buffer);
            }
            return new String(Buffer);
        } catch (Exception Ex) {
            return null;
        }
    }

    public Image scaleImage(int width, int height, Image Img) {
        return Img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public ImageIcon scaleIcon(int width, int height, ImageIcon Img) {
        return new ImageIcon(scaleImage(width, height, Img.getImage()));
    }

    public Image loadResourceImage(String ResourcePath) {
        try {
            return ImageIO.read(getClass().getResource(ResourcePath));
        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
        return null;
    }
    
    public Image grayScale(Image Img, int GrayPercentage) {
        ImageProducer Producer = new FilteredImageSource(Img.getSource(), new GrayFilter(true, GrayPercentage));  
        return java.awt.Toolkit.getDefaultToolkit().createImage(Producer);
    }
}
