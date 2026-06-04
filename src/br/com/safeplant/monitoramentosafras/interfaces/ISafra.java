package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;

public interface ISafra extends IOperacoesPadrao {
    void modificarStatusSafra(StatusSafra status);
}
