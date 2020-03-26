package com.example.urltest;

import android.app.Application;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchResult extends Application {
    String book_tile;
    String author;
    String publisher;
    String year;
    String language;
    String extension;
    public   SearchResult(){


    }

    public String getBook_tile() {
        return book_tile;
    }

    public void setBook_tile(String book_tile) {
        this.book_tile = book_tile;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
