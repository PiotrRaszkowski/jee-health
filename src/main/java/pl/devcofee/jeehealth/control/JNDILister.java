package pl.devcofee.jeehealth.control;

import java.text.MessageFormat;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class JNDILister {

    public static JsonObject list(String namespace) {
        try {
            JsonObjectBuilder builder = Json.createObjectBuilder();

            InitialContext c = new InitialContext();
            NamingEnumeration<NameClassPair> list = c.list(namespace);
            while (list.hasMoreElements()) {
                NameClassPair nameClassPair = list.nextElement();
                String name = nameClassPair.getName();
                String type = nameClassPair.getClassName();
                builder.add(name, type);
            }
            return builder.build();
        } catch (NamingException e) {
            throw new RuntimeException(MessageFormat.format("Unable to list JNDI namespace = {0}.", namespace), e);
        }
    }
}
