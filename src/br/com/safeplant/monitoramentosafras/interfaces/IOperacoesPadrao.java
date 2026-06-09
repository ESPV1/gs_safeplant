package br.com.safeplant.monitoramentosafras.interfaces;

import java.util.ArrayList;

/**
 * Define o contrato padrão de operações CRUD para as entidades do sistema.
 */
public interface IOperacoesPadrao {
    /**
     * Adiciona um novo registro ao sistema.
     * @return {@code true} se o registro foi adicionado com sucesso, {@code false} caso contrário
     */
    boolean adicionar();
    /**
     * Remove o registro atual do sistema.
     * @return {@code true} se o registro foi removido com sucesso, {@code false} caso contrário
     */
    boolean remover();
    /**
     * Edita as informações do registro atual.
     * @return {@code true} se o registro foi editado com sucesso, {@code false} caso contrário
     */
    boolean editar();
    /**
     * Verifica e retorna os dados do registro atual.
     * @return {@link ArrayList} de {@link String} contendo as informações do registro
     */
    ArrayList<String> verificarRegistro();
    /**
     * Retorna o identificador único do registro.
     * @return {@link String} representando o ID do registro
     */
    String getId();
}
