package com.juane.peapyoung.entity;

public class ReceiveBody {
    private Administrateur administrateur;
    private String id;

    public ReceiveBody() {
    }

    public ReceiveBody(Administrateur administrateur, String id) {
        this.administrateur = administrateur;
        this.id = id;
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
