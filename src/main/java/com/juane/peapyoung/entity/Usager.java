package com.juane.peapyoung.entity;

import org.springframework.stereotype.Component;

public class Usager {
    private String id;
    private String aid;
    private String name;
    private String sex;
    private String phone;
    private String avator;
    private String password;
    //代表账号现存状态:不必用户填写，系统自动生成
    private Integer status;
    private String schoolPosition;

    public Usager() {
    }

    //这里构造器的参数位置要与数据库中的属性位置相一致，否则会报错
    public Usager(String id, String aid, String name,
                  String sex, String phone,Integer status, String avator,
                  String password,  String schoolPosition) {
        this.id = id;
        this.aid = aid;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.avator = avator;
        this.password = password;
        this.status = status;
        this.schoolPosition = schoolPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getaId() {
        return aid;
    }

    public void setsId(String aId) {
        this.aid = aId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolPosition() {
        return schoolPosition;
    }

    public void setSchoolPosition(String schoolPosition) {
        this.schoolPosition = schoolPosition;
    }
}
