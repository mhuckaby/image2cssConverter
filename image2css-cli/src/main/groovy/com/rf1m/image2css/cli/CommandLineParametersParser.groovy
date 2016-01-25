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

import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.exception.Error
import com.rf1m.image2css.exception.Image2CssException
import com.rf1m.image2css.exception.Image2CssValidationException
import org.apache.commons.cli.BasicParser
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException

import java.util.regex.Matcher
import java.util.regex.Pattern

import static com.rf1m.image2css.domain.SupportedImageType.gif
import static com.rf1m.image2css.domain.SupportedImageType.jpg
import static com.rf1m.image2css.domain.SupportedImageType.png

class CommandLineParametersParser {
    protected static final Pattern patternHttp = Pattern.compile("http[s]?://")

    protected static final String[] defaultTypes = [gif.name(), jpg.name(), png.name()]

    protected final BasicParser basicParser

    protected final Option optionCssFile
    protected final Option optionHtmlFile
    protected final Option optionImageFile
    protected final Option optionSupportedImageTypes
    protected final Option optionSyso

    protected final Options options

    public CommandLineParametersParser(final BasicParser basicParser,
                                       final Option optionCssFile,
                                       final Option optionHtmlFile,
                                       final Option optionImageFile,
                                       final Option optionSupportedImageTypes,
                                       final Option optionSyso,
                                       final Options options) {

        this.basicParser = basicParser
        this.optionCssFile = optionCssFile
        this.optionHtmlFile = optionHtmlFile
        this.optionImageFile = optionImageFile
        this.optionSupportedImageTypes = optionSupportedImageTypes
        this.optionSyso = optionSyso
        this.options = options
    }

    public Parameters parse(final String[] args) throws ParseException {
        boolean isSysoSpecified = this.isSysoSpecified(args)
        correctOptionRequirementsForSyso(isSysoSpecified)

        CommandLine commandLine = basicParser.parse(options, args)

        File cssFile = isSysoSpecified ? null : extractFileFromOption(commandLine, optionCssFile.opt)
        File htmlFile = isSysoSpecified ? null : extractFileFromOption(commandLine, optionHtmlFile.opt)
        boolean isLocalResource = isALocalResource(commandLine, optionImageFile.opt)
        File imageFile = isLocalResource ? extractFileFromOption(commandLine, optionImageFile.opt) : null
        URL url = !isLocalResource ? extractURLFromOption(commandLine, optionImageFile.opt) : null
        Set<SupportedImageType> supportedImageTypes =
            extractImageTypesFromOption(commandLine, optionSupportedImageTypes.opt)

        new ImmutableParameters(imageFile, cssFile, htmlFile, supportedImageTypes, isSysoSpecified, isLocalResource, url)
    }

    protected void correctOptionRequirementsForSyso(final boolean isSysoSpecified) {
        if(isSysoSpecified) {
            optionHtmlFile.setRequired(false)
            optionCssFile.setRequired(false)
            options.getRequiredOptions().removeAll({ it ->
                optionHtmlFile.opt.equalsIgnoreCase(it)
                return optionHtmlFile.opt.equalsIgnoreCase(it) || optionCssFile.opt.equalsIgnoreCase(it)
            })
        }

    }

    protected boolean isSysoSpecified(final String[] args) {
        String dashSyso = "-$optionSyso.opt"

        for(String arg : args) {
            if(arg.equalsIgnoreCase(dashSyso)) {
                return true
            }else {
                continue
            }
        }

        return false
    }

    protected boolean isALocalResource(final CommandLine commandLine, final String option) {
        String[] optionValues = commandLine.getOptionValues(option)
        if(!optionValues) {
            false
        }else {
            String filename = commandLine.getOptionValues(option)[0]
            Matcher matcher = patternHttp.matcher(filename)

            !matcher.find()
        }
    }

    protected File extractFileFromOption(final CommandLine commandLine, final String option) {
        String[] optionValues = commandLine.getOptionValues(option)
        if(!optionValues) {
            null
        }else {
            new File(optionValues[0])
        }
    }

    protected URL extractURLFromOption(final CommandLine commandLine, final String option) {
        String[] optionValues = commandLine.getOptionValues(option)
        if(!optionValues) {
            null
        }else {
            try {
                new URL(commandLine.getOptionValues(option)[0])
            }catch(MalformedURLException e) {
                throw new Image2CssException(e, Error.errorParsingUrlParameter)
            }
        }
    }

    protected Set<SupportedImageType> extractImageTypesFromOption(final CommandLine commandLine, final String option) {
        Set<SupportedImageType> result = new HashSet()
        String[] optionValues = commandLine.getOptionValues(option)
        String[] selectedImageTypes = optionValues ? optionValues : defaultTypes

        for(String selectedImageType : selectedImageTypes) {
            result.add(this.convertStringImageTypeArgumentToEnumType(selectedImageType))
        }

        result
    }

    protected SupportedImageType convertStringImageTypeArgumentToEnumType(final String supportedImageTypeArg) {
        try {
            SupportedImageType.valueOf(supportedImageTypeArg.toLowerCase())
        }catch(final IllegalArgumentException illegalArgumentException) {
            throw new Image2CssValidationException(Error.parameterUnsupportedImageType)
        }
    }
}
