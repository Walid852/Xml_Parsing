package com.company;

import java.util.Comparator;
import java.util.Date;

public class Book {
    String ID;
    String Author;
    String Title;
    String Genre;
    double Price=0.0;
    String Publish_Date;
    String Description;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getPublish_Date() {
        return Publish_Date;
    }

    public void setPublish_Date(String publish_Date) {
        Publish_Date = publish_Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID='" + ID + '\'' +
                ", Author='" + Author + '\'' +
                ", Title='" + Title + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Price='" + Price + '\'' +
                ", Publish_Date='" + Publish_Date + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public Book(String ID, String author, String title, String genre, double price, String publish_Date, String description) {
        this.ID = ID;
        Author = author;
        Title = title;
        Genre = genre;
        Price = price;
        Publish_Date = publish_Date;
        Description = description;
    }


}
