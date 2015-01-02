package com.rf1m.image2css.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration()
@ComponentScan("com.rf1m.image2css.web.controller")
@EnableWebMvc
@Import(CommonContextConfiguration.class)
class WebContextConfiguration {

}
