package com.example.usermanagementmodule.book;

import android.net.Uri;

public class Book {
    private String name;
    private String veiw;
    private String realestDate;
    private String deseridsion;
    private String booklan;
    private String photo;

    public Book(String deseridsion,String name,String veiw ,String realestDate,String photo) {
        this.deseridsion = deseridsion;
        this.name=name;
        this.veiw=veiw;
        this.realestDate=realestDate;
        this.photo=photo;
        this.booklan=booklan;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBooklan() {
        return booklan;
    }

    public void setBooklan(String booklan) {
        this.booklan = booklan;
    }

    public String getDeseridsion() {
        return deseridsion;
    }

    public void setDeseridsion(String deseridsion) {
        this.deseridsion = deseridsion;
    }

    public String getRealestDate() {
        return realestDate;
    }

    public void setRealestDate(String realestDate) {
        this.realestDate = realestDate;
    }

    public String getVeiw() {
        return veiw;
    }

    public void setVeiw(String veiw) {
        this.veiw = veiw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", veiw='" + veiw + '\'' +
                ", realestDate='" + realestDate + '\'' +
                ", deseridsion='" + deseridsion + '\'' +
                ", booklan='" + booklan + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

}
