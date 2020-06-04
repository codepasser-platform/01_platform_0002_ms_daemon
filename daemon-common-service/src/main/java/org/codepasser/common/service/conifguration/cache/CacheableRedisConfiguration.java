package org.codepasser.common.service.conifguration.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.ConstantInterface.CACHE_KEY_PLACEHOLDER;
import static org.codepasser.common.model.ConstantInterface.CACHE_KEY_SEPARATOR;

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
   * @param redisConnectionFactory 链接工厂.
   * @param redisSerializer redis序列化程序.
   * @return redis template.
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setDefaultSerializer(redisSerializer);
    redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
    redisTemplate.setValueSerializer(redisSerializer);
    redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
    redisTemplate.setHashValueSerializer(redisSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  /**
   * 自定义redis序列化的机制,重新定义一个ObjectMapper.防止和MVC的冲突.
   *
   * @return RedisSerializer.
   */
  @Bean
  public RedisSerializer<Object> redisSerializer() {
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    // 反序列化时候遇到不匹配的属性并不抛出异常
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 序列化时候遇到空对象不抛出异常
    om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    // 反序列化的时候如果是无效子类型,不抛出异常
    om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    // 不使用默认的dateTime进行序列化,
    om.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
    // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
    om.registerModule(new JavaTimeModule());
    // 启用反序列化所需的类型信息,在属性中添加@class
    // @deprecated Since 2.10
    // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    om.activateDefaultTyping(
        LaissezFaireSubTypeValidator.instance,
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.PROPERTY);
    // 配置null值的序列化器
    GenericJackson2JsonRedisSerializer.registerNullValueSerializer(om, null);
    return new GenericJackson2JsonRedisSerializer(om);
  }
}
