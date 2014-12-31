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
package com.rf1m.image2css.domain

enum SupportedImageType {
	gif("image/gif"),
    jpg("image/jpg"),
    jpeg("image/jpeg"),
    png("image/png")

    private final String contentType

    SupportedImageType(final String contentType) {
        this.contentType = contentType
    }

    static boolean isUnsupportedImageType(final String value) {
        return !isSupportedImageType(value)
    }

    static boolean isSupportedImageType(final String value) {
        for(final SupportedImageType supportedImageType : values()) {
            if(supportedImageType.toString().equalsIgnoreCase(value)) {
                return true
            }else {
                continue
            }
        }
        return false
    }

    static SupportedImageType byContentType(final String value) {
        for(final SupportedImageType supportedImageType : values()) {
            if(supportedImageType.contentType.equalsIgnoreCase(value)) {
                return supportedImageType
            }else {
                continue
            }
        }
        return null
    }

}
