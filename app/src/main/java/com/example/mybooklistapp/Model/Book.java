package com.example.mybooklistapp.Model;



public class Book {
 private final String nameOfBook;
    private final String author;
    private final String pubDate;
    private final String imageUrl;
    private final String previewLink;

    public Book(String nameOfBook, String author, String pubDate,String imageUrl,String previewLink) {
        this.nameOfBook = nameOfBook;
        this.author = author;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
        this.previewLink = previewLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public String getAuthor() {
        return author;
    }

    public String getPubDate() {
        return pubDate;
    }

}
