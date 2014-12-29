package com.rf1m.image2css;

import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.ioc.CommonObjectFactory;
import com.rf1m.image2css.service.DefaultImageConversionService;
import com.rf1m.image2css.util.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.HashSet;
import java.util.Set;

import static com.rf1m.image2css.domain.SupportedImageType.gif;
import static com.rf1m.image2css.domain.SupportedImageType.jpg;
import static com.rf1m.image2css.domain.SupportedImageType.png;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

@Configuration()
@PropertySource(value = {"classpath:/image2css-cmn.properties", "classpath:/image2css-exception-messages.properties"})
public class Image2CssCommonContextConfiguration {

    public static final Set<SupportedImageType> defaultSupportedImageTypes =
        unmodifiableSet(new HashSet(asList(new SupportedImageType[]{ gif, jpg, png })));

    @Autowired
    private Environment environment;

    @Bean
    public CommonObjectFactory commonObjectFactory() {
        return new CommonObjectFactory();
    }

    @Bean
    public Base64Encoder base64Encoder() {
        return new Base64Encoder(commonObjectFactory());
    }

    @Bean
    public DefaultImageConversionService defaultImageConversionService() {
        return new DefaultImageConversionService(base64Encoder(), commonObjectFactory(), environment.getProperty("template.css.class.def"));
    }
}

