package br.com.safeplant.models;

import br.com.safeplant.interfaces.IUsuario;

import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Usuario implements IUsuario {
    private String id;
    private String nomeCompleto;
    private String nomeUsuario;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean temMfa;
    private boolean ativo;
    private LocalDateTime ultimoAcessoEm;

    public Usuario() {}

    // Construtor teste somente até ser possível ler usuário do JSON
    public Usuario(String nomeUsuario, String senha){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public Usuario(String id, String nomeCompleto, String nomeUsuario, String email, String senha) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.ultimoAcessoEm = LocalDateTime.now();
    }

    public Usuario(String nomeCompleto, String email, String senha, boolean temMfa, boolean ativo) {
        this.id = UUID.randomUUID().toString();
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.temMfa = temMfa;
        this.ativo = ativo;
        this.ultimoAcessoEm = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isTemMfa() {
        return temMfa;
    }

    public void setTemMfa(boolean temMfa) {
        this.temMfa = temMfa;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getUltimoAcessoEm() {
        return ultimoAcessoEm;
    }

    public void setUltimoAcessoEm(LocalDateTime ultimoAcessoEm) {
        this.ultimoAcessoEm = ultimoAcessoEm;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public boolean autenticarUsuario(String usuario, String senha) {
        if (senha.isEmpty())
            return false;
        String senhaformatada = senha.trim();

        if (!usuario.equals("luis.mariano") || !senhaformatada.equals("123"))
            return false;
        this.nomeUsuario = usuario;
        this.senha = senha;
        return true;
    }

    public boolean adicionar() {
        return true;
    }

    public boolean remover() {
        return true;
    }

    public boolean editar() {
        return false;
    }

    public boolean exibir() {
        return false;
    }

    public boolean verificarUsuario() {
        return true;
    }

    public ArrayList<Usuario> lerTodosUsuarios() {
        try {
            URL url = Usuario.class.getClassLoader().getResource("db_user.json");
            String jsonUsers = Files.readString(Paths.get(url.toURI()), StandardCharsets.UTF_8);

            return null;
        }
        catch (Exception ex) {
            return null;
        }

    }

}
