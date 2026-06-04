package br.com.safeplant.interfaces;

import br.com.safeplant.models.Usuario;

import java.util.ArrayList;

public interface IUsuario extends IOperacoesPadrao {
    boolean autenticarUsuario(String usuario, String senha);
    boolean verificarUsuario();
    ArrayList<Usuario> lerTodosUsuarios();
}
