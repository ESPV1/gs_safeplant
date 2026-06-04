package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Usuario implements IUsuario {
    private String usuarioId;
    private String nomeCompleto;
    private String nomeUsuario;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean temMfa;
    private boolean ativo;
    private LocalDateTime ultimoAcessoEm;
    private final IDatabase<Usuario> database;

    public Usuario() {
        this.database = new Database<Usuario>();
    }

    public Usuario(String nomeCompleto, String nomeUsuario, String email, String senha) {
        this.usuarioId = UUID.randomUUID().toString();
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.ultimoAcessoEm = LocalDateTime.now();
        this.temMfa = false;
        this.ativo = true;
        this.database = new Database<Usuario>();
        this.separacaoNomeCompleto(nomeCompleto);
    }

    public Usuario(String email, String senha, String nomeUsuario, boolean temMfa) {
        this.usuarioId = UUID.randomUUID().toString();
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.temMfa = temMfa;
        this.ativo = true;
        this.ultimoAcessoEm = LocalDateTime.now();
        this.database = new Database<Usuario>();
    }

    public Usuario(LocalDateTime ultimoAcessoEm, boolean ativo, boolean temMfa, String senha, String email, String sobrenome, String primeiroNome, String nomeUsuario, String nomeCompleto, String usuarioId) {
        this.usuarioId = usuarioId;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.ultimoAcessoEm = ultimoAcessoEm;
        this.ativo = ativo;
        this.temMfa = temMfa;
        this.database = new Database<Usuario>();
        separacaoNomeCompleto(nomeCompleto);
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    private void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
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

    public boolean autenticar(String usuario, String senha) {
        if (senha.isEmpty())
            return false;
        String senhaformatada = senha.trim();

        ArrayList<Usuario> usuarios = database.lerRegistro(Usuario.class);
        if (usuarios == null) {
            System.out.println("Não foi possível encontrar os usuários no sistema.");
            return false;
        }

        for (Usuario user : usuarios) {
            if (user.nomeUsuario.equals(usuario) && user.senha.equals(senhaformatada) && user.ativo) {
                definirUsuarioAutenticado(user);
                return true;
            }
        }
        return false;
    }

    public boolean verificarUsuario() {
        if (getSenha().length() <= 3)
            return false;

        if (!(getEmail().contains("@") &&
                getEmail().endsWith(".com") ||
                getEmail().endsWith(".br") ||
                getEmail().endsWith(".net"))) {
            return false;
        }

        if (getNomeCompleto().matches(".*\\d+.*"))
            return false;

        if (getNomeUsuario().substring(0, 1).matches(".*\\d+.*"))
            return false;

        return true;
    }

    public boolean salvarRegistro() {
        boolean usuarioValido = verificarUsuario();
        if (!usuarioValido)
            return false;

        System.out.println("Usuário cadastrado!");
        return true;
    }

    public void definirUsuarioAutenticado(Usuario usuario) {
        setUsuarioId(usuario.usuarioId);
        setNomeCompleto(usuario.nomeCompleto);
        setPrimeiroNome(usuario.primeiroNome);
        setSobrenome(usuario.sobrenome);
        setNomeUsuario(usuario.nomeUsuario);
        setEmail(usuario.email);
        setSenha(usuario.senha);
        setUltimoAcessoEm(usuario.ultimoAcessoEm);
        setAtivo(usuario.ativo);
        setTemMfa(usuario.temMfa);
    }

    public void separacaoNomeCompleto(String nomeCompleto) {
        String[] nomes = nomeCompleto.split(" ");

        String primeiroNome = nomes[0];
        String sobreNome = String.join(" ", Arrays.copyOfRange(nomes, 1, nomes.length));

        setPrimeiroNome(primeiroNome);
        setSobrenome(sobreNome);
    }
}
