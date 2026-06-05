package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Usuario;

import java.util.ArrayList;

public interface IUsuario {
    ArrayList<String> verificarUsuario();
    boolean autenticar(String usuario, String senha);
    boolean salvarRegistro();
    void definirUsuarioAutenticado(Usuario usuario);
    void separacaoNomeCompleto(String nomeCompleto);
}
