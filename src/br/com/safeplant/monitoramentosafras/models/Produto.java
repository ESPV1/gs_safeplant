package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.enums.TipoProduto;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IProduto;

import java.util.ArrayList;
import java.util.UUID;

public class Produto implements IProduto {
    private String produtoId;
    private String nome;
    private TipoProduto tipoProduto;
    private String nomeCientifico;
    private int tempoColheitaEmDias;
    private String agricultorId;
    private transient final IDatabase<Produto> database;

    public Produto() {
        this.database = new Database<Produto>();
    }

    public Produto(String nome, String nomeCientifico, int tempoColheitaEmDias, TipoProduto tipoProduto, String agricultorId) {
        this.produtoId = UUID.randomUUID().toString();
        this.nome = nome;
        this.nomeCientifico = nomeCientifico;
        this.tempoColheitaEmDias = tempoColheitaEmDias;
        this.tipoProduto = tipoProduto;
        this.agricultorId = agricultorId;
        this.database = new Database<Produto>();
    }

    public String getProdutoId() {
        return produtoId;
    }

    private void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return this.nome;
    }

    public String getNomeFormatado() {
        if (this.tipoProduto == null)
            return this.nome;

        switch (this.tipoProduto) {
            case LEGUME:  return "\033[1;35m" + this.nome + "\033[m";
            case VEGETAL: return "\033[1;92m" + this.nome + "\033[m";
            case FRUTA:   return "\033[1;33m" + this.nome + "\033[m";
            case CEREAL:  return "\033[1;93m" + this.nome + "\033[m";
            case LACTINIO: return "\033[1;34m" + this.nome + "\033[m";
            default: return this.nome;
        }
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

    public int getTempoColheitaEmDias() {
        return tempoColheitaEmDias;
    }

    public void setTempoColheitaEmDias(int tempoColheitaEmDias) {
        this.tempoColheitaEmDias = tempoColheitaEmDias;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    public boolean adicionar() {
        try {
            ArrayList<Produto> meusProduos = pegarMeusProdutos();
            return database.criarRegistro(this, Produto.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover() {
        return false;
    }

    public boolean editar() {
        return false;
    }

    public ArrayList<Produto> exibirProdutos() {
        try {
            return database.lerRegistro(Produto.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Produto>();
        }
    }

    public ArrayList<String> verificarRegistro() {
        ArrayList<String> erros = new ArrayList<String>();

        if (getNome().length() < 3)
            erros.add("O nome do produto deve ter pelo menos 3 caractéres.");

        if (getNomeCientifico().length() <= 5)
            erros.add("O nome científico do produto deve ter pelo menos 3 caractéres.");

        if (getNomeCientifico().equalsIgnoreCase(getNome()))
            erros.add("O nome científico não pode ser igual ao nome ");

        if (getNomeCientifico().split(" ").length != 2)
            erros.add("Não é um nome científico válido");

        if (getTempoColheitaEmDias() <= 0)
            erros.add("Tempo de Colheita deve ser superior a zero");

        return erros;
    }

    public ArrayList<Produto> pegarMeusProdutos() {
        ArrayList<Produto> produtos = exibirProdutos();
        if (produtos.isEmpty())
            return new ArrayList<Produto>();

        ArrayList<Produto> meusProdutos = new ArrayList<Produto>();
        for (Produto prod : produtos) {
            if (prod.getAgricultorId().equals(this.agricultorId))
                meusProdutos.add(prod);
        }
        return meusProdutos;
    }

    public ArrayList<Produto> pegarMeusProdutos(String agricultorId) {
        ArrayList<Produto> produtos = exibirProdutos();
        if (produtos.isEmpty())
            return new ArrayList<Produto>();

        ArrayList<Produto> meusProdutos = new ArrayList<Produto>();
        for (Produto prod : produtos) {
            if (prod.getAgricultorId().equals(agricultorId))
                meusProdutos.add(prod);
        }
        return meusProdutos;
    }
    public ArrayList<Produto> pegarMeusProdutos(String agricultorId, ArrayList<Produto> filtros) {
        ArrayList<Produto> produtos = exibirProdutos();
        if (produtos.isEmpty())
            return new ArrayList<Produto>();

        ArrayList<Produto> meusProdutos = new ArrayList<Produto>();
        for (Produto prod : produtos) {
            if (prod.getAgricultorId().equals(agricultorId) && (filtros.isEmpty() || !Produto.existeProduto(filtros, prod))) {
                meusProdutos.add(prod);
            }
        }
        return meusProdutos;
    }

    private static boolean existeProduto(ArrayList<Produto> produtos, Produto prodAEncontrar) {
        for (Produto prodF : produtos) {
            if (prodAEncontrar.getProdutoId().equalsIgnoreCase(prodF.getProdutoId()))
                return true;
        }
        return false;
    }

    public String getId() {
        return getProdutoId();
    }
}
