package com.rf1m.image2css.cli

import org.kohsuke.args4j.Option
import org.kohsuke.args4j.spi.StringArrayOptionHandler


class CommandLineArgument {
    String[] originalArgs

    @Option(aliases = "--file-in",
            name = "-f",
            required = true,
            usage = "The filename, directory path, or absolute non-redirecting URL to an image file")
    String filename


    @Option(aliases = "--include",
            name = "-i",
            handler = StringArrayOptionHandler.class,
            required = false,
            usage = "Include types gif, jpg, jpeg, png, or supply none and include all types")
    String[] includes


    @Option(aliases = "--css-file-out",
            name = "-o",
            required = false,
            usage = "The path and name of the CSS file that will be written")
    String cssFile


    @Option(aliases = "--html-file-out",
            name = "-h",
            required = false,
            usage = "The path and name of the example HTML file that will be written")
    String htmlFile


    @Option(name = "-syso",
            required = false,
            usage = "Write the resulting CSS output to the console rather than a file")
    Boolean syso


}
