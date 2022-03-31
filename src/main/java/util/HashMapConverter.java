package util;

import util.MessageProcessError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = new ObjectMapper().writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            throw new MessageProcessError(e.getMessage());
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, Object> customerInfo = null;
        try {
            customerInfo = new ObjectMapper().readValue(customerInfoJSON, Map.class);
        } catch (final IOException e) {
            throw new MessageProcessError(e.getMessage());
        }

        return customerInfo;
    }

}