package org.cleartrip.CacheManager;


import org.cleartrip.entities.Movie;

import java.util.LinkedHashMap;
import java.util.List;

public class LRUCache{
    private final int capacity;
    private final LinkedHashMap<String, List<Movie>> cache;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }
    //Get movie

    public List<Movie> get(String key){
        return cache.getOrDefault(key,null);
    }
    //put movie
    public void put(String key, List<Movie> value){
        //if cache capacity is exceeded
        if(cache.size() >= capacity){
            String oldestMovie = cache.keySet().iterator().next(); //present at the first entry
            cache.remove(oldestMovie);
        }

        cache.put(key, value);
    }

    //consider adding a log or message for when evictions happen,
    //for debugging or tracking the eviction process

    //clear cache
    public void clear(){
        cache.clear();
    }

    //check if the key is already present
    public boolean contains(String key){
        return cache.containsKey(key);
    }
}
