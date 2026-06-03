package br.com.safeplant.enums;

public enum StatusSafra {

    PLANEJADA("Planejada"),
    EM_ANDAMENTO("Em Andamento"),
    COLHEITA("Em Colheita"),
    FINALIZADA("Finalizada"),
    EM_ALERTA("Em Alerta"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusSafra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
