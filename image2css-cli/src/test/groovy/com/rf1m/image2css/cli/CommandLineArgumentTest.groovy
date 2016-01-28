package com.rf1m.image2css.cli

import org.junit.Test
import org.kohsuke.args4j.CmdLineParser

import static org.junit.Assert.assertEquals

class CommandLineArgumentTest {

    String filename = "test.png"

    @Test
    public void "should require -f"() {
        String[] args = ["-f", filename]
        CommandLineArgument commandLineArgument = new CommandLineArgument(originalArgs: args)
        CmdLineParser cmdLineParser = new CmdLineParser(commandLineArgument)
        cmdLineParser.parseArgument(args)

        assertEquals(commandLineArgument.filename, filename)
    }

    @Test
    public void "should allow several include types -i"() {
        String[] includes = ["png", "gif"]
        String[] args = ["-f", "test.png", "-i", includes[0], includes[1]]
        CommandLineArgument commandLineArgument = new CommandLineArgument(originalArgs: args)
        CmdLineParser cmdLineParser = new CmdLineParser(commandLineArgument)
        cmdLineParser.parseArgument(args)

        assertEquals(commandLineArgument.filename, filename)
        assertEquals(commandLineArgument.includes, includes)
    }

}