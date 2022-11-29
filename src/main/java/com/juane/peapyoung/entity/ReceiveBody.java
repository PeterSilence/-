package com.juane.peapyoung.entity;

public class ReceiveBody {
    private Administrateur administrateur;

    private Articles articles;
    private String id;

    public ReceiveBody() {
    }

    public ReceiveBody(Administrateur administrateur, String id) {
        this.administrateur = administrateur;
        this.id = id;
    }

    public ReceiveBody(Articles articles, String id) {
        this.articles = articles;
        this.id = id;
    }

    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
