package api;

import org.zbot.api.methods.utilities.Utilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Richard on 2015.02.26..
 */
public final class Keyboard {
    private Canvas canvas;
    private KeyListener rsKeyListener;

    public Keyboard() {
    }

    public void setKeyboard(Canvas canvas) {
        this.rsKeyListener = canvas.getKeyListeners()[0];
        this.canvas = canvas;
    }

    public void sendMousePressed(char c) {
        this.sendEvent(c, true);
    }

    public void sendMouseRelease(char c) {
        this.sendEvent(c, false);
    }

    private char getKeyChar(char c) {
        return c >= 36 && c <= 40?'\u0000':c;
    }

    public void writeText(String text) {
        char[] var5;
        int var4 = (var5 = text.toCharArray()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            char c = var5[var3];
            KeyEvent event = new KeyEvent(this.canvas, 400, System.currentTimeMillis(), 0, 0, c);
            this.rsKeyListener.keyTyped(event);
            Utilities.sleep(350, 600);
        }

    }

    public void sendEvent(char key, boolean pressed) {
        KeyEvent event = new KeyEvent(this.canvas, pressed?401:402, System.currentTimeMillis(), 0, key, this.getKeyChar(key));
        this.canvas.dispatchEvent(event);
    }
}
