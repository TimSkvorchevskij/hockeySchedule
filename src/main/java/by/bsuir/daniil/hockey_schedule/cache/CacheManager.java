package by.bsuir.daniil.hockey_schedule.cache;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheManager {
    private final Map<String, Object> cache;

    public CacheManager() {
        this.cache = new ConcurrentHashMap<>();
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




//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//public class CacheManager {
//    private static CacheManager instance;
//    private static Object monitor = new Object();
//    private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());
//
//    private CacheManager() {
//    }
//
//    public void put(String cacheKey, Object value) {
//        cache.put(cacheKey, value);
//    }
//
//    public Object get(String cacheKey) {
//        return cache.get(cacheKey);
//    }
//
//    public void clear(String cacheKey) {
//        cache.put(cacheKey, null);
//    }
//
//    public void clear() {
//        cache.clear();
//    }
//
//    public static CacheManager getInstance() {
//        if (instance == null) {
//            synchronized (monitor) {
//                if (instance == null) {
//                    instance = new CacheManager();
//                }
//            }
//        }
//        return instance;
//    }
//
//}
