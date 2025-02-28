package com.example.usermanagementmodule;

import com.example.usermanagementmodule.book.Book;

import java.util.ArrayList;

public class DataUser {

    private String name;
    private String numphone;
    private String email;
    private ArrayList<Book> books;
    private String datebirth;
    private String photo;


    public void Users(String name, String numphone, String email, ArrayList<Book> book , String datebirth,String photo) {
        this.name=name;
        this.numphone=numphone;
        this.email=email;
        this.books = book;
        this.datebirth=datebirth;
        this.photo=photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumphone() {
        return numphone;
    }

    public void setPhone(String numphone) {this.numphone = numphone;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> book) {
        this.books = book;
    }

    public String getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(String datebirth) {
        this.datebirth = datebirth;
    }

    public void setNumphone(String numphone) {this.numphone = numphone;}

    public String getPhoto() {return photo;}

    public void setPhoto(String photo) {this.photo = photo;}

    @Override
    public String toString() {
        return "DataUser{" +
                "name='" + name + '\'' +
                ", phone='" + numphone + '\'' +
                ", email='" + email + '\'' +
                ", books=" + books +
                ", datebirth='" + datebirth + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
