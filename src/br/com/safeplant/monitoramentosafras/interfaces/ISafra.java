package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;

/**
 * Define o contrato de operações específicas para a entidade Safra.
 */
public interface ISafra extends IOperacoesPadrao {
    /**
     * Modifica o status atual da safra.
     * @param status novo {@link StatusSafra} a ser atribuído à safra
     */
    void modificarStatusSafra(StatusSafra status);
}
