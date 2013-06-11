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

import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssValidationException;
import com.rf1m.image2css.cmn.ioc.BeanType;
import com.rf1m.image2css.cmn.ioc.ObjectFactory;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Set;

public class CommandLineParametersParser {
    protected final ObjectFactory objectFactory;

    public CommandLineParametersParser(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Parameters parse(final String[] args) throws ParseException {
        final BasicParser basicParser = objectFactory.instance(BeanType.basicParser);

        final Option optionCssFile = objectFactory.instance(BeanType.optionCssFile);
        final Option optionHtmlFile = objectFactory.instance(BeanType.optionHtmlFile);
        final Option optionImageFile = objectFactory.instance(BeanType.optionImageFile);
        final Option optionSupportedImageTypes = objectFactory.instance(BeanType.optionImageTypes);
        final Option optionSyso = objectFactory.instance(BeanType.optionSyso);

        final Options options = objectFactory.instance(BeanType.options);

        final CommandLine commandLine = basicParser.parse(options, args);

        final File cssFile = this.extractFileFromOption(commandLine, optionCssFile.getOpt());
        final File htmlFile = this.extractFileFromOption(commandLine, optionHtmlFile.getOpt());
        final File imageFile = this.extractFileFromOption(commandLine, optionImageFile.getOpt());
        final Set<SupportedImageType> supportedImageTypes =
            this.extractImageTypesFromOption(commandLine, optionSupportedImageTypes.getOpt());
        final boolean syso = commandLine.hasOption(optionSyso.getOpt());

        return objectFactory.instance(BeanType.immutableParameters, imageFile, cssFile, htmlFile, supportedImageTypes, syso);
    }

    protected File extractFileFromOption(final CommandLine commandLine, final String option) {
        final String filename = commandLine.getOptionValues(option)[0];
        final File file = this.objectFactory.instance(BeanType.file, filename);

        return file;
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
        final Set<SupportedImageType> result = this.objectFactory.instance(BeanType.set);
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
            throw new Image2CssValidationException(Errors.parameterUnsupportedImageType);
        }
    }

}