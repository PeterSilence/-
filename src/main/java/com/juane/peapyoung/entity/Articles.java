package com.juane.peapyoung.entity;

public class Articles {
    private Integer id;
    private String name;
    private String position;
    private String date;
    private Integer takerId;
    private String avator;

    public Articles(Integer id, String name, String position, String date, Integer takerId, String avator) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.date = date;
        this.takerId = takerId;
        this.avator = avator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTakerId() {
        return takerId;
    }

    public void setTakerId(Integer takerId) {
        this.takerId = takerId;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
}
