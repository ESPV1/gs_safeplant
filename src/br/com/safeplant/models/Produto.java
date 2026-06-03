package br.com.safeplant.models;

import br.com.safeplant.interfaces.IProduto;

import java.util.UUID;

public class Produto implements IProduto {
    private final String id;
    private String nome;
    private String nomeCientifico;
    private String tempoColheitaEmDias;

    public Produto(String nome, String nomeCientifico, String tempoColheitaEmDias) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.nomeCientifico = nomeCientifico;
        this.tempoColheitaEmDias = tempoColheitaEmDias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getTempoColheitaEmDias() {
        return tempoColheitaEmDias;
    }

    public void setTempoColheitaEmDias(String tempoColheitaEmDias) {
        this.tempoColheitaEmDias = tempoColheitaEmDias;
    }
}
