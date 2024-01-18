package com.custom.json.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonFilter {
	
	public <T> T getFilterJson(T t, String _filter, Class<T> tt) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String _json = objectMapper.writeValueAsString(t);
        JsonNode jsonNode = new JsonProcessor(_filter.split(","), objectMapper.readTree(_json)).call();
        return objectMapper.readValue(objectMapper.writeValueAsString(jsonNode), tt);
    }
}
