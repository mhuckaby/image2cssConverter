package com.rf1m.image2css.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Image2CssHelpFormatter extends HelpFormatter {
    protected final String helpText;
    protected final Options options;

    public Image2CssHelpFormatter(final String helpText, final Options options) {
        this.helpText = helpText;
        this.options = options;
    }

    public void showHelp() {
        this.printHelp(this.helpText, this.options);
    }

}
