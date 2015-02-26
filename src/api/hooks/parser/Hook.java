package api.hooks.parser;

/**
 * Created by Richard on 2015.02.26..
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.Serializable;

public class Hook implements Serializable {
    private long mult;
    private static final long serialVersionUID = 1L;
    private String className;
    private String fieldName;
    private String realName;
    private String sourceName;

    public Hook(String realName, String sourceName) {
        this(realName, sourceName, 1L);
    }

    public Hook(String realName, String sourceName, long mult) {
        this.mult = mult;
        this.realName = realName;
        this.sourceName = sourceName;
        this.className = sourceName.split("\\.")[0];
        this.fieldName = sourceName.split("\\.")[1];
    }

    public String getRealName() {
        return this.realName;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public long getMultiplier() {
        return this.mult;
    }

    public String getClassName() {
        return this.className;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String toString() {
        return String.format("------>Real name : %s   identified as : %s  %s", new Object[]{this.realName, this.sourceName, this.mult != 1L?" * " + this.mult:""});
    }
}

