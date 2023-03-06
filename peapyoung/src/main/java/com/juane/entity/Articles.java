package com.juane.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Articles implements Serializable {
    private Long id;
    private String name;
    private String position;
    private LocalDate lost_time;
    private String takerId;
    private String avator;

    private String owner;

    //物品具备三种状态：丢失1，待认领2，找到3
    private Integer status;

    private LocalDate gmt_create;

    private LocalDate gmt_modified;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDate gmt_create) {
        this.gmt_create = gmt_create;
    }

    public LocalDate getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(LocalDate gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public Articles() {
    }

    public Articles(Long id, String name, String position,
                    LocalDate date, String takerId, String avator,
                    int status, String owner) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.lost_time = date;
        this.takerId = takerId;
        this.avator = avator;
        this.owner = owner;
        this.status = status;
    }

    public LocalDate getLost_time() {
        return lost_time;
    }

    public void setLost_time(LocalDate lost_time) {
        this.lost_time = lost_time;
    }

    public String buildKey(){
        return "articles" + this.name + "_" + this.position + "_" +
                this.lost_time + "_" + this.status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public void setDate(LocalDate date) {
        this.lost_time = date;
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
        return this.status;
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

    @Override
    public String toString() {
        return "Articles{" +
                "id=" + this.id +
                ",name=" + this.name +
                ",position=" + this.position +
                ",takerId=" + this.takerId +
                ",lost_time=" + this.lost_time +
                ",status=" + this.status  +
                ",owner=" + this.owner + "}";
    }
}
