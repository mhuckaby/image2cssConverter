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
package com.rf1m.image2css.web.controller

import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.domain.ErrorResponse
import com.rf1m.image2css.exception.Image2CssException
import com.rf1m.image2css.exception.Image2CssValidationException
import com.rf1m.image2css.service.ImageConversionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
class ConversionController {
    protected static final Logger logger = LoggerFactory.getLogger(ConversionController.class)

    @Autowired
    ImageConversionService imageConversionService

    @RequestMapping(value = "/convert", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    CssClass convertUrl(@RequestParam final String url) {
        logger.info("convertUrl=" + url)
        return this.imageConversionService.convert(url)
    }

    @ExceptionHandler(Image2CssValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleBadRequest(final Image2CssValidationException image2CssValidationException) {
        return new ErrorResponse(image2CssValidationException.getMessage())
    }

    @ExceptionHandler(value = [Image2CssException.class, Exception.class])
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleInternalServerError(final Image2CssException image2cssException) {
        return new ErrorResponse(image2cssException.getMessage())
    }


}
