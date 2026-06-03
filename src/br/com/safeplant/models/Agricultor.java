package br.com.safeplant.models;

import br.com.safeplant.interfaces.IAgricultor;
import java.time.LocalDateTime;
import java.util.UUID;

public class Agricultor implements IAgricultor {
    private String id;
    private String usuarioId;
    private String enderecoId;
    private LocalDateTime dataDeNascimento;
    private String celular;
    private String cpf;

    public Agricultor(String usuarioId, String enderecoId, LocalDateTime dataDeNascimento, String celular, String cpf) {
        this.id = UUID.randomUUID().toString();
        this.usuarioId = usuarioId;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    private void setId(String value) {
        this.id = value;
    }

    public LocalDateTime getDataDeNascimento() {
        return dataDeNascimento;
    }

    private void setDataDeNascimento(LocalDateTime dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCelular() {
        return celular;
    }

    private void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return this.cpf;
    }

    private void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }
}
