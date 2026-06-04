package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IProduto;

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

    public void GerenciamentoSafra() {

    }

    public boolean Adicionar() {
        return true;
    }

    public boolean Editar() {
        return true;
    }

    public boolean Remover() {
        return true;
    }

    public void exibirProdutos() {

    }

    @Override
    public boolean adicionar() {
        return false;
    }

    @Override
    public boolean remover() {
        return false;
    }

    @Override
    public boolean editar() {
        return false;
    }

    @Override
    public boolean exibir() {
        return false;
    }
}
