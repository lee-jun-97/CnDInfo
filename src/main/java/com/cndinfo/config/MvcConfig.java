package com.cndinfo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/about").setViewName("about");
		registry.addViewController("/services").setViewName("services");
//		registry.addViewController("/contact").setViewName("contact");
		registry.addViewController("/loginForm").setViewName("loginForm");
		registry.addViewController("/join").setViewName("join");
		
	}

}
