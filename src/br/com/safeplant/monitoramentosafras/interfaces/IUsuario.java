package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Usuario;

import java.util.ArrayList;

/**
 * Define o contrato de operações específicas para a entidade Usuario,
 * incluindo autenticação e gerenciamento de sessão.
 */
public interface IUsuario extends IOperacoesPadrao {
    /**
     * Autentica um usuário com base nas credenciais fornecidas.
     * @param usuario nome de usuário para autenticação
     * @param senha   senha correspondente ao usuário
     * @return {@code true} se as credenciais são válidas, {@code false} caso contrário
     */
    boolean autenticar(String usuario, String senha);
    /**
     * Define o usuário atualmente autenticado na sessão.
     * @param usuario instância de {@link Usuario} a ser marcada como autenticada
     */
    void definirUsuarioAutenticado(Usuario usuario);
    /**
     * Separa o nome completo em suas partes (ex: primeiro nome e sobrenome).
     * @param nomeCompleto {@link String} contendo o nome completo do usuário
     */
    void separacaoNomeCompleto(String nomeCompleto);
}
