package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Usuario;

import java.util.ArrayList;

public interface IUsuario extends IOperacoesPadrao {
    boolean autenticar(String usuario, String senha);
    void definirUsuarioAutenticado(Usuario usuario);
    void separacaoNomeCompleto(String nomeCompleto);
}
