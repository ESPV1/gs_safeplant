package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.ILocalizacao;

import java.util.UUID;

public class Localizacao implements ILocalizacao {
    private transient String localizaId;
    private double latitude;
    private double longitude;

    public Localizacao(double latitude, double longitude) {
        this.localizaId = UUID.randomUUID().toString();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTerrenoId() {
        return localizaId;
    }

    private void setLocalizaId(String localizaId) {
        this.localizaId = localizaId;
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
