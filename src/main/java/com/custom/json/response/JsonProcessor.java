package com.custom.json.response;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonProcessor {
	
	 private final String[] filters;

	    private final JsonNode jsonNode;

	    JsonProcessor(String[] filters, JsonNode jsonNode) {
	        this.filters = filters;
	        this.jsonNode = jsonNode;
	    }

	    private JsonNode getFields(JsonNode jsonNode, String initial, int step, String... filter) {
	        try {
	            Iterator<Map.Entry<String, JsonNode>> key = jsonNode.fields();
	            while (key.hasNext()) {
	                Map.Entry<String, JsonNode> map = key.next();
	                String header = map.getKey();
	                JsonNode tail = map.getValue();
	                if(ObjectUtils.isEmpty(initial))
	                    initial = header;
	                else
	                    initial = initial + header;
	                if (tail instanceof ArrayNode) {
	                    if(getReverseValidateFields(initial, filter)){
	                        initial = initial.substring(0, initial.length() - header.length());
	                        if(step > 1)
	                            break;
	                    }else if(!StringUtils.isEmpty(getValidateFields(initial, filter))) {
	                        initial = initial + "[" ;
	                        getFieldFromArray((ArrayNode) tail, initial, ++step, filter);
	                        --step;
	                        initial = initial.substring(0, initial.length() - header.length() -1);
	                    } else {
	                        initial = initial.substring(0, initial.length() - header.length());
	                        key.remove();
	                    }
	                } else if (tail instanceof ObjectNode) {
	                    if(getReverseValidateFields(initial, filter)){
	                        initial = initial.substring(0, initial.length() - header.length());
	                        if(step > 1)
	                            break;
	                    }else if(!StringUtils.isEmpty(getValidateFields(initial, filter))) {
	                        initial = initial + ".";
	                        getFields(tail, initial, ++step, filter);
	                        --step;
	                        initial = initial.substring(0, initial.length() - header.length() -1);
	                    }
	                    else {
	                        initial = initial.substring(0, initial.length() - header.length());
	                        key.remove();
	                    }
	                } else {
	                    if (StringUtils.isEmpty(getValidateFields(initial, filter))) {
	                        key.remove();
	                    }
	                    initial = initial.substring(0, initial.length() - header.length());
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Exception: " + e);
	        }
	        return jsonNode;
	    }

	    private ArrayNode getFieldFromArray(ArrayNode arrayNode, String initial, int step, String... filter){
	        for(int i = 0; i < arrayNode.size(); i++){
	            JsonNode jsonNode = arrayNode.get(i);
	            getFields(jsonNode, initial, step, filter);
	        }
	        return arrayNode;
	    }

	    public JsonNode call() {
	        return getFields(jsonNode,  "", 0, filters);
	    }

	    private static String getValidateFields(String filter, String... args){
	        String _data = null;
	        for(String arg : args){
	            if(arg.startsWith(filter)){
	                _data = arg;
	                break;
	            }
	        }
	        return _data;
	    }

	    private static boolean getReverseValidateFields(String filter, String... args){
	        boolean _data = false;
	        for(String arg : args){
	            if(filter.startsWith(arg)){
	                _data = true;
	                break;
	            }
	        }
	        return _data;
	    }

}
