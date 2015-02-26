package loaders;

import bottutorial.Utilities;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Brandon
 */
public class ClientApplet extends JPanel implements AppletStub {

    private Applet applet = null;
    private URLClassLoader ClassLoader = null;
    private URL codeBase = null, documentBase = null;
    private HashMap<String, String> parameters = new HashMap<>();
    private static final Pattern codeRegex = Pattern.compile("code=(.*) ");
    private static final Pattern archiveRegex = Pattern.compile("archive=(.*) ");
    private static final Pattern parameterRegex = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

    public ClientApplet(String World, boolean DownloadGamePack, int Width, int Height) {
        try {
            this.setLayout(new BorderLayout(0, 0)); //Makes the applet fit tightly into our JPanel (No gaps).
            String PageSource = Utilities.downloadPage(new URL(World)); //Download the HTML Source for the specified World.
            Matcher CodeMatcher = codeRegex.matcher(PageSource); //Attach the CodeRegex Matcher to the source.
            Matcher ArchiveMatcher = archiveRegex.matcher(PageSource); //Attach the Archive Pattern Matcher to the source.

            if (CodeMatcher.find() && ArchiveMatcher.find()) {
                String Archive = ArchiveMatcher.group(1);
                String JarLocation = World + "/" + Archive; //The location of the JarFile is: http://world#.runescape.com/ + Archive/JarName.
                String Code = CodeMatcher.group(1).replaceAll(".class", "");
                Matcher ParameterMatcher = parameterRegex.matcher(PageSource);
                this.codeBase = new URL(JarLocation);
                this.documentBase = new URL(World);

                while (ParameterMatcher.find()) { //For every parameter, add it to our HashMap<ParamKey, ParamValue>;
                    this.parameters.put(ParameterMatcher.group(1), ParameterMatcher.group(2));
                }

                if (!DownloadGamePack) { //If we do NOT want to download the Jar, then just load the applet from the jar location.
                    ClassLoader = new URLClassLoader(new URL[]{new URL(JarLocation)});
                    applet = (Applet) ClassLoader.loadClass(Code).newInstance();
                } else { //Else download the Jar then load from the Jar. Can be modified for injecting into the Jar.
                    Utilities.downloadFile(new URL(JarLocation), "./gamepack.jar");
                    ClassLoader = new URLClassLoader(new URL[]{new URL("file:./gamepack.jar")});
                    applet = (Applet) ClassLoader.loadClass(Code).newInstance();
                }

                applet.setStub(this); //Let the applet know that this class contains the stub.
                applet.setPreferredSize(new Dimension(Width, Height)); //Set the size of the applet/JPanel.
                this.add(applet, BorderLayout.CENTER); //Add the applet to this JPanel.
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException Ex) {
            Ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Loading.. Please Check Your Internet Connection.", "Error Loading..", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void start() { //This function will initialize our applet and then start it.
        if (this.applet != null) {
            this.applet.init();
        }

        if (this.applet != null) {
            this.applet.start();
        }
    }

    public void destruct() { //Java doesn't have destructors so we need to call this manually..
        if (this.applet != null) {
            this.remove(this.applet); //First remove the applet from the JPanel.
            this.applet.stop();  //Next we need to stop it from running.
            this.applet.destroy(); //Finally, clean up any resources the applet was using/created.
            this.applet = null;
        }

        if (this.ClassLoader != null) { //Close our URLClass loader to prevent resource leaks.
            try {
                this.ClassLoader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        return this.documentBase;
    }

    @Override
    public URL getCodeBase() {
        return this.codeBase;
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int width, int height) {
    }
}
