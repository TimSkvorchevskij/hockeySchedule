package by.bsuir.daniil.hockey_schedule.cache;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class CacheManager {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static CacheManager instance;
    private CacheManager() {
    }
    public static CacheManager getInstance() {
        if (instance == null){
            instance = new CacheManager();
        }
        return instance;
    }
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public void evict(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}

