package br.com.safeplant.monitoramentosafras.enums;

/**
 * Enum que representa os tipos de produtos cultiváveis no sistema.
 */
public enum TipoProduto {
    LEGUME("Legume"),
    VEGETAL("Vegetal"),
    FRUTA("Fruta"),
    CEREAL("Cereal"),
    LACTINIO("Lactínio");

    private final String descricao;

    /**
     * Inicializa o tipo de produto com sua descrição legível.
     *
     * @param descricao {@link String} descrição do tipo de produto
     */
    TipoProduto(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição legível do tipo de produto.
     * @return {@link String} descrição do tipo de produto
     */
    public String getDescricao() {
        return descricao;
    }
}
