package com.custom.json.response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfiguration {
	
	   @Bean
	    public ObjectMapper buildObjectMapper() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setSerializationInclusion(Include.NON_NULL);
	        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
	                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
	                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
	                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
	        return objectMapper;
	    }

}
