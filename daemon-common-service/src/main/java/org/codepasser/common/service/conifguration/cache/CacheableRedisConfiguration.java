package org.codepasser.common.service.conifguration.cache;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.ConstantInterface.CACHE_KEY_PLACEHOLDER;
import static org.codepasser.common.model.ConstantInterface.CACHE_KEY_SEPARATOR;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * CacheableRedisConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/17 : base version.
 */
@Configuration
@EnableCaching // 开启缓存支持
@EnableTransactionManagement // 开启支持事务
public class CacheableRedisConfiguration extends CachingConfigurerSupport {

  private static final int AUTO_CACHE_TTL_SECONDS = 300;

  @Autowired private LettuceConnectionFactory lettuceConnectionFactory;

  @Bean
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        StringBuffer sb = new StringBuffer();
        sb.append(target.getClass().getName());
        sb.append(CACHE_KEY_SEPARATOR);
        // sb.append(method.getName());
        for (Object obj : params) {
          sb.append(CACHE_KEY_SEPARATOR);
          if (obj != null) {
            sb.append(obj.toString());
          } else {
            sb.append(CACHE_KEY_PLACEHOLDER);
          }
        }
        return sb.toString();
      }
    };
  }

  /**
   * 缓存管理器.
   *
   * @return CacheManager
   */
  @Bean(value = CacheableSupport.MANAGER_DEFAULT)
  public CacheManager cacheManager() {
    RedisCacheManager.RedisCacheManagerBuilder builder =
        RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
    Set<String> cacheNames = newHashSet("codeNameCache");
    builder.initialCacheNames(cacheNames);
    return builder.build();
  }

  /**
   * 缓存管理器.
   *
   * @return CacheManager
   */
  @Bean(value = CacheableSupport.MANAGER_AUTO)
  public CacheManager autoCacheManager() {
    RedisCacheManager.RedisCacheManagerBuilder builder =
        RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
    // 设置默认超过期时间是300s
    builder.cacheDefaults(
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(AUTO_CACHE_TTL_SECONDS)));
    return builder.build();
  }

  /**
   * RedisTemplate配置.
   *
   * @param lettuceConnectionFactory
   * @return RedisTemplate
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      LettuceConnectionFactory lettuceConnectionFactory) {
    // 设置序列化
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    // 配置redisTemplate
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    RedisSerializer<?> stringSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(stringSerializer); // key序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // value序列化
    redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash value序列化
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
