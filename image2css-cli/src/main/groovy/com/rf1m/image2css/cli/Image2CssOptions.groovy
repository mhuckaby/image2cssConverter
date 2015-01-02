package com.rf1m.image2css.cli

import org.apache.commons.cli.Options

class Image2CssOptions extends Options {

    public Image2CssOptions(final Image2CssOption ... image2CssOption) {
        for(Image2CssOption option : image2CssOption) {
            this.addOption(option)
        }
    }
}
