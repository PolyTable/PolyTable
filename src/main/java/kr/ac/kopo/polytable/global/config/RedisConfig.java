package kr.ac.kopo.polytable.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static kr.ac.kopo.polytable.global.config.RedisPolicy.*;


//@Configuration
//@EnableCaching
public class RedisConfig {

//    private final String host;
//    private final int port;
//    private final String password;
//
//    public RedisConfig(@Value("${spring.redis.host}") String host,
//                       @Value("${spring.redis.port}") int port,
//                       @Value("${spring.redis.password}") String password) {
//        this.host = host;
//        this.port = port;
//        this.password = password;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(password);
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager() {
//        RedisCacheConfiguration configuration = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .disableCachingNullValues()
//                .entryTtl(Duration.ofMinutes(30))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()));
//
//        Map<String, RedisCacheConfiguration> config = new HashMap<>();
//        config.put(CERTIFICATION_KEY, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(CERTIFICATION_TTL)));
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(redisConnectionFactory())
//                .cacheDefaults(configuration)
//                .withInitialCacheConfigurations(config)
//                .build();
//    }
}
