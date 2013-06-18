package com.rf1m.image2css.cli;

import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Image2CssValidationException;
import com.rf1m.image2css.cmn.ioc.BeanType;
import com.rf1m.image2css.ioc.CliBeanType;
import com.rf1m.image2css.ioc.CliObjectFactory;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.Mock;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineParametersParserTest {
    @Mock
    CliObjectFactory objectFactory;

    @Mock
    BasicParser basicParser;

    @Mock
    Option optionCssFile;

    @Mock
    Option optionHtmlFile;

    @Mock
    Option optionImageFile;

    @Mock
    Option optionSupportedImageTypes;

    @Mock
    Option optionSyso;

    @Mock
    Options options;


    @Mock
    CommandLine commandLine;

    CommandLineParametersParser commandLineParametersParser;

    @Before
    public void before() {
        commandLineParametersParser =
            spy(new CommandLineParametersParser(basicParser, optionCssFile, optionHtmlFile, optionImageFile, optionSupportedImageTypes,
                    optionSyso, options, objectFactory));
    }

    @Test
    public void parseShouldDelegateArgumentParsingToCommandLineParser() throws Exception {
        final String optionSysoGetOptValue = "optionSysoGetOptValue";
        final String optionCssFileGetOptValue = "optionCssFileGetOptValue";
        final String optionHtmlFileGetOptValue = "optionHtmlFileGetOptValue";
        final String optionImageFileGetOptValue = "optionImageFileGetOptValue";
        final String optionSupportImageTypesGetOptValue = "optionSupportImageTypesGetOptValue";

        final String[] args = {};

        final boolean syso = false;

        File cssFile = mock(File.class);
        File htmlFile = mock(File.class);
        File imageFile = mock(File.class);

        Set<SupportedImageType> supportedImageTypes = mock(Set.class);

        Parameters parameters = mock(Parameters.class);

        when(basicParser.parse(options, args))
            .thenReturn(commandLine);

        when(optionCssFile.getOpt())
            .thenReturn(optionCssFileGetOptValue);

        doReturn(cssFile)
            .when(commandLineParametersParser)
            .extractFileFromOption(commandLine, optionCssFileGetOptValue);

        when(optionHtmlFile.getOpt())
            .thenReturn(optionHtmlFileGetOptValue);

        doReturn(htmlFile)
            .when(commandLineParametersParser)
            .extractFileFromOption(commandLine, optionHtmlFileGetOptValue);

        when(optionImageFile.getOpt())
            .thenReturn(optionImageFileGetOptValue);

        doReturn(imageFile)
            .when(commandLineParametersParser)
            .extractFileFromOption(commandLine, optionImageFileGetOptValue);

        when(optionSupportedImageTypes.getOpt())
            .thenReturn(optionSupportImageTypesGetOptValue);

        doReturn(supportedImageTypes)
            .when(commandLineParametersParser)
            .extractImageTypesFromOption(commandLine, optionSupportImageTypesGetOptValue);

        when(optionSyso.getOpt())
            .thenReturn(optionSysoGetOptValue);

        when(commandLine.hasOption(optionSysoGetOptValue))
            .thenReturn(syso);

        when(objectFactory.getInstance(CliBeanType.immutableParameters, imageFile, cssFile, htmlFile, supportedImageTypes, syso))
            .thenReturn(parameters);

        final Parameters result = commandLineParametersParser.parse(args);

        assertThat(result, is(parameters));

        verify(basicParser, times(1))
            .parse(options, args);

        verify(optionCssFile, times(1))
            .getOpt();

        verify(commandLineParametersParser, times(1))
            .extractFileFromOption(commandLine, optionCssFileGetOptValue);

        verify(optionHtmlFile, times(1))
            .getOpt();

        verify(commandLineParametersParser, times(1))
            .extractFileFromOption(commandLine, optionHtmlFileGetOptValue);

        verify(optionImageFile, times(1))
            .getOpt();

        verify(commandLineParametersParser, times(1))
            .extractFileFromOption(commandLine, optionImageFileGetOptValue);

        verify(optionSupportedImageTypes, times(1))
            .getOpt();

        verify(commandLineParametersParser, times(1))
            .extractImageTypesFromOption(commandLine, optionSupportImageTypesGetOptValue);

        verify(optionSyso, times(1))
            .getOpt();

        verify(commandLine, times(1))
            .hasOption(optionSysoGetOptValue);

        verify(objectFactory, times(1))
            .getInstance(CliBeanType.immutableParameters, imageFile, cssFile, htmlFile, supportedImageTypes, syso);
    }

    @Test
    public void extractFileFromOptionsShouldPassUseFirstElementInValuesArrayAsFilename() {
        final String option = "option";
        final String optionValue = "optionValue";

        final String[] optionValues = {optionValue};

        File file = mock(File.class);

        when(commandLine.getOptionValues(option))
            .thenReturn(optionValues);

        when(objectFactory.getInstance(BeanType.file, optionValue))
            .thenReturn(file);

        final File result = commandLineParametersParser.extractFileFromOption(commandLine, option);

        assertThat(result, is(file));

        verify(commandLine, times(1))
            .getOptionValues(option);

        verify(objectFactory, times(1))
            .getInstance(BeanType.file, optionValue);
    }

    @Test
    public void shouldPopulateResultingSetWithImageTypesFromCommandLineArgs() {
        final String option = "option";
        final String optionValueGif = "gif";

        final String[] optionValues = {optionValueGif};

        Set<SupportedImageType> supportedImageTypes = mock(Set.class);

        when(objectFactory.getInstance(BeanType.set))
            .thenReturn(supportedImageTypes);

        when(commandLine.getOptionValues(option))
            .thenReturn(optionValues);

        doReturn(optionValues)
            .when(commandLineParametersParser)
            .determineIncludedImageTypes(optionValues);

        doReturn(SupportedImageType.gif)
            .when(commandLineParametersParser)
            .convertStringImageTypeArgumentToEnumType(option);

        final Set<SupportedImageType> result =
            commandLineParametersParser.extractImageTypesFromOption(commandLine, option);

        assertThat(result, is(supportedImageTypes));

        verify(objectFactory, times(1))
            .getInstance(BeanType.set);

        verify(commandLineParametersParser, times(1))
            .determineIncludedImageTypes(optionValues);

        verify(commandLine, times(1))
            .getOptionValues(option);

        verify(supportedImageTypes, times(1))
            .add(SupportedImageType.gif);
    }

    @Test
    public void convertStringImageTypeArgumentToEnumTypeShouldConvertStringValueToEnumValueRegardlessOfCase() {
        final String optionValueGif = "GiF";

        final SupportedImageType result =
            commandLineParametersParser.convertStringImageTypeArgumentToEnumType(optionValueGif);

        assertThat(result, is(SupportedImageType.gif));
    }

    @Test(expected = Image2CssValidationException.class)
    public void convertStringImageTypeArgumentToEnumTypeShouldWrapInvalidArgumentInProjectException() {
        final String optionInvalid = "optionInvalid";

        commandLineParametersParser.convertStringImageTypeArgumentToEnumType(optionInvalid);
    }

    @Test
    public void determineIncludedImageTypesShouldReturnAllTypesWhenNullArrayIsPassed() {
        final String[] result = commandLineParametersParser.determineIncludedImageTypes(null);

        assertThat(result.length, is(3));
    }

    @Test
    public void determineIncludedImageTypesShouldReturnAllTypesWhenNullArrayIsLengthZero() {
        final String[] result = commandLineParametersParser.determineIncludedImageTypes(new String[] {});

        assertThat(result.length, is(3));
    }

    @Test
    public void determineIncludedImageTypesShouldReturnSameParamIfNotNullOrLengthZero() {
        final String[] arguments = {"argument"};

        final String[] result = commandLineParametersParser.determineIncludedImageTypes(arguments);

        assertThat(result, is(arguments));
    }



}