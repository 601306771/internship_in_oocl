package com.cargosmart.snakebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
class RedisConfig {

    @Autowired
    Environment environment;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(new Integer(environment.getProperty("spring.redis.pool.max-idle")))
        poolConfig.setMaxTotal(new Integer(environment.getProperty("spring.redis.pool.max-total")));
        poolConfig.setMinIdle(new Integer(environment.getProperty("spring.redis.pool.min-idle")));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
        factory.setUsePool(true);
        factory.setHostName(environment.getProperty("spring.redis.host"));
        factory.setPort(new Integer(environment.getProperty("spring.redis.port")));
        factory.setDatabase(new Integer(environment.getProperty("spring.redis.pool.database")));
        return factory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

}
