package com.todoapp.infrastructure.config;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${DOCKER_REDIS_HOST}")
    private String REDIS_HOST;

    @Value("${REDIS_PORT}")
    private String REDIS_PORT;

    private static final Integer[] TODO_CACHE_TTL_MINUTES = { 1, 1 };
    private static final String[] TODO_CACHE_VALUES = { "todo_id", "my_todos" };

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(REDIS_HOST, Integer.parseInt(REDIS_PORT));
    }

    // !
    // ! KeySerializer: Anahtar keyleri redise String formatta saklanacak
    // ! HashKeySerializer : hash yapısında nesnelerin string şeklinde saklar =>
    // ! GenericToStringSerializer
    // ! HashValueSerializer : Hash değerleri için kullanılır ve Java nesnelerini
    // ! serileştirir => JdkSerializationRedisSerializer
    // ! ValueSerializer : Değerlerin nasıl serileştirileceği belirlenir. Burada,
    // ! değerlerin string formatında serileştirileceği belirtilmiştir.
    // !

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;

    }

    // ! Cache mekanizma ayarları
    @Bean(value = "cacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // ? null değerleri cache de saklama
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        // ? JSON formunda verilerin serileşmesini sağlar

        redisCacheConfiguration.usePrefix(); // ? redis cache anahtarları ekler ,birbirleri ile ayrışmasını sağlar
                                             // ? unique id gibi bir şey

        // ! Default ttl süresi
        redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        Map<String, RedisCacheConfiguration> configs = new HashMap<>();

        for (int i = 0; i < TODO_CACHE_TTL_MINUTES.length; i++) {
            configs.put(TODO_CACHE_VALUES[i],
                    redisCacheConfiguration.entryTtl(Duration.ofMinutes(TODO_CACHE_TTL_MINUTES[i])));
        }

        // ! Redis bağlantı nesnesini kullanarak bir RedisCacheManager nesnesi
        // ! oluşturur.Ayarları yapılandırır
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(configs)
                .build();
    }
}