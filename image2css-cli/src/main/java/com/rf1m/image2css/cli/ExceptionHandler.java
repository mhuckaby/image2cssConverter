package com.rf1m.image2css.cli;

import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.ioc.BeanType;
import com.rf1m.image2css.cmn.ioc.ObjectFactory;
import org.apache.commons.cli.ParseException;

import java.io.PrintStream;
import java.util.ResourceBundle;

public class ExceptionHandler {
    protected final ObjectFactory objectFactory;
    protected final PrintStream printStream;
    protected final ResourceBundle resourceBundle;


    public ExceptionHandler(final ObjectFactory objectFactory, final PrintStream printStream, final ResourceBundle resourceBundle) {
        this.objectFactory = objectFactory;
        this.printStream = printStream;
        this.resourceBundle = resourceBundle;
    }

    protected void handleException(final Exception e) {
        final String issueUrl = this.getString("issue.url");
        final String messageTemplate = this.getString("message.abnormal.exit");
        final String formattedMessage = this.format(messageTemplate, e.getMessage(), issueUrl);

        printStream.println(formattedMessage);
        e.printStackTrace();
    }

    protected void handleImage2CssException(final Image2CssException image2CssException) {
        final String exceptionFormat = this.getString("format.exception");
        final String formattedExceptionMessage = this.format(exceptionFormat, image2CssException.getMessage());

        printStream.println(formattedExceptionMessage);
    }

    protected void handleParseException(final ParseException parseException) {
        final Image2CssHelpFormatter image2CssHelpFormatter = this.objectFactory.instance(BeanType.helpFormatter);
        final String exceptionFormat = this.getString("format.exception");
        final String formattedExceptionMessage = this.format(exceptionFormat, parseException.getMessage());

        printStream.println(formattedExceptionMessage);
        image2CssHelpFormatter.showHelp();
    }

    protected String format(final String template, final String ... param) {
        return String.format(template, param);
    }

    protected String getString(final String key) {
        return this.resourceBundle.getString(key);
    }

}
