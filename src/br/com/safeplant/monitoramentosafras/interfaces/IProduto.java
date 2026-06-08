package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Produto;

import java.util.ArrayList;

/**
 * Define o contrato de operações específicas para a entidade Produto.
 */
public interface IProduto extends IOperacoesPadrao {
    /**
     * Retorna a lista de produtos associados ao usuário autenticado.
     * @return {@link ArrayList} de {@link Produto} pertencentes ao usuário atual
     */
    ArrayList<Produto> pegarMeusProdutos();
    /**
     * Retorna todos os produtos disponíveis no sistema.
     * @return {@link ArrayList} de {@link Produto} com todos os produtos cadastrados
     */
    ArrayList<Produto> exibirProdutos();
}
