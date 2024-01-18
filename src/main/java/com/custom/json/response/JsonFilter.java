package com.custom.json.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonFilter {
	
	public <T> T getFilterJson(T t, String _filter, Class<T> tt) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String _json = objectMapper.writeValueAsString(t);
        JsonNode jsonNode = new JsonProcessor(getFeilds(_filter), objectMapper.readTree(_json)).call();
        return objectMapper.readValue(objectMapper.writeValueAsString(jsonNode), tt);
    }
	
	private String[] getFeilds(String feilds) throws Exception {
		if(!StringUtils.isEmpty(feilds)) {
			List<String> args = new ArrayList<>();
			if(feilds.contains(",")) {
				args = Arrays.asList(feilds.split(","));   
				args.replaceAll(String::trim);
			}else {
				args = Arrays.asList(feilds.trim());
			}
			return args.toArray(new String[args.size()]);
		}else {
			throw new Exception("Please provide the filter arguments.");
		}
	}
}
