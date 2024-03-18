package by.bsuir.daniil.hockey_schedule.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
@Component
public class CacheManager<K, V> extends LinkedHashMap<K, V> {

//    CacheManager<String,Object> cache = new CacheManager<>();
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        int maxSize = 1000;
        return size() > maxSize;
    }


//    public void put(String key, Object value) {
//        cache.put(key, value);
//    }
//
//    public Object get(String key) {
//        return cache.get(key);
//    }
//
//    public boolean containsKey(String key) {
//        return cache.containsKey(key);
//    }
//
//    public void evict(String key) {
//        cache.remove(key);
//    }
//
///    public void clear() {
//        cache.clear();
//    }
}

