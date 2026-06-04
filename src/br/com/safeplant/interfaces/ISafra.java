package br.com.safeplant.interfaces;

import br.com.safeplant.enums.StatusSafra;

public interface ISafra extends IOperacoesPadrao {
    void modificarStatusSafra(StatusSafra status);
}
