package api.hooks.parser;

import org.zbot.hooks.parser.*;
import org.zbot.hooks.reflect.Receiver;

import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Hashtable;

/**
 * Created by Richard on 2015.02.26..
 */
public class Parser {
    private org.zbot.hooks.parser.Hook c;
    private static final String HOOKS_FILE = "http://zbot.org/logs/log.ser";
    private Hashtable<String, Receiver> hooks = new Hashtable();

    public Parser(URLClassLoader classLoader) throws Exception {
        URLConnection s = (new URL("http://zbot.org/logs/log.ser")).openConnection();
        ObjectInputStream br = new ObjectInputStream(s.getInputStream());

        for(int x = 0; x < 216 && (this.c = (org.zbot.hooks.parser.Hook)br.readObject()) != null; ++x) {
            this.hooks.put(this.c.getRealName(), new Receiver(this.c, classLoader));
        }

        this.hooks.put((this.c = (org.zbot.hooks.parser.Hook)br.readObject()).getRealName(), new Receiver(this.c));
        this.hooks.put((this.c = (org.zbot.hooks.parser.Hook)br.readObject()).getRealName(), new Receiver(this.c));
        this.hooks.put((this.c = (org.zbot.hooks.parser.Hook)br.readObject()).getRealName(), new Receiver(this.c));
        this.hooks.put((this.c = (org.zbot.hooks.parser.Hook)br.readObject()).getRealName(), new Receiver(this.c));
        br.close();
    }

    public void setHooksTable(Hashtable<String, Receiver> hooks) {
        this.hooks = hooks;
    }

    public Hashtable<String, Receiver> getHookTable() {
        return this.hooks;
    }
}
