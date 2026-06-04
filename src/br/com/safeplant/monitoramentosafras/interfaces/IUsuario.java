package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Usuario;

public interface IUsuario {
    boolean verificarUsuario();
    boolean autenticar(String usuario, String senha);
    boolean salvarRegistro();
    void definirUsuarioAutenticado(Usuario usuario);
    void separacaoNomeCompleto(String nomeCompleto);
}
