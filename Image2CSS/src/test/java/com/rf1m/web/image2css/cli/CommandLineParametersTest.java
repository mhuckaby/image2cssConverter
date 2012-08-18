package com.rf1m.web.image2css.cli;

import com.rf1m.web.image2css.cli.CommandLineParameters.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static com.rf1m.web.image2css.cli.CommandLineParameters.PARAM_F;
import static com.rf1m.web.image2css.cli.CommandLineParameters.PARAM_H;
import static com.rf1m.web.image2css.cli.CommandLineParameters.PARAM_I;
import static com.rf1m.web.image2css.cli.CommandLineParameters.PARAM_O;
import static com.rf1m.web.image2css.cli.CommandLineParameters.PARAM_SYSO;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineParametersTest {
    CommandLineParameters commandLineParameters;

    final String targetFile = "./";

    @Mock FileFactory fileFactory;
    @Mock File file;

    @Before
    public void before(){
        when(fileFactory.newFile(targetFile)).thenReturn(file);

        commandLineParameters = new CommandLineParameters();
        commandLineParameters.fileFactory = fileFactory;
    }

    @Test
    public void paramFShouldIndicateNextParamAsImageFile(){
        String[] args = {
            PARAM_F,
            targetFile
        };

        commandLineParameters.parse(args);

        verify(fileFactory).newFile(targetFile);

        assertThat(commandLineParameters.imageFile, is(file));
    }

    @Test
    public void paramHShouldIndicateNextParamAsHTMLFile(){
        String[] args = {
            PARAM_H,
                targetFile
        };

        commandLineParameters.parse(args);

        verify(fileFactory).newFile(targetFile);

        assertThat(commandLineParameters.htmlFile, is(file));
    }

    @Test
    public void paramIShouldIndicateTheStartOfImageTypes(){
        String[] args = {
            PARAM_I,
            SupportedImageTypes.gif.toString(),
            SupportedImageTypes.jpg.toString(),
            SupportedImageTypes.jpeg.toString(),
            SupportedImageTypes.png.toString(),
        };

        commandLineParameters.parse(args);

        verify(fileFactory, times(0)).newFile(targetFile);

        assertThat(commandLineParameters.supportedTypes.size(), is(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void paramIExpectsAtLeastOneImageType(){
        String[] args = {
            PARAM_I,
        };

        commandLineParameters.parse(args);
    }

    @Test
    public void paramOShouldIndicateNextParamAsCSSFile(){
        String[] args = {
            PARAM_O,
                targetFile
        };

        commandLineParameters.parse(args);

        verify(fileFactory).newFile(targetFile);

        assertThat(commandLineParameters.cssFile, is(file));
    }

    @Test
    public void paramSYSOShouldIndicateScreenOutput(){
        String[] args = {
            PARAM_SYSO,
        };

        commandLineParameters.parse(args);

        verify(fileFactory, times(0)).newFile(targetFile);

        assertTrue(commandLineParameters.outputToScreen);
    }
}