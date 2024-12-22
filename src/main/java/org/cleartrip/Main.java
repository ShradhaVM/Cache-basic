package org.cleartrip;
import org.cleartrip.MoveManagementSystem.MovieManagement;

import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        MovieManagement driverSystem = new MovieManagement();
        System.out.println("Welcome to Movie Management System");


        while(true){
            System.out.println("\nEnter a command:");
            System.out.println("0: ADD_MOVIE <id> <title> <genre> <year> <rating>");
            System.out.println("1: ADD_USER <id> <name> <preferred_genre>");
            System.out.println("2: SEARCH_MULTI <user_id> <search_type> <search_value>");
            System.out.println("3: SEARCH <user_id> <search_type> <search_value>");
            System.out.println("4: VIEW_CACHE_STATS");
            System.out.println("5: CLEAR_CACHE <cache_level");
            System.out.println("6: EXIT");
            System.out.println("7: LIST_ALL");

            String input = scanner.nextLine();
            String[] parts = input.split("\\s+", 2);
            String command = parts[0]. toUpperCase();

            try{
                switch(command){
                    case "ADD_MOVIE":
                        String[] movieArgs = parts[1].split("\\s+", 5);
                        int movieId = Integer.parseInt((movieArgs[0]));
                        String title = movieArgs[1];
                        String genre = movieArgs[2];
                        int year = Integer.parseInt(movieArgs[3]);
                        double rating = Double.parseDouble(movieArgs[4]);
                        driverSystem.addMovie(movieId, title, genre, year, rating);
                        break;

                    case "ADD_USER" :
                        String[] userArgs = parts[1].split("\\s+", 3);
                        int userId = Integer.parseInt(userArgs[0]);
                        String name = userArgs[1];
                        String preferredGenre = userArgs[2];
                        driverSystem.addUser(userId, name, preferredGenre);
                        break;

                    case "SEARCH_MULTI":
                        String[] searchMultiArgs = parts[1].split("\\s+", 4);
                        int searchMultiUserId = Integer.parseInt(searchMultiArgs[0]);
                        String searchMultiGenre = searchMultiArgs[1];
                        int searchMultiYear = Integer.parseInt(searchMultiArgs[2]);
                        double searchMultiRating = Double.parseDouble(searchMultiArgs[3]);
                        driverSystem.searchMulti(searchMultiUserId, searchMultiGenre, searchMultiYear, searchMultiRating);
                        break;

                    case "SEARCH":
                        String[] searchArgs = parts[1].split("\\s+", 3);
                        int searchUserId = Integer.parseInt(searchArgs[0]);
                        if (!driverSystem.isUserRegistered(searchUserId)) {
                            System.out.println("Error: Invalid user ID. Please register the user first.");
                            break;
                        }
                        String searchType = searchArgs[1];
                        String searchValue = searchArgs[2];
                        driverSystem.search(searchUserId, searchType, searchValue);
                        break;

                    case "VIEW_CACHE_STATS":
                        driverSystem.viewHits();
                        break;

                    case "CLEAR_CACHE":
                        driverSystem.clearCache(parts[1].trim());
                        break;

                    case "LIST_ALL":
                        driverSystem.listAll();
                        break;

                    case "EXIT":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid command");
                }
            } catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        //add additional validation for invalid inputs
        // like invalid movie or user IDs when adding them, or
        // checking for empty values in commands like ADD_MOVIE or ADD_USER.

    }
}