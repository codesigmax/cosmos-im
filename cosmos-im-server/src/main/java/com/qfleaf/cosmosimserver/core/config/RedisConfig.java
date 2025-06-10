package com.qfleaf.cosmosimserver.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qfleaf.cosmosimserver.chat.domain.events.listener.ChatMessageSendEventListener;
import com.qfleaf.cosmosimserver.contact.domain.events.listener.ContactCreateEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 构建自定义的对象映射器
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 允许处理更多日期数据类型

        // 构建自定义的string序列化器
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 构建自定义的json序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = GenericJackson2JsonRedisSerializer.builder()
                .objectMapper(objectMapper) // 设置自定义的 objectMapper 以允许处理更多数据类型
                .registerNullValueSerializer(true) // 加入空值序列化器
                .defaultTyping(true) // 允许默认类型（序列化后的json会存放class信息）
                .build();

        // 设置序列化方式
        redisTemplate.setKeySerializer(stringSerializer);  // 键使用 StringRedisSerializer
        redisTemplate.setValueSerializer(jsonSerializer);  // 值使用 JSON 序列化器
        redisTemplate.setHashKeySerializer(stringSerializer);  // Hash 键使用 StringRedisSerializer
        redisTemplate.setHashValueSerializer(jsonSerializer);  // Hash 值使用 JSON 序列化器

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory,
                                                        ContactCreateEventListener contactCreateEventListener,
                                                        ChatMessageSendEventListener chatMessageSendEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(contactCreateEventListener, new PatternTopic("user_online_channel"));
        container.addMessageListener(chatMessageSendEventListener, new PatternTopic("user_chat_channel"));
        return container;
    }
}