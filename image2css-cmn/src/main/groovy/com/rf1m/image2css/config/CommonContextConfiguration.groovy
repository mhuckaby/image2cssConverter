/**
 *
 * Copyright (c) 2011-2015 Matthew D Huckaby. All rights reservered.
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
package com.rf1m.image2css.config

import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.service.DefaultImageConversionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

import static com.rf1m.image2css.domain.SupportedImageType.gif
import static com.rf1m.image2css.domain.SupportedImageType.jpg
import static com.rf1m.image2css.domain.SupportedImageType.png

import static java.util.Collections.unmodifiableSet

@Configuration()
@PropertySource(value = ["classpath:/image2css-cmn.properties", "classpath:/image2css-exception-messages.properties"])
class CommonContextConfiguration {

    public static final Set<SupportedImageType> defaultSupportedImageTypes =
        unmodifiableSet(new HashSet([gif, jpg, png]))

    @Autowired
    Environment environment

    @Bean
    DefaultImageConversionService defaultImageConversionService() {
        return new DefaultImageConversionService(environment.getProperty("template.css.class.def"))
    }
}

