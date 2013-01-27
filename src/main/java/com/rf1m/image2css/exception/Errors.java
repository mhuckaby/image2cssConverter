package com.rf1m.image2css.exception;

public enum Errors {
    parametersCollectionCannotBeNullOrEmpty("Parameters cannot be null or empty"),
    parametersObjectCannotBeNull("Parameters object cannot be null"),
    parametersObjectMustHaveValidImageInputFileOrDir("Image input file or directory must be specified"),
    parametersObjectOutputInvalid("Image input file or directory not found"),
    parametersObjectImageInputFileOrDirNotExists("A valid output to file or screen must be specified for CSS"),
    parameterCannotBeNullOrEmptyOrBlank("Parameter cannot be null, empty, or blank"),
    parameterBeanType("Unknown bean type, %1$s"),

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
