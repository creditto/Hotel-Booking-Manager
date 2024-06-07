package com.hb.api.ms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api(@Autowired TypeResolver typeResolver) {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.hb.api.ms.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
				.produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
				.protocols(new HashSet<>(Arrays.asList("http","https")));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Hotel Booking Manager API")
				.contact(new Contact("Hotel Booking Manager API", "htts://www.booking.com", "booking@yahoo.com"))
				.license("PROPRIETATRY")
				.version("1.0.0.dev-SNAPSHOT")
				.build();
	}
}
