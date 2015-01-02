package com.rf1m.image2css.cli

import org.apache.commons.cli.Option

class Image2CssOption extends Option {

    public Image2CssOption(final String option, final String description, final boolean hasArg, final boolean required) {
        super(option, hasArg, description)
        this.required = required
    }

    public Image2CssOption(final String option,
                           final String description,
                           final boolean hasArg,
                           final boolean required,
                           final int argCount) {
        this(option, description, hasArg, required)
        this.valueSeparator = ' '
        this.args = argCount
    }

}
