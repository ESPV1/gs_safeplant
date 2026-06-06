package br.com.safeplant.monitoramentosafras.enums;

public enum TipoProduto {
    LEGUME("Legume"),
    VEGETAL("Vegetal"),
    FRUTA("Fruta"),
    CEREAL("Cereal"),
    LACTINIO("Lactínio");

    private final String descricao;

    TipoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
