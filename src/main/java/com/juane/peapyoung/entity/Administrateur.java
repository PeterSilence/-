package com.juane.peapyoung.entity;

public class Administrateur {
    private String id;
    private String name;
    private String phone;
    private Integer status;
    private String password;

    public Administrateur() {
    }

    public Administrateur(String id, String name, String phone, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
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
