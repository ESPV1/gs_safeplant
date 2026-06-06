package br.com.safeplant.monitoramentosafras.interfaces;

import java.util.ArrayList;

public interface IDatabase<T> {
    ArrayList<T> lerRegistro(Class<T> classe);
    boolean criarRegistro(T entidade, Class<T> classe);
    boolean editarRegistro(T entidade, Class<T> classe);
    boolean removerRegistro(T entidade, Class<T> classe);
    T converterJsonParaJava(String json, Class<T> classe);
}
