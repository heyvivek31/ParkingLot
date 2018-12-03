package com.vivek.parkinglot.model;

public class EntryPoint {
    private int id;

    public EntryPoint(int id){
        this.id = id;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EntryPoint{" + "id=" + id + '}';
    }
}
