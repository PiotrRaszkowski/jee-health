package pl.devcofee.jeehealth.control;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ConfigurationPropertiesProvider {

    public static JsonObject getSystemProperties() {
        Properties properties = System.getProperties();
        Enumeration<Object> keys = properties.keys();

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();

            jsonObjectBuilder.add(key.toString(), properties.get(key).toString());
        }

        return jsonObjectBuilder.build();
    }

    public static JsonObject getEnvironmentVariables() {
        Map<String, String> environmentVariables = System.getenv();

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, String> entry : environmentVariables.entrySet()) {
            jsonObjectBuilder.add(entry.getKey(), entry.getValue());
        }

        return jsonObjectBuilder.build();
    }
}
