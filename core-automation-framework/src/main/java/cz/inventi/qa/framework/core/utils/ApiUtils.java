package cz.inventi.qa.framework.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

public class ApiUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Converts given object to json String.
     *
     * @param object object to be converted
     * @return String with json
     */
    public static String convertToJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FrameworkException(e);
        }
    }
}