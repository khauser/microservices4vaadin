package microservices4vaadin.authserver.configuration;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

//@Slf4j
public class SessionSerializer extends JdkSerializationRedisSerializer {

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            Object obj = super.deserialize(bytes);
            return obj;
        }
        catch (SerializationException se) {
            // whenever inexistent classes from other service are deserialized they end up here
            //log.debug("Ignore: ", se.getRootCause());
        }

        return null;
    }

    @Override
    public byte[] serialize(Object object) {
        return super.serialize(object);
    }

}