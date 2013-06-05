package com.rf1m.image2css.cli;

import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.exception.Image2CssValidationException;

import static com.rf1m.image2css.exception.Errors.*;

public class CommandLineRunnerValidator {

    protected final Image2CssHelpFormatter image2CssHelpFormatter;
    protected final SystemWrapper systemWrapper;

    public CommandLineRunnerValidator(final Image2CssHelpFormatter image2CssHelpFormatter, final SystemWrapper systemWrapper) {
        this.image2CssHelpFormatter = image2CssHelpFormatter;
        this.systemWrapper = systemWrapper;
    }

    protected void argumentLengthCheck(final String[] arguments) {
        if(0 == arguments.length){
            this.image2CssHelpFormatter.showHelp();
            this.systemWrapper.exit();
        }
    }

    public void validateParameters(Parameters parameters) throws Image2CssException {
        if(null == parameters){
            throw new Image2CssValidationException(parametersObjectCannotBeNull) ;
        }else if(null == parameters.getImageFile()){
            throw new Image2CssValidationException(parametersObjectMustHaveValidImageInputFileOrDir);
        }else if(!parameters.getImageFile().exists()){
            throw new Image2CssValidationException(parametersObjectImageInputFileOrDirNotExists);
        }else if(!parameters.isOutputValid()){
            throw new Image2CssValidationException(parametersObjectOutputInvalid);
        }else if(parameters.isHtmlFileOutputDesired() && !parameters.isCssFileOutputDesired()) {
            throw new Image2CssValidationException(parameterHtmlIndexWithNoCssFile);
        }
    }

}
