package br.com.safeplant.monitoramentosafras.interfaces;

import java.util.ArrayList;

/**
 * Define o contrato genérico de persistência de dados para as entidades do sistema.
 *
 * @param <T> tipo da entidade gerenciada
 */
public interface IDatabase<T> {
    /**
     * Lê e retorna todos os registros do tipo especificado.
     * @param classe classe do tipo {@code T} usada para desserialização dos registros
     * @return {@link ArrayList} contendo os registros encontrados
     */
    ArrayList<T> lerRegistro(Class<T> classe);
    /**
     * Cria e persiste um novo registro no banco de dados.
     * @param entidade objeto do tipo {@code T} a ser salvo
     * @param classe   classe do tipo {@code T} usada para serialização
     * @return {@code true} se o registro foi criado com sucesso, {@code false} caso contrário
     */
    boolean criarRegistro(T entidade, Class<T> classe);
    /**
     * Atualiza um registro existente no banco de dados.
     * @param entidade objeto do tipo {@code T} com os dados atualizados
     * @param classe   classe do tipo {@code T} usada para serialização
     * @return {@code true} se o registro foi editado com sucesso, {@code false} caso contrário
     */
    boolean editarRegistro(T entidade, Class<T> classe);
    /**
     * Remove um registro existente do banco de dados.
     * @param entidade objeto do tipo {@code T} a ser removido
     * @param classe   classe do tipo {@code T} usada para identificação
     * @return {@code true} se o registro foi removido com sucesso, {@code false} caso contrário
     */
    boolean removerRegistro(T entidade, Class<T> classe);
    /**
     * Converte uma string JSON em um objeto Java do tipo especificado.
     * @param json   {@link String} no formato JSON a ser convertida
     * @param classe classe do tipo {@code T} para mapeamento do JSON
     * @return instância de {@code T} populada com os dados do JSON
     */
    T converterJsonParaJava(String json, Class<T> classe);
}
