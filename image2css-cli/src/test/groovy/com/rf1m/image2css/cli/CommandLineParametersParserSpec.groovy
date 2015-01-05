package com.rf1m.image2css.cli

import com.rf1m.image2css.config.CliContextConfiguration
import com.rf1m.image2css.domain.SupportedImageType
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spock.lang.Specification

class CommandLineParametersParserSpec extends Specification {
    CommandLineParametersParser commandLineParametersParser

    def setup() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(CliContextConfiguration.class)
        commandLineParametersParser = ctx.getBean("commandLineParametersParser")
    }

    def "should remove html and css requirements when syso parameter is passed"() {
        when:
            commandLineParametersParser.correctOptionRequirementsForSyso(true)
        then:
            commandLineParametersParser.optionCssFile.required == false
            commandLineParametersParser.optionHtmlFile.required == false
            commandLineParametersParser.options.requiredOptions.contains(commandLineParametersParser.optionHtmlFile) == false
            commandLineParametersParser.options.requiredOptions.contains(commandLineParametersParser.optionCssFile) == false
    }

    def "should parse system out and use default types when none are specified"() {
        setup:
            String[] args = ["-syso", "-f", "file.jpg"]

        when:
            ImmutableParameters result = commandLineParametersParser.parse(args)
        then:
            result.outputToConsoleDesired == true
            result.imageFile.name == "file.jpg"
            result.cssFile == null
            result.htmlFile == null
            (0..2).each({ i ->
                assert result.supportedTypes.contains(SupportedImageType.valueOf(commandLineParametersParser.defaultTypes[i]))
            })
            result.outputValid == true
            result.cssFileOutputDesired == false
            result.htmlFileOutputDesired == false
            result.localResource == true
    }

    def "should use specified types"() {
        setup:
            String[] args = ["-syso", "-f", "file.jpg", "-i", "jpg"]

        when:
            ImmutableParameters result = commandLineParametersParser.parse(args)
        then:
            result.outputToConsoleDesired == true
            result.imageFile.name == "file.jpg"
            result.cssFile == null
            result.htmlFile == null

            assert result.supportedTypes.contains(SupportedImageType.jpg)

            result.outputValid == true
            result.cssFileOutputDesired == false
            result.htmlFileOutputDesired == false
            result.localResource == true
    }
}
