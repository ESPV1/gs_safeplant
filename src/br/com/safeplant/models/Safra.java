package br.com.safeplant.models;

import br.com.safeplant.enums.StatusSafra;
import br.com.safeplant.interfaces.ISafra;

import java.time.LocalDateTime;
import java.util.UUID;

public class Safra implements ISafra {
    private String id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusSafra status;

    public Safra(LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = UUID.randomUUID().toString();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusSafra.PLANEJADA;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
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

    private void setStatus(StatusSafra status) {
        this.status = status;
    }

    public void ModificarStatusSafra(StatusSafra status) {
        setStatus(status);
    }
}
