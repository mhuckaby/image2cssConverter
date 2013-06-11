package com.rf1m.image2css.cli;

public class SystemWrapper {
    protected final int exit = -1;

    public void exit() {
        System.exit(exit);
    }
}
