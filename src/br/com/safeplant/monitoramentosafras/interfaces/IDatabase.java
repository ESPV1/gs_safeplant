package br.com.safeplant.monitoramentosafras.interfaces;

import java.util.ArrayList;

public interface IDatabase<T> {
    ArrayList<T> lerRegistro(Class<T> classe);
    boolean criarRegistro(T entidade);
    boolean editarRegistro(T entidade);
    boolean removerRegistro(T entidade);
}
