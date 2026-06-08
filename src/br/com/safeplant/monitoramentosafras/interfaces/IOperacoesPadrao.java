package br.com.safeplant.monitoramentosafras.interfaces;

import java.util.ArrayList;

public interface IOperacoesPadrao {
    boolean adicionar();
    boolean remover();
    boolean editar();
    ArrayList<String> verificarRegistro();
    String getId();
}
