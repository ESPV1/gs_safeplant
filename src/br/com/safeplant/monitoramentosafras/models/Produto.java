package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.enums.TipoProduto;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IProduto;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Representa um produto agrícola cadastrado no sistema,
 * contendo informações de identificação, tipo e tempo de colheita.
 */
public class Produto implements IProduto {
    private String produtoId;
    private String nome;
    private TipoProduto tipoProduto;
    private String nomeCientifico;
    private int tempoColheitaEmDias;
    private String agricultorId;
    private transient final IDatabase<Produto> database;

    /**
     * Inicializa um produto sem dados, apenas com o banco de dados configurado.
     * Utilizado para operações de consulta.
     */
    public Produto() {
        this.database = new Database<Produto>();
    }

    /**
     * Inicializa um produto com todos os dados necessários para cadastro.
     * produtoId é gerado automaticamente.
     *
     * @param nome                 nome popular do produto
     * @param nomeCientifico       nome científico do produto
     * @param tempoColheitaEmDias  tempo estimado até a colheita em dias
     * @param tipoProduto          categoria do produto conforme {@link TipoProduto}
     * @param agricultorId         ID do agricultor dono do produto
     */
    public Produto(String nome, String nomeCientifico, int tempoColheitaEmDias, TipoProduto tipoProduto, String agricultorId) {
        this.produtoId = UUID.randomUUID().toString();
        this.nome = nome;
        this.nomeCientifico = nomeCientifico;
        this.tempoColheitaEmDias = tempoColheitaEmDias;
        this.tipoProduto = tipoProduto;
        this.agricultorId = agricultorId;
        this.database = new Database<Produto>();
    }

    /**
     * Retorna o identificador único do produto.
     * @return {@link String} ID do produto
     */
    public String getProdutoId() {
        return produtoId;
    }

    /**
     * Define o identificador único do produto.
     * @param produtoId {@link String} ID do produto
     */
    private void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    /**
     * Retorna o nome do produto.
     * @return {@link String} Nome do produto
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do produto.
     * @param nome {@link String} Nome do produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome científico do produto.
     * @return {@link String} Nome científico do produto
     */
    public String getNomeCientifico() {
        return nomeCientifico;
    }

    /**
     * Define o nome científico do produto.
     * @param nomeCientifico {@link String} Nome científico do produto
     */
    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    /**
     * Retorna o tempo estimado de colheita do produto.
     * @return int Tempo de colheita em dias
     */
    public int getTempoColheitaEmDias() {
        return tempoColheitaEmDias;
    }

    /**
     * Define o tempo estimado de colheita do produto.
     * @param tempoColheitaEmDias int Tempo de colheita em dias
     */
    public void setTempoColheitaEmDias(int tempoColheitaEmDias) {
        this.tempoColheitaEmDias = tempoColheitaEmDias;
    }

    /**
     * Retorna o tipo do produto.
     * @return {@link TipoProduto} Tipo do produto
     */
    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    /**
     * Define o tipo do produto.
     * @param tipoProduto {@link TipoProduto} Tipo do produto
     */
    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    /**
     * Retorna o identificador do agricultor dono do produto.
     * @return {@link String} ID do agricultor
     */
    public String getAgricultorId() {
        return agricultorId;
    }

    /**
     * Define o identificador do agricultor dono do produto.
     * @param agricultorId {@link String} ID do agricultor
     */
    public void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    /**
     * Retorna o nome do produto formatado com cor ANSI de acordo com seu {@link TipoProduto}.
     * Caso o tipo não esteja definido, retorna o nome sem formatação.
     * @return {@link String} com o nome formatado para exibição no terminal
     */
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

    /**
     * {@inheritDoc}
     * Verifica se o produto já existe antes de adicionar.
     */
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

    /**
     * {@inheritDoc}
     * Retorna todos os produtos cadastrados no banco de dados.
     */
    public ArrayList<Produto> exibirProdutos() {
        try {
            return database.lerRegistro(Produto.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Produto>();
        }
    }

    /**
     * {@inheritDoc}
     *
     * Valida: nome mínimo de 3 caracteres, nome científico com formato binomial
     * distinto do nome popular, e tempo de colheita superior a zero.
     */
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

    /**
     * {@inheritDoc}
     *
     * Filtra os produtos pelo {@code agricultorId} do próprio objeto.
     */
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

    /**
     * Retorna os produtos pertencentes ao agricultor informado.
     *
     * @param agricultorId ID do agricultor cujos produtos serão retornados
     * @return {@link ArrayList} de {@link Produto} do agricultor especificado
     */
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

    /**
     * Retorna os produtos do agricultor informado, excluindo os presentes na lista de filtros.
     *
     * @param agricultorId ID do agricultor cujos produtos serão retornados
     * @param filtros      lista de {@link Produto} a serem excluídos do resultado
     * @return {@link ArrayList} de {@link Produto} filtrada
     */
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

    /** {@inheritDoc} */
    public String getId() {
        return getProdutoId();
    }
}
