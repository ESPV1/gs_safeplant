package br.com.safeplant.models;

public class Terreno {
    private String id;
    private double area;
    private Localizacao localizacaoId;

    public Terreno(String id, double area, Localizacao localizacaoId) {
        this.id = id;
        this.area = area;
        this.localizacaoId = localizacaoId;
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

    public Localizacao getLocalizacaoId() {
        return localizacaoId;
    }

    public void setLocalizacaoId(Localizacao localizacaoId) {
        this.localizacaoId = localizacaoId;
    }
}
