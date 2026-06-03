package br.com.safeplant.interfaces;

public interface IUsuario {
    String EncriptarSenha(String senha);
    String DecriptarSenha(String senha);
    
}
