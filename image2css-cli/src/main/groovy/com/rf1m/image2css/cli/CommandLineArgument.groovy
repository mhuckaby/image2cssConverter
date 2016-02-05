/**
 *
 * Copyright (c) 2011-2016 Matthew D Huckaby. All rights reservered.
 * ------------------------------------------------------------------------------------
 * Image2Css is licensed under Apache 2.0, please see LICENSE file.
 *
 * Use of this software indicates you agree to the following as well :
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * This product includes software developed by The Apache Software Foundation (http://www.apache.org/).
 * ------------------------------------------------------------------------------------
 */
package com.rf1m.image2css.cli

import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import org.kohsuke.args4j.spi.StringArrayOptionHandler


class CommandLineArgument {
    CmdLineParser cmdLineParser
    String[] originalArgs

    @Option(aliases = "--css-file-out",
            name = "-o",
            required = false,
            usage = "The path and name of the CSS file that will be written")
    String cssFile


    @Option(aliases = "--file-in",
            name = "-f",
            required = true,
            usage = "The filename, directory path, or absolute non-redirecting URL to an image file")
    String filename


    @Option(name = "--help",
            required = false,
            help = true,
            usage = "Print the help text")
    Boolean help


    @Option(aliases = "--html-file-out",
            name = "-h",
            required = false,
            usage = "The path and name of the example HTML file that will be written")
    String htmlFile


    @Option(aliases = "--include",
            name = "-i",
            handler = StringArrayOptionHandler.class,
            required = false,
            usage = "Include types gif, jpg, jpeg, png, or supply none and include all types")
    String[] includes = [] as String[]


    @Option(name = "-syso",
            required = false,
            usage = "Write the resulting CSS output to the console rather than a file")
    Boolean syso


    @Option(aliases = "--version",
            name = "-v",
            required = false,
            usage = "Prints the name and version to console")
    Boolean version

    CmdLineParser getCmdLineParser() {
        return cmdLineParser
    }

    String[] getOriginalArgs() {
        return originalArgs
    }

    String getCssFile() {
        return cssFile
    }

    String getFilename() {
        return filename
    }

    Boolean getHelp() {
        return help
    }

    String getHtmlFile() {
        return htmlFile
    }

    String[] getIncludes() {
        return includes
    }

    Boolean getSyso() {
        return syso
    }

    Boolean getVersion() {
        return version
    }
}
