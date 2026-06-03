package br.com.safeplant.models;

import br.com.safeplant.interfaces.IUsuario;
import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario implements IUsuario {
    private String id;
    private String nomeCompleto;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean temMfa;
    private boolean ativo;
    private LocalDateTime ultimoAcessoEm;

    public Usuario(String nomeCompleto, String email, String senha) {
        this.id = UUID.randomUUID().toString();
        this.nomeCompleto = nomeCompleto;
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

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public String EncriptarSenha(String senha) {
        return "";
    }

    @Override
    public String DecriptarSenha(String senha) {
        return "";
    }
}
