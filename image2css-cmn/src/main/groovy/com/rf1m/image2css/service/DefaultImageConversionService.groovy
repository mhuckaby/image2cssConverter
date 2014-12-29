package com.rf1m.image2css.service

import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.exception.Errors
import com.rf1m.image2css.ioc.CommonObjectFactory
import org.apache.commons.lang3.tuple.Pair

import javax.swing.ImageIcon

import static com.rf1m.image2css.domain.SupportedImageType.isUnsupportedImageType
import static com.rf1m.image2css.exception.Errors.*
import static java.lang.String.format
import static org.apache.commons.codec.binary.Base64.encodeBase64
import static org.apache.commons.io.FilenameUtils.getExtension
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang3.StringUtils.startsWith

class DefaultImageConversionService implements ImageConversionService {
    protected static final String UNDERSCORE = "_"
    protected static final String http = "http://"
    protected static final String NL = "\n"
    protected static final String EMPTY = ""
    protected static final String GET = "GET"
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

        def extension = getExtension(imageFile)

        if(isUnsupportedImageType(extension)){
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUnsupportedImageType)
        }

        byte[] bytes = this.getFileBytes(imageFile)
        bytesToCssClass(bytes, imageFile.name, extension)
    }

    @Override
    public CssClass convert(final URL url) {
        if(!url) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUrlCannotBeEmpty)
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection()
            httpURLConnection.setRequestMethod(GET)
            // TODO define a better user agent
            httpURLConnection.addRequestProperty(USER_AGENT, JAVA_CLIENT)
            // TODO Get the filename from head request
            Pair<String, String> validatedFilenameAndExtension = this.validateFilenameAndExtension(url)
            BufferedInputStream inputStream = this.commonObjectFactory.newBufferedInputStream(httpURLConnection)
            byte[] bytes = this.readInputStreamToBytes(inputStream)
            bytesToCssClass(bytes, validatedFilenameAndExtension.left, validatedFilenameAndExtension.right)
        }catch(IOException e) {
            throw this.commonObjectFactory.newImage2CssException(e, errorRetrievingRemoteResource)
        }
    }

    protected CssClass bytesToCssClass(final byte[] bytes, final String filename, final String extension) {
        Pair<Integer, Integer> dimension = {
            this.commonObjectFactory.newPair(it.iconWidth, it.iconHeight)
        }(new ImageIcon(bytes))

        String cssClassName = this.determineCssClassName(filename)
        String cssEntry = determineCssEntry(cssClassName, extension, base64EncodeBytes(bytes), dimension)

        return new CssClass(cssClassName, cssEntry)
    }

    @Override
    public CssClass convertUrlAsString(final String urlAsString) {
        if(!urlAsString) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUrlCannotBeEmpty)
        }

        String urlStringWithProtocol = startsWith(urlAsString.toLowerCase(), http) ?
            urlAsString : this.commonObjectFactory.newStringBuilder(http).append(urlAsString).toString()

        URL url = this.commonObjectFactory.newUrl(urlStringWithProtocol)

        return this.convert(url)
    }

    public String base64EncodeBytes(final byte[] bytes) {
        new String(encodeBase64(bytes, false)).replaceAll(NL, EMPTY)
    }

    protected String determineCssEntry(final String cssClassName,
                                       final String fileExtension,
                                       final String base64Bytes,
                                       final Pair<Integer, Integer> dimension) {

        format(cssClassTemplate, cssClassName, fileExtension, base64Bytes, dimension.left, dimension.right)
    }

    protected String determineCssClassName(final String fileName) {
        String candidate =
            fileName.replaceAll("\\.", UNDERSCORE).replaceAll("\\\\", UNDERSCORE).replaceAll("/", UNDERSCORE)
        return candidate ? candidate : randomAlphabetic(7)
    }

    /**
     * Read the bytes of a file into a byte array and return the array.
     * @param file
     * @return
     * @throws Exception
     */
    protected byte[] getFileBytes(final File file) {
        FileInputStream fileInputStream = this.commonObjectFactory.newFileInputStream(file)
        byte[] bytes = this.commonObjectFactory.newByteArray(file.length())

        try {
            fileInputStream.read(bytes)
        }catch(final IOException ioException) {
            throw this.commonObjectFactory.newImage2CssException(ioException, Errors.errorReadingFile)
        }finally {
            try {
                fileInputStream.close()
            }catch(Exception exception) {
                throw this.commonObjectFactory.newImage2CssException(exception, Errors.errorClosingFile)
            }
        }

        return bytes
    }

    protected Pair<String, String> validateFilenameAndExtension(final URL url) {
        final String imageFilename = url.file

        if(!imageFilename) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterCannotDetermineFilenameFromUrl)
        }

        final String fileExtension = getExtension(imageFilename)

        if(!fileExtension) {
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUrlCannotBeEmpty)
        }else if(isUnsupportedImageType(fileExtension)){
            // TODO this will fail if the url does not indicate file type ; use HEAD request to determine file type
            // in case of unsupported here
            throw this.commonObjectFactory.newImage2CssValidationException(parameterUnsupportedImageType)
        }

        this.commonObjectFactory.newPair(imageFilename, fileExtension)
    }

    protected byte[] readInputStreamToBytes(final BufferedInputStream bufferedInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = this.commonObjectFactory.newByteArrayOutputStream()
        byte[] buffer = this.commonObjectFactory.newByteArray(1)

        try {
            int len
            while((len = bufferedInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len)
            }
            return byteArrayOutputStream.toByteArray()
        }catch(IOException e) {
            throw this.commonObjectFactory.newImage2CssException(e, errorOpeningStream)
        }
    }

}
