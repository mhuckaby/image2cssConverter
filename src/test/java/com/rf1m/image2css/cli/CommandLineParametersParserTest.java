package com.rf1m.image2css.cli;

import com.rf1m.image2css.domain.BeanType;
import com.rf1m.image2css.domain.ObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Set;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.Mock;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineParametersParserTest {
    final String paramF = "-f";
    final String paramFValue = "./src/test/resources/";

    final String paramH = "-h";
    final String paramHValue = "demo.html";

    final String paramI = "-i";
    final String paramIValuePng = "png";
    final String paramIValueGif = "gif";
    final String paramIValueJpg = "jpg";

    final String paramO = "-o";
    final String paramOValue = "demo.css";

    final String[] paramsFullDirectory = {
        paramF, paramFValue,
        paramH, paramHValue,
        paramO, paramOValue,
    };

    final String[] paramsIncludeJpegOnly = {
        paramF, paramFValue,
        paramH, paramHValue,
        paramI, paramIValueJpg,
        paramO, paramOValue,
    };

    final String[] paramsIncludePngOnly = {
        paramF, paramFValue,
        paramH, paramHValue,
        paramI, paramIValuePng,
        paramO, paramOValue,
    };

    final String[] paramsIncludeGifOnly = {
        paramF, paramFValue,
        paramH, paramHValue,
        paramI, paramIValueGif,
        paramO, paramOValue,
    };

    final String[] paramsIncludeJpgPngOnly = {
        paramF, paramFValue,
        paramH, paramHValue,
        paramI, paramIValueJpg, paramIValuePng,
        paramO, paramOValue,
    };

    @Mock
    ObjectFactory objectFactory;

    @Mock
    Set<SupportedImageTypes> supportedImageTypes;

    @Mock
    MutableParameters mutableParameters;

    @Mock
    File paramFMarshaledValue;

    @Mock
    File paramHMarshaledValue;

    @Mock
    File paramOMarshaledValue;

    CommandLineParametersParser commandLineParametersParser;

    @Before
    public void before() {
        commandLineParametersParser = spy(new CommandLineParametersParser(objectFactory));

        when(objectFactory.instance(BeanType.mutableParameters))
            .thenReturn(mutableParameters);

        when(objectFactory.instance(BeanType.set))
            .thenReturn(supportedImageTypes);

        when(objectFactory.instance(BeanType.file, paramFValue))
            .thenReturn(paramFMarshaledValue);

        when(objectFactory.instance(BeanType.file, paramHValue))
            .thenReturn(paramHMarshaledValue);

        when(objectFactory.instance(BeanType.file, paramOValue))
            .thenReturn(paramOMarshaledValue);

    }

    @Test
    public void shouldReturnParamFValue() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 0);

        assertThat(result, is(paramFValue));
    }

    @Test
    public void shouldReturnParamH() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 1);

        assertThat(result, is(paramH));
    }

    @Test
    public void shouldReturnParamHValue() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 2);

        assertThat(result, is(paramHValue));
    }

    @Test
    public void shouldReturnParamO() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 3);

        assertThat(result, is(paramO));
    }

    @Test
    public void shouldReturnParamOValue() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 4);

        assertThat(result, is(paramOValue));
    }

    @Test
    public void shouldReturnNull() {
        final String result = commandLineParametersParser.nextParam(paramsFullDirectory, 5);

        assertNull(result);
    }

    @Test
    public void shouldRecognizeGifAndAddToResult() {
        final String extension = "gif";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.gif);
    }

    @Test
    public void shouldRecognizeCrookedCaseGifAndAddToResult() {
        final String extension = "GiF";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.gif);
    }

    @Test
    public void shouldRecognizeJpgAndAddToResult() {
        final String extension = "jpg";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.jpg);
    }

    @Test
    public void shouldRecognizeCrookedCaseJpgAndAddToResult() {
        final String extension = "jPg";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.jpg);
    }

    @Test
    public void shouldRecognizePngAndAddToResult() {
        final String extension = "png";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.png);
    }

    @Test
    public void shouldRecognizeCrookedCasePngAndAddToResult() {
        final String extension = "PnG";

        commandLineParametersParser.evaluateCurrentImageTypeParameter(supportedImageTypes, extension);

        verify(supportedImageTypes, times(1)).add(SupportedImageTypes.png);
    }

    @Test
    public void shouldDetermineTypesToSupportAreOfSizeOneLimitedToJpgBasedOnIncludeParameter() {
        final Set<SupportedImageTypes> result = commandLineParametersParser.determineSupportedTypes(paramsIncludeJpegOnly, 4);

        verify(commandLineParametersParser, times(1))
            .evaluateCurrentImageTypeParameter(supportedImageTypes, paramIValueJpg);

        verify(supportedImageTypes, times(1))
            .add(SupportedImageTypes.jpg);

        verify(supportedImageTypes, times(1))
            .isEmpty();
    }

    @Test
    public void shouldDetermineTypesToSupportAreOfSizeOneLimitedToPngBasedOnIncludeParameter() {
        final Set<SupportedImageTypes> result = commandLineParametersParser.determineSupportedTypes(paramsIncludePngOnly, 4);

        verify(commandLineParametersParser, times(1))
            .evaluateCurrentImageTypeParameter(supportedImageTypes, paramIValuePng);

        verify(supportedImageTypes, times(1))
            .add(SupportedImageTypes.png);

        verify(supportedImageTypes, times(1))
            .isEmpty();
    }

    @Test
    public void shouldDetermineTypesToSupportAreOfSizeOneLimitedToGifBasedOnIncludeParameter() {
        final Set<SupportedImageTypes> result = commandLineParametersParser.determineSupportedTypes(paramsIncludeGifOnly, 4);

        verify(commandLineParametersParser, times(1))
            .evaluateCurrentImageTypeParameter(supportedImageTypes, paramIValueGif);

        verify(supportedImageTypes, times(1))
            .add(SupportedImageTypes.gif);

        verify(supportedImageTypes, times(1))
            .isEmpty();
    }

    @Test
    public void shouldMarshalParamFValueToParamFMarshaledValue() {
        commandLineParametersParser.marshalStringParamToType(paramsIncludeJpegOnly, 0, mutableParameters);

        verify(mutableParameters, times(1))
            .setImageFile(paramFMarshaledValue);
    }

    @Test
    public void shouldMarshalParamHValueToParamHMarshaledValue() {
        commandLineParametersParser.marshalStringParamToType(paramsIncludeJpegOnly, 2, mutableParameters);

        verify(mutableParameters, times(1))
            .setHtmlFile(paramHMarshaledValue);
    }

    @Test
    public void shouldMarshalParamIValueToDeterminedSet() {
        Set<SupportedImageTypes> determinedSet = mock(Set.class);

        doReturn(determinedSet)
            .when(commandLineParametersParser)
            .determineSupportedTypes(any(String[].class), anyInt());

        commandLineParametersParser.marshalStringParamToType(paramsIncludeJpegOnly, 4, mutableParameters);

        verify(mutableParameters, times(1))
            .setSupportedTypes(determinedSet);
    }

    @Test
    public void shouldMarshalParamOValueToParamOMarshaledValue() {
        commandLineParametersParser.marshalStringParamToType(paramsIncludeJpegOnly, 6, mutableParameters);

        verify(mutableParameters, times(1))
            .setCssFile(paramOMarshaledValue);
    }

    @Test
    public void should() {
        Parameters immutableParameters = mock(Parameters.class);

        when(objectFactory.instance(any(BeanType.class), any(File.class), any(File.class), any(File.class), any(Set.class), anyBoolean()))
            .thenReturn(immutableParameters);

        doNothing()
            .when(commandLineParametersParser)
            .marshalStringParamToType(any(String[].class), anyInt(), any(MutableParameters.class));

        final Parameters result = commandLineParametersParser.parse(paramsIncludeJpegOnly);

        assertThat(result, is(immutableParameters));

        verify(mutableParameters, times(1))
            .getImageFile();

        verify(mutableParameters, times(1))
            .getCssFile();

        verify(mutableParameters, times(1))
            .getHtmlFile();

        verify(mutableParameters, times(1))
            .getSupportedTypes();

        verify(mutableParameters, times(1))
            .isOutputToScreen();
    }

}




