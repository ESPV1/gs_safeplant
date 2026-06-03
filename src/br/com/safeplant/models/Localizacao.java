package br.com.safeplant.models;

import br.com.safeplant.interfaces.ILocalizacao;

import java.util.UUID;

public class Localizacao implements ILocalizacao {
    private String id;
    private double latitude;
    private double longitude;

    public Localizacao(double latitude, double longitude) {
        this.id = UUID.randomUUID().toString();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
