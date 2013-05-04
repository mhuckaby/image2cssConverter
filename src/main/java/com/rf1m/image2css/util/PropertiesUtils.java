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
package com.rf1m.image2css.util;

import com.rf1m.image2css.domain.BeanType;
import com.rf1m.image2css.domain.ObjectFactory;
import com.rf1m.image2css.exception.Image2CssValidationException;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static com.rf1m.image2css.exception.Errors.parameterCannotBeNullOrEmptyOrBlank;
import static com.rf1m.image2css.exception.Errors.parametersCollectionCannotBeNullOrEmpty;
import static java.lang.String.format;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class PropertiesUtils {
    protected static final String PROPERTIES_FILE = "image2css.properties";

    protected final Properties properties;
    protected final ObjectFactory objectFactory;

    public PropertiesUtils(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.properties = loadPropertiesFile(PROPERTIES_FILE);
    }

    public String getProperty(final String parameter){
        return getProperties(parameter)[0];
    }

    public String[] getProperties(final String ... parameters){
		if(isNotEmpty(parameters)){
            final String[] result = this.objectFactory.instance(BeanType.stringArray, parameters.length);

            for(int i=0;i<parameters.length;i++){
                if(isBlank(parameters[i])){
                    throw new Image2CssValidationException(parameterCannotBeNullOrEmptyOrBlank);
                }else{
                    result[i] = properties.getProperty(parameters[i]);
                }
            }
            return result;
		}else {
            throw new Image2CssValidationException(parametersCollectionCannotBeNullOrEmpty);
        }
	}

    protected Properties loadPropertiesFile(final String propertiesFile) {
        try{
            final URL url = this.objectFactory.instance(BeanType.url, propertiesFile);
            final Properties properties = this.objectFactory.instance(BeanType.properties);

            properties.load(url.openStream());

            return properties;
        }catch(IOException ioException){
            final String exceptionMessage = format("unable to read properties file, \"%1$s\"", PROPERTIES_FILE);
            throw new RuntimeException(exceptionMessage, ioException);
        }
    }

}