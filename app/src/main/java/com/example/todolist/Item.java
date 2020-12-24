package com.example.todolist;

public class Item {

    private int id;
    private String item;
    private String date;

    public Item(){

        super();

    }
    public Item(int id, String item, String date){

        this.id = id;
        this.item = item;
        this.date = date;

    }
    public Item(String item, String date){

        this.item = item;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
