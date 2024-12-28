# Movie Management System with Caching

## Overview

This is a movie management system that implements two types of caching mechanisms to optimize search operations:

- **LRU Cache** (Least Recently Used): To store a limited number of movies that are frequently accessed.
- **LFU Cache** (Least Frequently Used): To store a larger number of movies based on their access frequency.

The system allows you to add movies, add users, search for movies by different parameters, and view cache statistics.

## Features

- **Add Movies**: Add new movies with unique IDs, title, genre, release year, and rating.
- **Add Users**: Register users with their preferred genre.
- **Search Movies**: Search movies by title, genre, or year, with caching at different levels (L1 and L2).
- **Cache Management**: View cache hits, clear specific caches (L1 or L2), and manage cache capacity.
- **Multi-Parameter Search**: Search for movies by genre, release year, and minimum rating.

## Project Structure

### 1. **CacheManager**
Contains classes for managing two types of caches:

- **LFUCache**: Least Frequently Used cache for storing and evicting movie data based on access frequency.
- **LRUCache**: Least Recently Used cache for storing and evicting movie data based on recency of access.

### 2. **Entities**
Contains classes representing the data structures used in the system:

- **Movie**: Represents a movie with fields such as ID, title, genre, release year, and rating.
- **User**: Represents a user with fields such as user ID, name, and preferred genre.

### 3. **MoveManagementSystem**
Contains the logic for adding movies, adding users, searching, and managing caches.

### 4. **Main**
Contains the main entry point of the program, handling user input and executing commands.

## How It Works

### Caching Strategy
- **L1 Cache (LRU)**: Stores movie search results with the most recent access. This is the first cache to be checked.
- **L2 Cache (LFU)**: Stores search results based on how frequently they are accessed. When L1 cache misses, L2 is checked next.
- **Primary Store**: If both L1 and L2 caches miss, the primary movie store is queried. The result is then cached in both L1 and L2 for future use.

### Search Operations
1. **Search by Title, Genre, or Year**: Search in L1 cache, then in L2 cache, and finally in the primary store if needed.
2. **Multi-Parameter Search**: Search by genre, release year, and minimum rating.

### Cache Statistics
- Track cache hits for both L1 and L2 caches, as well as the number of searches performed on the primary movie store.

## Usage

### Commands

1. **ADD_MOVIE** `<id> <title> <genre> <year> <rating>`
   - Adds a new movie to the system.
   - Example: 
     ```text
     ADD_MOVIE 1 "The Dark Knight" "Action" 2008 9.0
     ```

2. **ADD_USER** `<id> <name> <preferred_genre>`
   - Adds a new user to the system.
   - Example: 
     ```text
     ADD_USER 101 "John Doe" "Action"
     ```

3. **SEARCH** `<user_id> <search_type> <search_value>`
   - Search for a movie by title, genre, or year.
   - Example: 
     ```text
     SEARCH 101 "TITLE" "The Dark Knight"
     ```

4. **SEARCH_MULTI** `<user_id> <genre> <year> <minRating>`
   - Search for movies by multiple parameters: genre, year, and minimum rating.
   - Example: 
     ```text
     SEARCH_MULTI 101 "Action" 2008 8.0
     ```

5. **VIEW_CACHE_STATS**
   - View the current cache hit statistics.

6. **CLEAR_CACHE** `<cache_level>`
   - Clear the cache at the specified level (L1 or L2).
   - Example: 
     ```text
     CLEAR_CACHE L1
     ```

7. **LIST_ALL**
   - List all movies in the primary store.

8. **EXIT**
   - Exit the program.

## Cache Management

The cache evicts movies from memory based on access patterns:
- **LRU (Least Recently Used)**: Evicts the least recently accessed movie when the cache is full.
- **LFU (Least Frequently Used)**: Evicts the least frequently accessed movie when the cache is full.

## Example Interaction

```text
Welcome to Movie Management System

Enter a command:
0: ADD_MOVIE <id> <title> <genre> <year> <rating>
1: ADD_USER <id> <name> <preferred_genre>
2: SEARCH_MULTI <user_id> <search_type> <search_value>
3: SEARCH <user_id> <search_type> <search_value>
4: VIEW_CACHE_STATS
5: CLEAR_CACHE <cache_level>
6: EXIT
7: LIST_ALL

> ADD_MOVIE 1 "The Dark Knight" "Action" 2008 9.0
Movie added successfully - The Dark Knight

> ADD_USER 101 "John Doe" "Action"
User added successfully - John Doe

> SEARCH 101 "TITLE" "The Dark Knight"
Movie found in Primary: [[The Dark Knight (2008, Action, 9.0)]]

> VIEW_CACHE_STATS
Hits in L1 Cache: 0
Hits in L2 Cache: 0
Hits in Primary movie base: 1
All searches on the System: 1
