package com.rf1m.image2css.exception;

public enum Errors {
    parametersObjectCannotBeNull("Parameters object cannot be null"),
    parametersObjectMustHaveValidImageInputFileOrDir("Image input file or directory must be specified"),
    parametersObjectOutputInvalid("At least one output type required: CSS file to write or output to screen"),
    parametersObjectImageInputFileOrDirNotExists("A valid output to file or screen must be specified for CSS"),
    parameterBeanType("Unknown bean type, %1$s"),
    parameterHtmlIndex("No filename specified for HTML index"),
    parameterHtmlIndexWithNoCssFile("CSS file output is required to produce HTML index"),

    parameterCssClassCollectionIsNull("CSS classes collection is null"),

    fileNotFoundWhileCreatingFileInputStream("File not found"),

    cmdArgumentIRequiresFileTypes("-i requires file types be supplied"),

    failureToOutput("Failure occurred during output");

    protected final String message;

    Errors(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
