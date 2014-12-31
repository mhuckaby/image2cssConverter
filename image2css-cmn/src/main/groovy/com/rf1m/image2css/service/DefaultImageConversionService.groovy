package com.rf1m.image2css.service

import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.exception.Error
import com.rf1m.image2css.ioc.CommonObjectFactory

import javax.net.ssl.HttpsURLConnection
import javax.swing.ImageIcon

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH
import static com.google.common.net.HttpHeaders.CONTENT_TYPE
import static com.rf1m.image2css.domain.SupportedImageType.byContentType
import static com.rf1m.image2css.domain.SupportedImageType.isUnsupportedImageType
import static Error.*
import static java.lang.String.format
import static org.apache.commons.codec.binary.Base64.encodeBase64
import static org.apache.commons.io.FilenameUtils.getExtension

class DefaultImageConversionService implements ImageConversionService {
    protected static final String UNDERSCORE = "_"
    protected static final String http = "http://"
    protected static final String NL = "\n"
    protected static final String EMPTY = ""
    protected static final String GET = "GET"
    protected static final String HEAD = "HEAD"
    protected static final String FILENAME_REPLACE_PATTERN = "\\.|:|/|\\\\|\\W"
    // TODO define a better user agent
    protected static final String USER_AGENT = "User-Agent"
    protected static final String JAVA_CLIENT = "java-client"

    protected final CommonObjectFactory commonObjectFactory
    protected final String cssClassTemplate

    public DefaultImageConversionService(final CommonObjectFactory commonObjectFactory, final String cssClassTemplate) {
        this.commonObjectFactory = commonObjectFactory
        this.cssClassTemplate = cssClassTemplate
    }

    @Override
    public CssClass convert(final File imageFile) {
        if(!imageFile || !imageFile.exists() || imageFile.directory) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterFileMustBeNonNullAndNonDirectory)
        }

        String extension = getExtension(imageFile)

        if(isUnsupportedImageType(extension)){
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUnsupportedImageType)
        }

        try {
            bytesToCssClass(imageFile.readBytes(), imageFile.name, extension)
        }catch(final IOException ioException) {
            throw this.commonObjectFactory.newImage2CssException(ioException, Error.errorReadingFile)
        }
    }

    @Override
    public CssClass convert(final URL url) {
        if(!url || !url.file || !(~/http[s]{0,1}/).matcher(url.protocol).matches()) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUrlCannotBeEmpty)
        }
        HeadResponse headResponse = head(url)

        SupportedImageType supportedImageType = byContentType(headResponse.contentType)
        if(!supportedImageType){
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUnsupportedImageType)
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
            throw this.commonObjectFactory.newImage2CssException(e, errorRetrievingRemoteResource)
        }
    }

    class HeadResponse {
        String urlFile
        String contentType
        Integer contentLength
    }

    public HeadResponse head(final URL url) {
        def httpConnection = (~/http/).matcher(url.protocol) ?
                (HttpURLConnection)url.openConnection() : (HttpsURLConnection)url.openConnection()

        httpConnection.requestMethod = HEAD
        httpConnection.addRequestProperty(USER_AGENT, JAVA_CLIENT)

        String contentType = httpConnection.getHeaderField(CONTENT_TYPE)
        String contentLength = httpConnection.getHeaderField(CONTENT_LENGTH)

        new HeadResponse([urlFile: url.file, contentType: contentType, contentLength: contentLength ? contentLength.toInteger() : null])
    }

    @Override
    public CssClass convert(final String urlValue) {
        if(!urlValue) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUrlCannotBeEmpty)
        }

        URL url = {
            try {
                new URL(urlValue)
            }catch(MalformedURLException e) {
                throw this.commonObjectFactory.newImage2CssValidationException(e, errorCreatingUrlFromStringValue)
            }
        }()

        this.convert(url)
    }

    protected CssClass bytesToCssClass(final byte[] bytes, final String filename, final String extension) {
        ImageIcon icon = new ImageIcon(bytes)
        String cssClassName = filename.replaceAll(FILENAME_REPLACE_PATTERN, UNDERSCORE)
        String b64Bytes = new String(encodeBase64(bytes, false)).replaceAll(NL, EMPTY)
        String cssEntry = format(cssClassTemplate, cssClassName, extension, b64Bytes, icon.iconWidth, icon.iconHeight)

        new CssClass(cssClassName, cssEntry)
    }

}