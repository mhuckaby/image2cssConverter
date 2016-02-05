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
package com.rf1m.image2css.service

import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.domain.HeadResponse
import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.exception.Image2CssException
import com.rf1m.image2css.exception.Image2CssValidationException
import com.rf1m.image2css.io.ConversionFilenameFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.net.ssl.HttpsURLConnection
import javax.swing.ImageIcon

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH
import static com.google.common.net.HttpHeaders.CONTENT_TYPE
import static com.rf1m.image2css.domain.SupportedImageType.byContentType
import static com.rf1m.image2css.domain.SupportedImageType.isUnsupportedImageType
import static com.rf1m.image2css.exception.ServiceError.*
import static java.lang.String.format
import static org.apache.commons.codec.binary.Base64.encodeBase64
import static org.apache.commons.io.FilenameUtils.getExtension

@Service
class DefaultImageConversionService implements ImageConversionService {
    protected static final String UNDERSCORE = "_"
    protected static final String NL = "\n"
    protected static final String EMPTY = ""
    protected static final String GET = "GET"
    protected static final String HEAD = "HEAD"
    protected static final String FILENAME_REPLACE_PATTERN = "\\.|:|/|\\\\|\\W"
    // TODO define a better user agent
    protected static final String USER_AGENT = "User-Agent"
    protected static final String JAVA_CLIENT = "java-client"

    @Value('${template.css.class.def}')
    protected final String cssClassTemplate


    @Override
    public List<CssClass> convert(final Collection<SupportedImageType> include, final File imageFile) {
        List<CssClass> result = []
        if(!imageFile || !imageFile.exists()) {
            throw new Image2CssValidationException(parameterFileMustBeNonNullAndNonDirectory)
        }

        if(imageFile.directory) {
            ConversionFilenameFilter conversionFilenameFilter = new ConversionFilenameFilter(include ? include : SupportedImageType.values())
            List<File> files = imageFile.listFiles(conversionFilenameFilter) as List
            result.addAll(files.collect({
                processImageFile(it)
            }))
        }else {
            result.add(processImageFile(imageFile))
        }

        result
    }

    protected CssClass processImageFile(final File imageFile) {
        String extension = getExtension(imageFile.name)

        if(isUnsupportedImageType(extension)){
            throw new Image2CssValidationException(parameterUnsupportedImageType)
        }

        try {
            bytesToCssClass(imageFile.readBytes(), imageFile.name, extension)
        }catch(final IOException ioException) {
            throw new Image2CssException(ioException, errorReadingFile)
        }
    }

    @Override
    public CssClass convert(final URL url) {
        if(!url || !url.file || !(~/http[s]{0,1}/).matcher(url.protocol).matches()) {
            throw new Image2CssValidationException(parameterUrlCannotBeEmpty)
        }
        HeadResponse headResponse = head(url)

        SupportedImageType supportedImageType = byContentType(headResponse.contentType)
        if(!supportedImageType){
            throw new Image2CssValidationException(parameterUnsupportedImageType)
        }

        try {
            def httpConnection = (~/http/).matcher(url.protocol) ?
                (HttpURLConnection)url.openConnection() : (HttpsURLConnection)url.openConnection()
            httpConnection.requestMethod = GET
            httpConnection.addRequestProperty(USER_AGENT, JAVA_CLIENT)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            httpConnection.inputStream.eachByte { byteArrayOutputStream.write(it) }
            bytesToCssClass(byteArrayOutputStream.toByteArray(), headResponse.urlFile, supportedImageType.toString())
        }catch(IOException e) {
            throw new Image2CssException(e, errorRetrievingRemoteResource)
        }
    }

    @Override
    public CssClass convert(final String urlValue) {
        if(!urlValue) {
            throw new Image2CssValidationException(parameterUrlCannotBeEmpty)
        }

        URL url = {
            try {
                new URL(urlValue)
            }catch(MalformedURLException e) {
                throw new Image2CssValidationException(e, errorCreatingUrlFromStringValue)
            }
        }()

        this.convert(url)
    }

    protected HeadResponse head(final URL url) {
        def httpConnection = (~/http/).matcher(url.protocol) ?
                (HttpURLConnection)url.openConnection() : (HttpsURLConnection)url.openConnection()

        httpConnection.requestMethod = HEAD
        httpConnection.addRequestProperty(USER_AGENT, JAVA_CLIENT)

        String contentType = httpConnection.getHeaderField(CONTENT_TYPE)
        String contentLength = httpConnection.getHeaderField(CONTENT_LENGTH)

        new HeadResponse([urlFile: url.file, contentType: contentType, contentLength: contentLength ? contentLength.toInteger() : null])
    }

    protected CssClass bytesToCssClass(final byte[] bytes, final String filename, final String extension) {
        ImageIcon icon = new ImageIcon(bytes)
        String cssClassName = filename.replaceAll(FILENAME_REPLACE_PATTERN, UNDERSCORE)
        String b64Bytes = new String(encodeBase64(bytes, false)).replaceAll(NL, EMPTY)
        String cssEntry = format(cssClassTemplate, cssClassName, extension, b64Bytes, icon.iconWidth, icon.iconHeight)

        new CssClass(cssClassName, cssEntry)
    }

}