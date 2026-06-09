package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Representa um usuário do sistema, contendo credenciais de acesso
 * e informações pessoais básicas.
 */
public class Usuario implements IUsuario {
    private String usuarioId;
    private String nomeCompleto;
    private String nomeUsuario;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean ativo;
    private String ultimoAcessoEm;
    private final transient IDatabase<Usuario> database;

    /**
     * Construtor padrão. Inicializa o banco de dados para operações de persistência.
     */
    public Usuario() {
        this.database = new Database<Usuario>();
    }

    /**
     * Cria um usuário com nome completo, separando automaticamente primeiro nome e sobrenome.
     * Define o último acesso como o momento atual e marca o usuário como ativo.
     *
     * @param nomeCompleto nome completo do usuário
     * @param nomeUsuario  nome de usuário para login
     * @param email        endereço de e-mail
     * @param senha        senha de acesso
     */
    public Usuario(String nomeCompleto, String nomeUsuario, String email, String senha) {
        this.usuarioId = UUID.randomUUID().toString();
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.ultimoAcessoEm = LocalDateTime.now().toString();
        this.ativo = true;
        this.database = new Database<Usuario>();
        this.separacaoNomeCompleto(nomeCompleto);
    }

    /**
     * Cria um usuário com credenciais básicas, sem separação de nome completo.
     *
     * @param email       endereço de e-mail
     * @param senha       senha de acesso
     * @param nomeUsuario nome de usuário para login
     */
    public Usuario(String email, String senha, String nomeUsuario) {
        this.usuarioId = UUID.randomUUID().toString();
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
        this.ultimoAcessoEm = LocalDateTime.now().toString();
        this.database = new Database<Usuario>();
    }

    /**
     * Cria um usuário com todos os campos explicitamente definidos.
     * Utilizado principalmente na desserialização do banco de dados.
     *
     * @param ultimoAcessoEm data/hora do último acesso
     * @param ativo          indica se o usuário está ativo
     * @param senha          senha de acesso
     * @param email          endereço de e-mail
     * @param sobrenome      sobrenome do usuário
     * @param primeiroNome   primeiro nome do usuário
     * @param nomeUsuario    nome de usuário para login
     * @param nomeCompleto   nome completo do usuário
     * @param usuarioId      identificador único do usuário
     */
    public Usuario(String ultimoAcessoEm, boolean ativo, String senha, String email, String sobrenome, String primeiroNome, String nomeUsuario, String nomeCompleto, String usuarioId) {
        this.usuarioId = usuarioId;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.ultimoAcessoEm = ultimoAcessoEm;
        this.ativo = ativo;
        this.database = new Database<Usuario>();
        separacaoNomeCompleto(nomeCompleto);
    }

    /**
     * Retorna o identificador único do usuário.
     * @return {@link String} ID do usuário
     */
    public String getUsuarioId() {
        return this.usuarioId;
    }

    /**
     * Define o identificador único do usuário.
     * @param usuarioId {@link String} ID do usuário
     */
    private void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Retorna o nome completo do usuário.
     * @return {@link String} Nome completo do usuário
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * Define o nome completo do usuário.
     * @param nomeCompleto {@link String} Nome completo do usuário
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * Retorna o e-mail do usuário.
     * @return {@link String} E-mail do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do usuário.
     * @param email {@link String} E-mail do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a senha do usuário.
     * @return {@link String} Senha do usuário
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     * @param senha {@link String} Senha do usuário
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna se o usuário está ativo no sistema.
     * @return boolean {@code true} se o usuário está ativo, {@code false} caso contrário
     */
    public boolean getAtivo() {
        return ativo;
    }

    /**
     * Define se o usuário está ativo no sistema.
     * @param ativo boolean {@code true} para ativar o usuário, {@code false} para desativar
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Retorna a data e hora do último acesso do usuário.
     * @return {@link String} Data e hora do último acesso
     */
    public String getUltimoAcessoEm() {
        return ultimoAcessoEm;
    }

    /**
     * Define a data e hora do último acesso do usuário.
     * @param ultimoAcessoEm {@link String} Data e hora do último acesso
     */
    public void setUltimoAcessoEm(String ultimoAcessoEm) {
        this.ultimoAcessoEm = ultimoAcessoEm;
    }

    /**
     * Retorna o sobrenome do usuário.
     * @return {@link String} Sobrenome do usuário
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Define o sobrenome do usuário.
     * @param sobrenome {@link String} Sobrenome do usuário
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    /**
     * Retorna o nome de usuário para login.
     * @return {@link String} Nome de usuário
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Define o nome de usuário para login.
     * @param nomeUsuario {@link String} Nome de usuário
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * Retorna o primeiro nome do usuário.
     * @return {@link String} Primeiro nome do usuário
     */
    public String getPrimeiroNome() {
        return primeiroNome;
    }

    /**
     * Define o primeiro nome do usuário.
     * @param primeiroNome {@link String} Primeiro nome do usuário
     */
    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     * Valida: tamanho da senha, formato do e-mail, nome completo sem números
     * e nome de usuário não iniciando com dígito.
     */
    public ArrayList<String> verificarRegistro() {
        ArrayList<String> erros = new ArrayList<String>();

        if (getSenha().length() <= 3)
            erros.add("Senha deve ter mais que 3 caractéres");

        if (getEmail().isEmpty())
            erros.add("Email é obrigatório");

        if (!(getEmail().contains("@") &&
                getEmail().endsWith(".com") ||
                getEmail().endsWith(".br") ||
                getEmail().endsWith(".net"))) {
            erros.add("Email possuí formato inválido");
        }

        if (getNomeCompleto().isEmpty())
            erros.add("Nome completo é obrigatório");

        if (getNomeCompleto().matches(".*\\d+.*"))
            erros.add("Nome completo não pode haver números");

        if (getNomeUsuario().isEmpty())
            erros.add("Nome de usuário é obrigatório");

        if (getNomeUsuario().substring(0, 1).matches(".*\\d+.*"))
            erros.add("Nome de usuário não deve começar com números");

        return erros;
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return getUsuarioId();
    }

    /**
     * {@inheritDoc}
     */
    public boolean adicionar() {
        try {
            return database.criarRegistro(this, Usuario.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro inesperado durante o registro do usuário");
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean remover() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean editar() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
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
    }

    /**
     * {@inheritDoc}
     */
    public void separacaoNomeCompleto(String nomeCompleto) {
        String[] nomes = nomeCompleto.split(" ");

        String primeiroNome = nomes[0];
        String sobreNome = String.join(" ", Arrays.copyOfRange(nomes, 1, nomes.length));

        setPrimeiroNome(primeiroNome);
        setSobrenome(sobreNome);
    }
}
