package com.tickettogether.global.config.redis.service;

import com.tickettogether.global.config.security.jwt.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService<K, V> {
    private final RedisTemplate<K, V> redisTemplate;
    private final JwtConfig jwtConfig;

    public void setValue(K key, V value){
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, Duration.ofSeconds(Long.parseLong(jwtConfig.getRefreshExpiry())));
    }

    public void setValue(K key, List<V> value){
        redisTemplate.opsForList().rightPush(key, value.get(0));
        redisTemplate.opsForList().rightPush(key, value.get(1));
        redisTemplate.expire(key, Long.parseLong(jwtConfig.getRefreshExpiry()), TimeUnit.MILLISECONDS);
    }

    public V getValue(K key){
        ValueOperations<K, V> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public List<V> getValues(K key){
        ListOperations<K, V> values = redisTemplate.opsForList();
        return values.range(key, 0, 1);
    }

    public void deleteValue(K key){
        redisTemplate.delete(key);
    }
}
