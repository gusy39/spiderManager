package com.ecmoho.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/1/19.
 */
public class Jackson2JsonListRedisSerializer implements RedisSerializer<Object> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    static final byte[] EMPTY_ARRAY = new byte[0];

    private TypeReference typeReference;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Jackson2JsonListRedisSerializer(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    @SuppressWarnings("unchecked")
    public Object deserialize(byte[] bytes) throws SerializationException {

        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (Object) this.objectMapper.readValue(bytes, 0, bytes.length, typeReference);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    public byte[] serialize(Object t) throws SerializationException {

        if (t == null) {
            return EMPTY_ARRAY;
        }
        try {
            return this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {

        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

}
