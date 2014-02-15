package com.rf1m.image2css.cmn.exception;

public enum Errors {
    parametersObjectCannotBeNull("parameters.object.cannot.be.null"),
    parametersObjectMustHaveValidImageInputFileOrDir("parameters.object.must.have.valid.image.input.file.or.dir"),
    parametersObjectOutputInvalid("parameters.object.output.invalid"),
    parametersObjectImageInputFileOrDirNotExists("parameters.object.image.input.file.or.dir.not.exists"),
    parameterHtmlIndexWithNoCssFile("parameters.html.index.with.no.css.file"),

    parameterCssClassCollectionIsNull("parameters.css.class.collection.is.null"),
    parameterUnsupportedImageType("parameters.unsupported.imagetype"),

    fileNotFound("file.not.found"),

    errorReadingFile("error.reading.file"),
    errorClosingFile("error.closing.file"),
    errorCreatingFileWriter("error.creating.file.writer"),
    errorOpeningStream("error.opening.stream"),
    errorCreatingUrlFromStringValue("error.creating.url.from.string.value");

    protected final String message;

    Errors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
