package com.Bingo.SlotGame.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class RedisHashMapRepository {

    private final HashOperations<String, Integer, Object> hashOperations;

    @Autowired
    public RedisHashMapRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    // Save a complete HashMap under a given key
    public void saveHashMap(String key, Map<Integer, Object> map) {
        hashOperations.putAll(key, map);
    }

    // Retrieve HashMap from Redis
    public Map<Integer, Object> getHashMap(String key) {
        return hashOperations.entries(key);
    }

    // Add or update a key-value pair inside the HashMap
    public void putValueInHashMap(String key, Integer mapKey, Object value) {
        hashOperations.put(key, mapKey, value);
    }

    // Get a single value from the HashMap
    public Object getValueFromHashMap(String key, Integer mapKey) {
        return hashOperations.get(key, mapKey);
    }

    // Delete a field from the HashMap
    public void deleteFromHashMap(String key, Integer mapKey) {
        hashOperations.delete(key, mapKey);
    }
}
