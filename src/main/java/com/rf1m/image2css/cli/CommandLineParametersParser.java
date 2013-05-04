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

import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import com.rf1m.image2css.exception.Image2CssValidationException;

import java.io.File;
import java.util.Set;

import static com.rf1m.image2css.cli.SupportedImageTypes.*;
import static com.rf1m.image2css.exception.Errors.cmdArgumentIRequiresFileTypes;
import static java.util.Arrays.copyOfRange;

public class CommandLineParametersParser {
    protected static final String PARAM_F 	= "-f";
    protected static final String PARAM_H 	= "-h";
    protected static final String PARAM_I 	= "-i";
    protected static final String PARAM_O 	= "-o";
    protected static final String PARAM_SYSO 	= "-syso";

    protected static final String EXAMPLE1 	= "./image2CSS.sh -f ./ -h demo.html -o demo.css";
    protected static final String EXAMPLE2 	= "./image2CSS.sh -f ./ -o demo.css -i png";
    protected static final String EXAMPLE3 	= "./image2CSS.sh -f ./image.png -syso";

    public static final String HELP =
            "\nUsage: Img2CSS [OPTIONS]\n"
            +	PARAM_F + " 	: image-file or directory containing images\n"
            +	PARAM_H + " 	: generate HTML index for CSS classes\n"
            +	PARAM_I + " 	: {png gif jpg} types to include when path is a directory (default all)\n"
            +	PARAM_O + " 	: CSS-file to be output\n"
            +	PARAM_SYSO + " 	: output CSS to screen\n"
            +	"\nExamples\n"
            + 	EXAMPLE1 + "\n"
            + 	EXAMPLE2 + "\n"
            + 	EXAMPLE3 + "\n";

    protected final ObjectFactory objectFactory;

    public CommandLineParametersParser(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Parameters parse(final String[] args){
        final MutableParameters mutableParameters = objectFactory.instance(BeanType.mutableParameters);

        for(int i=0;i<args.length;i++){
            marshalStringParamToType(args, i, mutableParameters);
        }

        return objectFactory.instance(
            BeanType.immutableParameters,
            mutableParameters.getImageFile(),
            mutableParameters.getCssFile(),
            mutableParameters.getHtmlFile(),
            mutableParameters.getSupportedTypes(),
            mutableParameters.isOutputToScreen()
        );
    }

    protected void marshalStringParamToType(final String[] args, final int currentIndex, final MutableParameters mutableParameters) {
        final String param = args[currentIndex];
        final String nextParam = nextParam(args, currentIndex);

        if(PARAM_F.equalsIgnoreCase(param)){
            final File imageFile = this.objectFactory.instance(BeanType.file, nextParam);
            mutableParameters.setImageFile(imageFile);
        }else if(PARAM_H.equalsIgnoreCase(param)){
            final File htmlFile = this.objectFactory.instance(BeanType.file, nextParam);
            mutableParameters.setHtmlFile(htmlFile);
        }else if(PARAM_I.equalsIgnoreCase(param)){
            final Set<SupportedImageTypes> supportedTypes = determineSupportedTypes(args, currentIndex);
            mutableParameters.setSupportedTypes(supportedTypes);
        }else if(PARAM_O.equalsIgnoreCase(param)){
            final File cssFile = this.objectFactory.instance(BeanType.file, nextParam);
            mutableParameters.setCssFile(cssFile);
        }else if(PARAM_SYSO.equalsIgnoreCase(param)){
            mutableParameters.setOutputToScreen(true);
        }
    }

    protected String nextParam(final String[] args, final int currentIndex) {
        final int correctedForZeroLength = args.length - 1;

        if(correctedForZeroLength - currentIndex > 0) {
            return args[currentIndex + 1];
        }else {
            return null;
        }
    }

    protected Set<SupportedImageTypes> determineSupportedTypes(final String[] args, final int currentIndex) {
        final Set<SupportedImageTypes> result = this.objectFactory.instance(BeanType.set);
        final String[] imageTypeParams = copyOfRange(args, currentIndex, args.length);

        for(final String imageTypeParam : imageTypeParams) {
            evaluateCurrentImageTypeParameter(result, imageTypeParam);
        }
        if(null == result || result.isEmpty()){
            throw new Image2CssValidationException(cmdArgumentIRequiresFileTypes);
        }else {
            return result;
        }
    }

    protected void evaluateCurrentImageTypeParameter(final Set<SupportedImageTypes> supportedImageTypes, final String imageType) {
        if(gif.toString().equalsIgnoreCase(imageType)){
            supportedImageTypes.add(gif);
        }else if(jpg.toString().equalsIgnoreCase(imageType)){
            supportedImageTypes.add(jpg);
        }else if(jpeg.toString().equalsIgnoreCase(imageType)){
            supportedImageTypes.add(jpeg);
        }else if(png.toString().equalsIgnoreCase(imageType)){
            supportedImageTypes.add(png);
        }
    }

}