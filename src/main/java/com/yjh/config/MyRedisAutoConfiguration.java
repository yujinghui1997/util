package com.yjh.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjh.properties.MyRedisProperties;
import com.yjh.util.MyRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class})
public class MyRedisAutoConfiguration  {

    @Autowired(required = false)
    private MyRedisProperties myRedisProperties;
    private Map<PatternTopic,MessageListenerAdapter> map;


    @Conditional(MyRedisProperties.class)
    @Bean(name = "rcf")
    public RedisConnectionFactory connectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(myRedisProperties.getMaxActive());
        poolConfig.setMaxIdle(myRedisProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(myRedisProperties.getMaxWait());
        poolConfig.setMinIdle(myRedisProperties.getMaxIdle());
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(true);
        JedisClientConfiguration jedisClientConfiguration = null;
        if (myRedisProperties.getSsl()){
            jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().
                    poolConfig(poolConfig).and().
                    readTimeout(Duration.ofMillis(myRedisProperties.getConnTimeout())).useSsl()
                    .build();
        }else {
            jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().
                    poolConfig(poolConfig).and().
                    readTimeout(Duration.ofMillis(myRedisProperties.getConnTimeout())).build();
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(myRedisProperties.getDatabase());
        redisStandaloneConfiguration.setPort(myRedisProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(myRedisProperties.getPassword()));
        redisStandaloneConfiguration.setHostName(myRedisProperties.getHost());
        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        return redisConnectionFactory;
    }

    @ConditionalOnBean(RedisConnectionFactory.class)
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Qualifier(value = "rcf") RedisConnectionFactory redisConnectionFactory) {
        //设置序列化
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        //指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @ConditionalOnBean(RedisConnectionFactory.class)
    @Bean(name = "srt")
    public StringRedisTemplate stringRedisTemplate(@Qualifier(value = "rcf") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        return stringRedisTemplate;
    }
    @ConditionalOnBean(StringRedisTemplate.class)
    @Bean
    public MyRedisUtil myRedisUtil(@Qualifier(value = "srt") StringRedisTemplate stringRedisTemplate) {
        return new MyRedisUtil(stringRedisTemplate);
    }

}
