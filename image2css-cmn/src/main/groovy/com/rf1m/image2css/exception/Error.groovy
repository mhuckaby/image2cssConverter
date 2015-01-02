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
