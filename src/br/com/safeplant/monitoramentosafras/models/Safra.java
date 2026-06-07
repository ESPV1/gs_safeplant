package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;
import br.com.safeplant.monitoramentosafras.interfaces.ISafra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Safra extends Terreno implements ISafra {
    private String id;
    private String nomeSafra;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusSafra status;
    private ArrayList<Produto> cultivados;

    public Safra(String id, String nomeSafra, LocalDateTime dataInicio, LocalDateTime dataFim, StatusSafra status, ArrayList<Produto> cultivados, double latitude, double longitude, double area) {
        super(latitude, longitude, area);
        this.id = id;
        this.nomeSafra = nomeSafra;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.cultivados = cultivados;
    }

    public Safra(String nomeSafra, LocalDateTime dataInicio, LocalDateTime dataFim, double latitude, double longitude, double area) {
        super(latitude, longitude, area);
        this.id = UUID.randomUUID().toString();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusSafra.PLANEJADA;
        this.cultivados = new ArrayList<Produto>();
    }

    public Safra(String nomeSafra, LocalDateTime dataInicio, LocalDateTime dataFim, double latitude, double longitude, double area, ArrayList<Produto> cultivados) {
        super(latitude, longitude, area);
        this.id = UUID.randomUUID().toString();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusSafra.PLANEJADA;
        this.cultivados = cultivados;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getNomeSafra() {
        return nomeSafra;
    }

    public void setNomeSafra(String nomeSafra) {
        this.nomeSafra = nomeSafra;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    private void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    private void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public StatusSafra getStatus() {
        return status;
    }

    public ArrayList<Produto> getCultivados() {
        return cultivados;
    }

    public void setCultivados(ArrayList<Produto> cultivados) {
        this.cultivados = cultivados;
    }

    private void setStatus(StatusSafra status) {
        this.status = status;
    }

    public void modificarStatusSafra(StatusSafra status) {
        setStatus(status);
    }

    public boolean adicionar() {
        return true;
    }

    public boolean editar() {
        return true;
    }

    public boolean exibir() {
        return true;
    }

    public boolean remover() {
        return true;
    }
}
