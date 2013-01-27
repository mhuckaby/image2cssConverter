package com.rf1m.image2css.cli;

import java.io.File;
import java.util.Set;

public class MutableParameters implements Parameters {
    File imageFile = null;
    File cssFile = null;
    File htmlFile = null;
    Set<SupportedImageTypes> supportedTypes = null;
    boolean outputToScreen = false;

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public File getCssFile() {
        return cssFile;
    }

    public void setCssFile(File cssFile) {
        this.cssFile = cssFile;
    }

    public File getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(File htmlFile) {
        this.htmlFile = htmlFile;
    }

    public Set<SupportedImageTypes> getSupportedTypes() {
        return supportedTypes;
    }

    public void setSupportedTypes(Set<SupportedImageTypes> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    public boolean isOutputToScreen() {
        return outputToScreen;
    }

    public void setOutputToScreen(boolean outputToScreen) {
        this.outputToScreen = outputToScreen;
    }

    @Override
    public boolean isOutputValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCssFileOutputDesired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHtmlFileOutputDesired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOutputToConsoleDesired() {
        throw new UnsupportedOperationException();
    }

}
