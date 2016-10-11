package com.ecmoho.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Administrator on 2016/1/18.
 */
public class Jackson2JsonRedisTemplate extends RedisTemplate<String,Object> {

    public Jackson2JsonRedisTemplate() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jsonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jsonSerializer.setObjectMapper(objectMapper);

        setKeySerializer(stringSerializer);
        setValueSerializer(jsonSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jsonSerializer);
    }

    public Jackson2JsonRedisTemplate(Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jsonSerializer = new Jackson2JsonRedisSerializer<>(clazz);
        jsonSerializer.setObjectMapper(objectMapper);

        setKeySerializer(stringSerializer);
        setValueSerializer(jsonSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jsonSerializer);
    }


    public Jackson2JsonRedisTemplate(TypeReference typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        Jackson2JsonListRedisSerializer jsonSerializer = new Jackson2JsonListRedisSerializer(typeReference);
        jsonSerializer.setObjectMapper(objectMapper);

        setKeySerializer(stringSerializer);
        setValueSerializer(jsonSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jsonSerializer);
    }


    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public Jackson2JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
