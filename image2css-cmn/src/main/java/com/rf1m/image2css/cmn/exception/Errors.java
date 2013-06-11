package com.rf1m.image2css.cmn.exception;

public enum Errors {
    parametersObjectCannotBeNull("Parameters object cannot be null"),
    parametersObjectMustHaveValidImageInputFileOrDir("Image input file or directory must be specified"),
    parametersObjectOutputInvalid("At least one output type required: CSS file to write or output to screen"),
    parametersObjectImageInputFileOrDirNotExists("A valid output to file or screen must be specified for CSS"),
    parameterBeanType("Unknown bean type, %1$s"),
    parameterHtmlIndexWithNoCssFile("CSS file output is required to produce HTML index"),

    parameterCssClassCollectionIsNull("CSS classes collection is null"),
    parameterUnsupportedImageType("Unsupported image type specified"),

    fileNotFound("File not found"),


    failureToOutput("Failure occurred during output"),

    errorReadingFile("Error while reading file"),
    errorClosingFile("Error while closing file");

    protected final String message;

    Errors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
