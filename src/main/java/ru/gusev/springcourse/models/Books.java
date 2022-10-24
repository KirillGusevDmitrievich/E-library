package ru.gusev.springcourse.models;

public class Books {
    private int book_id;
    private String title;
    private String autor;
    private int year;

    public Books () {}
    public Books ( String title, String autor, int year) {


        this.title=title;
        this.autor=autor;
        this.year=year;
    }
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }




}
