package com.rf1m.image2css;

import com.rf1m.image2css.config.Image2CssCommonContextConfiguration;
import com.rf1m.image2css.ioc.WebObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration()
@ComponentScan("com.rf1m.image2css.web.controller")
@EnableWebMvc
@Import(Image2CssCommonContextConfiguration.class)
public class Image2CssWebContextConfiguration {

    public Image2CssWebContextConfiguration() {
        super();
    }

    @Bean
    public WebObjectFactory webObjectFactory() {
        return new WebObjectFactory();
    }
}
