package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.ILocalizacao;

import java.util.UUID;

/**
 * Representa as coordenadas geográficas de latitude e longitude de um ponto no mapa.
 */
public class Localizacao implements ILocalizacao {
    private transient String localizaId;
    private double latitude;
    private double longitude;

    /**
     * Inicializa uma nova localização com as coordenadas fornecidas.
     * localizaId é gerado automaticamente.
     * @param latitude  valor da latitude da localização
     * @param longitude valor da longitude da localização
     */
    public Localizacao(double latitude, double longitude) {
        this.localizaId = UUID.randomUUID().toString();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Retorna o valor do identificador da localização.
     * @return identificador da localização
     */
    public String getLocalizaId() {
        return localizaId;
    }

    /**
     * Define o valor do identificador da localização.
     * @param localizaId novo valor do identificador definido
     */
    private void setLocalizaId(String localizaId) {
        this.localizaId = localizaId;
    }

    /**
     * Retorna o valor da latitude desta localização.
     * @return latitude em graus decimais
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Define o valor da latitude desta localização.
     * @param latitude novo valor de latitude em graus decimais
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Retorna o valor da longitude desta localização.
     * @return longitude em graus decimais
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Define o valor da longitude desta localização.
     * @param longitude novo valor de longitude em graus decimais
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
