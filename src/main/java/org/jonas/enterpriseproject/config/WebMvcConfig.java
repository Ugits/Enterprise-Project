package org.jonas.enterpriseproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /* Static .html Pages
    * Used for endpoint definition within our BACKEND.
    * Acts as an alternative to the classical annotated @Controller class
    * Mostly for Local Files (For now)
    *
    * Does this look for 'templates' specifically? - YES
    * */

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/admin").setViewName("adminpage");
        registry.addViewController("/user").setViewName("userpage");
    }

}

