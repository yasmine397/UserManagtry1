package com.example.usermanagementmodule.book;

public class Book {
    private String name;
    private String realestDate;
    private String deseridsion;
    private String booklan;
    private String photo;

    // Empty constructor needed for Firestore
    public Book() {
        // Required empty constructor for Firestore
    }

    public Book(String name, String realestDate, String deseridsion, String booklan, String photo) {
        this.name = name;
        this.realestDate = realestDate;
        this.deseridsion = deseridsion;
        this.booklan = booklan;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealestDate() {
        return realestDate;
    }

    public void setRealestDate(String realestDate) {
        this.realestDate = realestDate;
    }

    public String getDeseridsion() {
        return deseridsion;
    }

    public void setDeseridsion(String deseridsion) {
        this.deseridsion = deseridsion;
    }

    public String getBooklan() {
        return booklan;
    }

    public void setBooklan(String booklan) {
        this.booklan = booklan;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", realestDate='" + realestDate + '\'' +
                ", deseridsion='" + deseridsion + '\'' +
                ", booklan='" + booklan + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
