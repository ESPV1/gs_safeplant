package br.com.safeplant.monitoramentosafras.enums;

/**
 * Enum que representa os possíveis status de uma safra no sistema.
 */
public enum StatusSafra {

    PLANEJADA("Planejada"),
    EM_ANDAMENTO("Em Andamento"),
    COLHEITA("Em Colheita"),
    FINALIZADA("Finalizada"),
    EM_ALERTA("Em Alerta"),
    CANCELADA("Cancelada");

    private final String descricao;

    /**
     * Inicializa o status da safra com sua descrição legível.
     *
     * @param descricao {@link String} descrição do status da safra
     */
    StatusSafra(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição legível do status da safra.
     * @return {@link String} descrição do status da safra
     */
    public String getDescricao() {
        return descricao;
    }
}
