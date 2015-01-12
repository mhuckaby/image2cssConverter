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
package com.rf1m.image2css.exception

enum Error {
    parametersObjectCannotBeNull("parameters.object.cannot.be.null"),
    parametersObjectMustHaveValidImageInputFileOrDir("parameters.object.must.have.valid.image.input.file.or.dir"),
    parametersObjectOutputInvalid("parameters.object.output.invalid"),
    parametersObjectImageInputFileOrDirNotExists("parameters.object.image.input.file.or.dir.not.exists"),
    parametersUrlCannotBeNull("parameters.object.image.input.url"),
    parameterFileMustBeNonNullAndNonDirectory("parameter.file.must.be.nn.ndir"),
    parameterHtmlIndexWithNoCssFile("parameters.html.index.with.no.css.file"),
    parameterUrlCannotBeEmpty("parameters.url.cannot.be.empty"),
    parameterCssClassCollectionIsNull("parameters.css.class.collection.is.null"),
    parameterUnsupportedImageType("parameters.unsupported.imagetype"),

    errorWritingFile("error.writing.file"),
    errorReadingFile("error.reading.file"),
    errorCreatingFileWriter("error.creating.file.writer"),
    errorParsingUrlParameter("error.parsing.url.parameter"),
    errorRetrievingRemoteResource("error.retrieving.remote.resource"),
    errorCreatingUrlFromStringValue("error.creating.url.from.string.value")

    protected final String message

    Error(final String message) {
        this.message = message
    }

    String getMessage() {
        return this.message
    }

}
