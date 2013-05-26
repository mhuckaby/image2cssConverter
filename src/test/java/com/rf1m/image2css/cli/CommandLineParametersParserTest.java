package com.rf1m.image2css.cli;

import com.rf1m.image2css.exception.Image2CssValidationException;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
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
    ObjectFactory objectFactory;

    @Mock
    CommandLine commandLine;

    CommandLineParametersParser commandLineParametersParser;

    @Before
    public void before() {
        commandLineParametersParser = spy(new CommandLineParametersParser(objectFactory));
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

        CommandLineParser commandLineParser = mock(CommandLineParser.class);
        Option optionCssFile = mock(Option.class);
        Option optionHtmlFile = mock(Option.class);
        Option optionImageFile = mock(Option.class);
        Option optionSupportedImageTypes = mock(Option.class);
        Option optionSyso = mock(Option.class);

        Options options = mock(Options.class);

        File cssFile = mock(File.class);
        File htmlFile = mock(File.class);
        File imageFile = mock(File.class);

        Set<SupportedImageTypes> supportedImageTypes = mock(Set.class);

        Parameters parameters = mock(Parameters.class);

        when(objectFactory.instance(BeanType.commandLineParametersParser))
            .thenReturn(commandLineParser);

        when(objectFactory.instance(BeanType.optionCssFile))
            .thenReturn(optionCssFile);

        when(objectFactory.instance(BeanType.optionHtmlFile))
            .thenReturn(optionHtmlFile);

        when(objectFactory.instance(BeanType.optionImageFile))
            .thenReturn(optionImageFile);

        when(objectFactory.instance(BeanType.optionImageTypes))
            .thenReturn(optionSupportedImageTypes);

        when(objectFactory.instance(BeanType.optionSyso))
            .thenReturn(optionSyso);

        when(objectFactory.instance(BeanType.options))
            .thenReturn(options);

        when(commandLineParser.parse(options, args))
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

        when(objectFactory.instance(BeanType.immutableParameters, imageFile, cssFile, htmlFile, supportedImageTypes, syso))
            .thenReturn(parameters);

        final Parameters result = commandLineParametersParser.parse(args);

        assertThat(result, is(parameters));

        verify(objectFactory, times(1))
            .instance(BeanType.commandLineParametersParser);

        verify(objectFactory, times(1))
            .instance(BeanType.optionCssFile);

        verify(objectFactory, times(1))
            .instance(BeanType.optionHtmlFile);

        verify(objectFactory, times(1))
            .instance(BeanType.optionImageFile);

        verify(objectFactory, times(1))
            .instance(BeanType.optionImageTypes);

        verify(objectFactory, times(1))
            .instance(BeanType.optionSyso);

        verify(objectFactory, times(1))
            .instance(BeanType.options);

        verify(commandLineParser, times(1))
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
            .instance(BeanType.immutableParameters, imageFile, cssFile, htmlFile, supportedImageTypes, syso);
    }

    @Test
    public void extractFileFromOptionsShouldPassUseFirstElementInValuesArrayAsFilename() {
        final String option = "option";
        final String optionValue = "optionValue";

        final String[] optionValues = {optionValue};

        File file = mock(File.class);

        when(commandLine.getOptionValues(option))
            .thenReturn(optionValues);

        when(objectFactory.instance(BeanType.file, optionValue))
            .thenReturn(file);

        final File result = commandLineParametersParser.extractFileFromOption(commandLine, option);

        assertThat(result, is(file));

        verify(commandLine, times(1))
            .getOptionValues(option);

        verify(objectFactory, times(1))
            .instance(BeanType.file, optionValue);
    }

    @Test
    public void shouldPopulateResultingSetWithImageTypesFromCommandLineArgs() {
        final String option = "option";
        final String optionValueGif = "gif";

        final String[] optionValues = {optionValueGif};

        Set<SupportedImageTypes> supportedImageTypes = mock(Set.class);

        when(objectFactory.instance(BeanType.set))
            .thenReturn(supportedImageTypes);

        when(commandLine.getOptionValues(option))
            .thenReturn(optionValues);

        doReturn(SupportedImageTypes.gif)
            .when(commandLineParametersParser)
            .convertStringImageTypeArgumentToEnumType(option);

        final Set<SupportedImageTypes> result =
            commandLineParametersParser.extractImageTypesFromOption(commandLine, option);

        assertThat(result, is(supportedImageTypes));

        verify(objectFactory, times(1))
            .instance(BeanType.set);

        verify(commandLine, times(1))
            .getOptionValues(option);

        verify(supportedImageTypes, times(1))
            .add(SupportedImageTypes.gif);
    }

    @Test
    public void convertStringImageTypeArgumentToEnumTypeShouldConvertStringValueToEnumValueRegardlessOfCase() {
        final String optionValueGif = "GiF";

        final SupportedImageTypes result =
            commandLineParametersParser.convertStringImageTypeArgumentToEnumType(optionValueGif);

        assertThat(result, is(SupportedImageTypes.gif));
    }

    @Test(expected = Image2CssValidationException.class)
    public void convertStringImageTypeArgumentToEnumTypeShouldWrapInvalidArgumentInProjectException() {
        final String optionInvalid = "optionInvalid";

        commandLineParametersParser.convertStringImageTypeArgumentToEnumType(optionInvalid);
    }

}