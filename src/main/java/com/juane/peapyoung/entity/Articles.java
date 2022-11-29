package com.juane.peapyoung.entity;

import java.time.LocalDateTime;
import java.util.Date;


public class Articles {
    private String id;
    private String name;
    private String position;
    private Date date;
    private String takerId;
    private String avator;

    private String owner;

    //物品具备三种状态：丢失1，待认领2，找到3
    private int status;

    public Articles(String id, String name, String position, Date date, String takerId, String avator) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.date = date;
        this.takerId = takerId;
        this.avator = avator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTakerId() {
        return takerId;
    }

    public void setTakerId(String takerId) {
        this.takerId = takerId;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
