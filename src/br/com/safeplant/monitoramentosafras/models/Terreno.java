package br.com.safeplant.monitoramentosafras.models;

import java.util.UUID;

public class Terreno extends Localizacao {
    private String id;
    private double area;

    public Terreno(double latitude, double longitude, double area) {
        super(latitude, longitude);
        this.id = UUID.randomUUID().toString();
        this.area = area;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

}
