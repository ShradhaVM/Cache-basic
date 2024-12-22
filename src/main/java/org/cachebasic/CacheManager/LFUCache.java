package org.cachebasic.CacheManager;

import org.cachebasic.entities.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFUCache{
    private final int capacity;
    private final Map<String, List<Movie>> cache;
    private final Map<String, Integer> accessCounts;

    public LFUCache(int capacity){
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.accessCounts = new HashMap<>();
    }

    //Get movie
    public List<Movie> get(String key){
        if(!cache.containsKey(key)) return null;
        accessCounts.put(key, accessCounts.get(key) + 1);
        return cache.get(key);
    }

    //put movie
    public void put(String key, List<Movie> value){
        if(cache.containsKey(key)){
            // key already exists, we update value and access counts
            cache.put(key, value);
            accessCounts.put(key, accessCounts.get(key) + 1);
            return;
        }
        if(cache.size() >= capacity){
            //Cache is full so we remove least accessed
            String keyToRemove = findKeyToRemove();
            cache.remove(keyToRemove);
            accessCounts.remove(keyToRemove);
        }
        //else enters for the first time
        cache.put(key, value);
        accessCounts.put(key, 1);
    }

    private String findKeyToRemove(){
        String keyToRemove = null;
        int minFreq = Integer.MAX_VALUE;
        for(Map.Entry<String, Integer> entry : accessCounts.entrySet()){
            if(entry.getValue() < minFreq){
                minFreq = entry.getValue();
                keyToRemove = entry.getKey();
            }
        }
        return keyToRemove;
    }
    //clear cache using access counts
    public void clear(){
        cache.clear();
        accessCounts.clear();
    }
    public boolean contains(String key){
        return cache.containsKey(key);
    }
}