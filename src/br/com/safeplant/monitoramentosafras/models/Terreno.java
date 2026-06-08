package br.com.safeplant.monitoramentosafras.models;

import java.util.UUID;

public class Terreno extends Localizacao {
    private transient String terrenoId;
    private double area;

    public Terreno(double latitude, double longitude, double area) {
        super(latitude, longitude);
        this.terrenoId = UUID.randomUUID().toString();
        this.area = area;
    }

    public String getTerrenoId() {
        return terrenoId;
    }

    private void setTerrenoId(String terrenoId) {
        this.terrenoId = terrenoId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

}
