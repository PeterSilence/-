package com.juane.entity;

import java.io.Serializable;
import java.time.LocalDate;


//实现Serializable接口是为了将此实现类进行序列化存放到redis中
public class Administrateur implements Serializable {
    private String id;
    private String name;
    private String phone;
    private Integer status;
    private String password;

    private  LocalDate gmt_create;

    private  LocalDate gmt_modified;

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

    public Administrateur() {
    }

    public Administrateur(String id, String name, String phone, String password
                            , Integer status, LocalDate gmt_create, LocalDate gmt_modified) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.gmt_create = gmt_create;
        this.gmt_modified = gmt_modified;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + this.id +
                ",password" + this.password +
                ",name=" + this.name +
                ",phone=" + this.phone + '}';
    }
}
