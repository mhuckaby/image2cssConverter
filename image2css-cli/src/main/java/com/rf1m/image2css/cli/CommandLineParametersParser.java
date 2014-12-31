/**
 *
 * Copyright (c) 2011 Matthew D Huckaby. All rights reservered.
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
package com.rf1m.image2css.cli;

import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.exception.Error;
import com.rf1m.image2css.ioc.CliObjectFactory;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineParametersParser {
    protected final Pattern patternHttp = Pattern.compile("http[s]?://");
    protected final BasicParser basicParser;

    protected final Option optionCssFile;
    protected final Option optionHtmlFile;
    protected final Option optionImageFile;
    protected final Option optionSupportedImageTypes;
    protected final Option optionSyso;

    protected final Options options;

    protected final CliObjectFactory objectFactory;

    public CommandLineParametersParser(final BasicParser basicParser,
                                       final Option optionCssFile,
                                       final Option optionHtmlFile,
                                       final Option optionImageFile,
                                       final Option optionSupportedImageTypes,
                                       final Option optionSyso,
                                       final Options options,
                                       final CliObjectFactory objectFactory) {

        this.basicParser = basicParser;
        this.optionCssFile = optionCssFile;
        this.optionHtmlFile = optionHtmlFile;
        this.optionImageFile = optionImageFile;
        this.optionSupportedImageTypes = optionSupportedImageTypes;
        this.optionSyso = optionSyso;
        this.options = options;
        this.objectFactory = objectFactory;
    }

    public Parameters parse(final String[] args) throws ParseException {
        final boolean isSysoSpecified = this.isSysoSpecified(args);
        this.correctOptionRequirementsForSyso(isSysoSpecified);

        final CommandLine commandLine = basicParser.parse(options, args);

        final File cssFile = isSysoSpecified ? null : this.extractFileFromOption(commandLine, optionCssFile.getOpt());
        final File htmlFile = isSysoSpecified ? null : this.extractFileFromOption(commandLine, optionHtmlFile.getOpt());
        final boolean isLocalResource = this.isALocalResource(commandLine, optionImageFile.getOpt());
        final File imageFile = isLocalResource ? this.extractFileFromOption(commandLine, optionImageFile.getOpt()) : null;
        final URL url = !isLocalResource ? this.extractURLFromOption(commandLine, optionImageFile.getOpt()) : null;
        final Set<SupportedImageType> supportedImageTypes =
            this.extractImageTypesFromOption(commandLine, optionSupportedImageTypes.getOpt());

        return this.objectFactory.newImmutableParameters(imageFile, cssFile, htmlFile, supportedImageTypes, isSysoSpecified, isLocalResource, url);
    }

    protected void correctOptionRequirementsForSyso(final boolean isSysoSpecified) {
        if(isSysoSpecified) {
            this.optionHtmlFile.setRequired(false);
            this.optionCssFile.setRequired(false);

            ((List<String>)this.options.getRequiredOptions()).removeIf(s -> this.equalsEitherHtmlOrCss(s));
        }

    }

    protected boolean isSysoSpecified(final String[] args) {
        final String dashSyso = "-" + this.optionSyso.getOpt();

        for(final String arg : args) {
            if(arg.equalsIgnoreCase(dashSyso)) {
                return true;
            }else {
                continue;
            }
        }

        return false;
    }

    protected boolean equalsEitherHtmlOrCss(final String optionValue) {
        return optionValue.equalsIgnoreCase(this.optionHtmlFile.getOpt()) || optionValue.equalsIgnoreCase(this.optionCssFile.getOpt());
    }

    protected boolean isALocalResource(final CommandLine commandLine, final String option) {
        final String[] optionValues = commandLine.getOptionValues(option);
        if(null == optionValues) {
            return false;
        }else {
            final String filename = commandLine.getOptionValues(option)[0];
            final Matcher matcher = patternHttp.matcher(filename);

            return !matcher.find();
        }
    }

    protected File extractFileFromOption(final CommandLine commandLine, final String option) {
        final String[] optionValues = commandLine.getOptionValues(option);
        if(optionValues == null || optionValues.length == 0) {
            return null;
        }

        final String filename = optionValues[0];
        final File file = this.objectFactory.newFile(filename);

        return file;
    }

    protected URL extractURLFromOption(final CommandLine commandLine, final String option) {
        final String[] optionValues = commandLine.getOptionValues(option);
        if(null == optionValues) {
            return null;
        }else {
            final String urlValue = commandLine.getOptionValues(option)[0];
            try {
                return new URL(urlValue);
            }catch(final MalformedURLException e) {
                throw this.objectFactory.newImage2CssException(e, Error.errorParsingUrlParameter);
            }
        }
    }

    protected String[] determineIncludedImageTypes(final String[] optionValues) {
        if(null == optionValues || 0 == optionValues.length) {
            return new String[] {
                SupportedImageType.gif.name(),
                SupportedImageType.jpg.name(),
                SupportedImageType.png.name(),
            };
        }else {
            return optionValues;
        }
    }

    protected Set<SupportedImageType> extractImageTypesFromOption(final CommandLine commandLine, final String option) {
        final Set<SupportedImageType> result = this.objectFactory.newMutableSet();
        final String[] optionValues = this.determineIncludedImageTypes(commandLine.getOptionValues(option));

        for(final String optionValue : optionValues) {
            final SupportedImageType supportedImageType = this.convertStringImageTypeArgumentToEnumType(optionValue);
            result.add(supportedImageType);
        }

        return result;
    }

    protected SupportedImageType convertStringImageTypeArgumentToEnumType(final String supportedImageTypeArg) {
        try {
            return SupportedImageType.valueOf(supportedImageTypeArg.toLowerCase());
        }catch(final IllegalArgumentException illegalArgumentException) {
            throw this.objectFactory.newImage2CssValidationException(Error.parameterUnsupportedImageType);
        }
    }

}