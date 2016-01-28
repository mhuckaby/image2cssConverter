package com.rf1m.image2css.cli

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser

class Image2CssMain {

    public static void main(final String[] args) {
        Image2CssMain image2CssMain
        ResourceBundle resourceBundle = ResourceBundle.getBundle("image2css-cli")
        int exit = 0

        try {
            image2CssMain = Image2CssMain.init(args)
            image2CssMain.execute()
        }catch(CmdLineException cmdLineException) {
            println cmdLineException.message
            exit = -1
        }catch(Exception e) {
            // TODO Print full stacktrace to a tmp file for submission
            String message = resourceBundle.getString("message.abnormal.exit")
            println String.format(message, e.message)
            exit = -1
        }

        System.exit(exit)
    }

    private static Image2CssMain init(final String[] args) {
        CommandLineArgument commandLineArgument = new CommandLineArgument(originalArgs: args)
        CmdLineParser cmdLineParser = new CmdLineParser(commandLineArgument)
        cmdLineParser.parseArgument(args)

        new Image2CssMain(commandLineArgument: commandLineArgument)
    }

    CommandLineArgument commandLineArgument

    private void execute() {

    }

}
