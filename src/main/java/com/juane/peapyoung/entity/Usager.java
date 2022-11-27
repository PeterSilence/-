package com.juane.peapyoung.entity;

public class Usager {
    private String id;
    private String sId;
    private Character sex;
    private String phone;
    private String avator;

    //代表账号现存状态：0停用，1审核中功能受限，2正常使用
    private Integer status;

    public Usager(String id, String sId, Character sex, String phone, String avator) {
        this.id = id;
        this.sId = sId;
        this.sex = sex;
        this.phone = phone;
        this.avator = avator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
