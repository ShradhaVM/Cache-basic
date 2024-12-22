package org.cleartrip.entities;

//Movie entity
public class Movie{
    int id;
    public String title;
    public String genre;
    public int releaseYear;
    double rating;

    public Movie(int id, String title, String genre, int releaseYear, double rating){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    @Override
    public String toString(){
        return "[" + title + " (" + releaseYear + ", " + genre + ", " + rating + ")]";
    }


    public boolean matchesMulti(String genre, int year, double minRating) {
        return this.genre.equalsIgnoreCase(genre) && this.releaseYear == year && this.rating >= minRating;
    }
}

//override equals() and hashCode() in the Movie class to make sure that two movies
// with the same ID are treated as the same object in hash-based collections (like HashMaps).