package br.com.safeplant.monitoramentosafras.interfaces;

/**
 * Define o contrato de operações específicas para a entidade Agricultor.
 */
public interface IAgricultor extends IOperacoesPadrao {
    /**
     * Exibe as informações do perfil do agricultor autenticado.
     */
    void exibirMeuPerfil();
    /**
     * Calcula e retorna a idade do agricultor com base na data de nascimento.
     * @return inteiro representando a idade do agricultor em anos
     */
    int calcularIdade();
}
