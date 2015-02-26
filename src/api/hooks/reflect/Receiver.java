package api.hooks.reflect;

/**
 * Created by Richard on 2015.02.26..
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.zbot.hooks.parser.Hook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.stream.Stream;

public class Receiver {
    private Field field;
    private Method method;
    private int multiplier;
    private String fieldName;
    private String className;

    public Receiver(Hook hook) throws Exception {
        this.fieldName = hook.getFieldName();
        this.className = hook.getClassName();
        this.multiplier = (int)hook.getMultiplier();
    }

    public Receiver(Hook h, URLClassLoader u) throws Exception {
        this.fieldName = h.getFieldName();
        this.className = h.getClassName();
        this.multiplier = (int)h.getMultiplier();
        this.field = u.loadClass(this.className).getDeclaredField(this.fieldName);
        this.field.setAccessible(true);
    }

    private Method get(Class<?> c, String name) {
        /*Stream stream = Arrays.stream(c.getDeclaredMethods());
        stream = stream.filter((m) -> {
            return m.getName().equals(name);
        });
        return (Method)stream.findFirst().orElse((Object)null);*/

        return null;
    }

    public void setInvoker(URLClassLoader u) throws Exception {
        this.method = this.get(u.loadClass(this.className), this.fieldName);
        this.method.setAccessible(true);
    }

    public Field getField() {
        return this.field;
    }

    public long getMultiplier() {
        return (long)this.multiplier;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getClassName() {
        return this.className;
    }

    public Object value() {
        return this.value((Object)null);
    }

    public int intValue(Object o) {
        return this.multiplier * ((Integer)this.value(o)).intValue();
    }

    public int intValue() {
        return this.multiplier * ((Integer)this.value((Object)null)).intValue();
    }

    public Object value(Object instance) {
        try {
            return this.field.get(instance);
        } catch (Exception var3) {
            return null;
        }
    }
}
