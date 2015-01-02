package com.rf1m.image2css.config

import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.service.DefaultImageConversionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

import static com.rf1m.image2css.domain.SupportedImageType.gif
import static com.rf1m.image2css.domain.SupportedImageType.jpg
import static com.rf1m.image2css.domain.SupportedImageType.png

import static java.util.Collections.unmodifiableSet

@Configuration()
@PropertySource(value = ["classpath:/image2css-cmn.properties", "classpath:/image2css-exception-messages.properties"])
class Image2CssCommonContextConfiguration {

    public static final Set<SupportedImageType> defaultSupportedImageTypes =
        unmodifiableSet(new HashSet([gif, jpg, png]))

    @Autowired
    Environment environment

    @Bean
    DefaultImageConversionService defaultImageConversionService() {
        return new DefaultImageConversionService(environment.getProperty("template.css.class.def"))
    }
}

