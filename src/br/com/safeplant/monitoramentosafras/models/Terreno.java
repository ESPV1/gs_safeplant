package br.com.safeplant.monitoramentosafras.models;

import java.util.UUID;

/**
 * Representa um terreno com localização geográfica e área definida.
 */
public class Terreno extends Localizacao {
    private transient String terrenoId;
    private double area;

    /**
     * Inicializa um terreno com coordenadas geográficas e área.
     * terrenoId é gerado automaticamente.
     * @param latitude  latitude do terreno em graus decimais
     * @param longitude longitude do terreno em graus decimais
     * @param area      área do terreno em metros quadrados
     */
    public Terreno(double latitude, double longitude, double area) {
        super(latitude, longitude);
        this.terrenoId = UUID.randomUUID().toString();
        this.area = area;
    }

    /**
     * Retorna o identificador único do terreno.
     * @return {@link String} representando o ID do terreno
     */
    public String getTerrenoId() {
        return terrenoId;
    }

    /**
     * Define o identificador do terreno.
     * @param terrenoId novo valor do identificador definido
     */
    private void setTerrenoId(String terrenoId) {
        this.terrenoId = terrenoId;
    }

    /**
     * Retorna a área do terreno.
     * @return área em metros quadrados
     */
    public double getArea() {
        return area;
    }

    /**
     * Define a área do terreno.
     * @param area novo valor de área em metros quadrados
     */
    public void setArea(double area) {
        this.area = area;
    }

}
