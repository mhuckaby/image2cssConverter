package com.rf1m.image2css.cli;

import org.apache.commons.cli.Option;

public class Image2CssOption extends Option {

    public Image2CssOption(final String option, final String description, final boolean hasArg, final boolean required) {
        super(option, hasArg, description);

        this.setRequired(required);
    }

    public Image2CssOption(final String option,
                           final String description,
                           final boolean hasArg,
                           final boolean required,
                           final char valueSeparator,
                           final int argCount) {
        this(option, description, hasArg, required);

        this.setValueSeparator(valueSeparator);
        this.setArgs(argCount);
    }

}
