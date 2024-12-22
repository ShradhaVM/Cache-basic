package org.cachebasic.MoveManagementSystem;

import org.cachebasic.CacheManager.LFUCache;
import org.cachebasic.CacheManager.LRUCache;
import org.cachebasic.entities.Movie;
import org.cachebasic.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieManagement{
    private final Map<Integer, Movie> primaryStore = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final LRUCache l1Cache = new LRUCache(5);
    private final LFUCache l2Cache = new LFUCache(20);

    private int hitsInL1 = 0;
    private int hitsInL2 = 0;
    private int hitsInPrim = 0;
    private int allSearches = 0;

    //Adding the newest movie in the prim store first
    public void addMovie(int id, String title, String genre, int releaseYear, double rating){
        //if movie already present
        if(primaryStore.containsKey(id)){
            System.out.println("Trying to enter duplicate movie id.... not allowed");
            return;
        }

        Movie movie = new Movie(id, title, genre, releaseYear, rating);
        primaryStore.put(id, movie);
        System.out.println("Movie added successfully - "+ title);
    }
    //Adding the new user first time
    public void addUser(int userId, String name, String preferredGenre){
        //If user already present
        if(users.containsKey(userId)){
            System.out.println("Adding user again... not allowed");
            return;
        }
        User user = new User(userId, name, preferredGenre);
        users.put(userId, user);
        System.out.println("User added successfully - " + name);
    }

    public boolean isUserRegistered(int userId) {
        return users.containsKey(userId); // Assuming userMap is a HashMap storing user details
    }

    //Get movies
    public void listAll() {
        for (Map.Entry<Integer, Movie> entry : primaryStore.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    //Searching movie as per search type - title, genre, release year
    public void search(int userId, String searchType, String searchValue){
        allSearches++;
        String keyForCache = userId + ":" + searchType + ";" + searchValue;

        //Checking L1 Cache
        if(l1Cache.contains(keyForCache)){
            hitsInL1++;
            System.out.println("Movie found in L1: " + l1Cache.get(keyForCache));
            return;
        }


        //Checking L2 Cache
        if(l2Cache.contains(keyForCache)){
            hitsInL2++;
            l1Cache.put(keyForCache, l2Cache.get(keyForCache)); //Adding to L1 first
            System.out.println("Movie found in L2: " + l2Cache.get(keyForCache));
            return;
        }
        //Checking in primary
        List<Movie> result = new ArrayList<>();
        for(Movie movie : primaryStore.values()){
            if(searchType.equalsIgnoreCase("TITLE") && movie.title.equalsIgnoreCase(searchValue)){
                result.add(movie);
            }
            else if(searchType.equalsIgnoreCase("GENRE") && movie.genre.equalsIgnoreCase(searchValue)){
                result.add(movie);
            }
            else if(searchType.equalsIgnoreCase("YEAR") && movie.releaseYear == Integer.parseInt(searchValue)){
                result.add(movie);
            }
        }

        hitsInPrim++;
        //Adding in l1 and l2 cache for future ref
        l1Cache.put(keyForCache, result);
        l2Cache.put(keyForCache, result);
        System.out.println("Movie found in Primary: " + result);

        //Improvements
        //Lazy Promotion: Instead of immediately moving an item from L2 to L1,
        // we could choose to promote the item to L1 after a certain threshold of accesses
        // or if the item remains in L2 for a set time.

        //Instead of promoting to L2 on every search, we could decide to move an item to L2
        // only after it has been searched a certain number of times in L1.
        // This could be useful if our L1 cache is not large and
        // we want to give priority to the most accessed data.
    }
    public void searchMulti(int userId, String genre, int year, double minRating) {
        allSearches++;
        String keyForCache = userId + ":" + genre + ";" + year + ";" + minRating;

        // Checking L1 Cache (user-specific)
        if (l1Cache.contains(keyForCache)) {
            hitsInL1++;
            System.out.println("Movies found in L1: " + l1Cache.get(keyForCache));
            return;
        }

        // Checking L2 Cache (global popular)
        if (l2Cache.contains(keyForCache)) {
            hitsInL2++;
            l1Cache.put(keyForCache, l2Cache.get(keyForCache)); // Adding to L1 first
            System.out.println("Movies found in L2: " + l2Cache.get(keyForCache));
            return;
        }

        // Search in Primary Store
        List<Movie> results = new ArrayList<>();
        for (Movie movie : primaryStore.values()) {
            if (movie.matchesMulti(genre, year, minRating)) {
                results.add(movie);
            }
        }

        // If results are found in primary store
        if (!results.isEmpty()) {
            hitsInPrim++;
            // Adding found results to L1 and L2 cache for future references
            l1Cache.put(keyForCache, results);
            l2Cache.put(keyForCache, results);
            System.out.println("Movies found in Primary: " + results);
        } else {
            System.out.println("No matching movies found.");
        }
    }



    //Bonus : Cache stats
    public void viewHits(){
        System.out.println("Hits in L1 Cache: " + hitsInL1);
        System.out.println("Hits in L2 Cache: " + hitsInL2);
        System.out.println("Hits in Primary movie base: " + hitsInPrim);
        System.out.println("All searches on the System: " + allSearches);
    }

    //Clear any specific cache
    public void clearCache(String level){
        if(level.equalsIgnoreCase("L1")){
            l1Cache.clear();
            System.out.println("Cache Cleared successfully: L1");
        }
        else if(level.equalsIgnoreCase("L2")){
            l2Cache.clear();
            System.out.println("Cache Cleared successfully: L2");
        }
        //The clearCache method works well,
        // but it can be extended to handle invalid inputs
        // (e.g., if the user enters a cache level other than L1 or L2).
    }
}

