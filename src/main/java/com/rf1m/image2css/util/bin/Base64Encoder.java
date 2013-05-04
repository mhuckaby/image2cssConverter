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
package com.rf1m.image2css.util.bin;

import com.rf1m.image2css.domain.BeanType;
import com.rf1m.image2css.domain.ObjectFactory;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

// Limits JVM to Sun/Oracle distribution
//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Base64Encoder {
    protected final static String NL 		= "\n";
    protected final static String EMPTY 	= "";

    protected final ObjectFactory objectFactory;

    public Base64Encoder(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public String base64EncodeBytes(final byte[] bytes) {
        final String encoded = this.objectFactory.instance(BeanType.string, encodeBase64(bytes, false));
        return encoded.replaceAll(NL, EMPTY);
    }

}
