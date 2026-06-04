package br.com.safeplant.interfaces;

public interface IUsuario extends IOperacoesPadrao {
    String encriptarSenha(String senha);
    String decriptarSenha(String senha);
    void autenticarUsuario();
    boolean verificarUsuario();
}
