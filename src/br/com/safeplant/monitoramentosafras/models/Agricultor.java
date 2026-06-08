package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.helper.Interacao;
import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.interfaces.IAgricultor;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Representa um agricultor do sistema, estendendo {@link Usuario}
 * com informações pessoais e rurais específicas.
 */
public class Agricultor extends Usuario implements IAgricultor {
    private String agricultorId;
    private String enderecoId;
    private String dataDeNascimento;
    private int idade;
    private String celular;
    private String cpf;
    private static final IDatabase<Agricultor> database = new Database<Agricultor>();

    /**
     * Cria um agricultor completo com todos os dados, incluindo herança de usuário.
     * Utilizado principalmente na desserialização do banco de dados.
     *
     * @param agricultorId     identificador único do agricultor
     * @param ultimoAcessoEm   data/hora do último acesso
     * @param ativo            indica se o usuário está ativo
     * @param senha            senha de acesso
     * @param email            endereço de e-mail
     * @param sobrenome        sobrenome do agricultor
     * @param primeiroNome     primeiro nome do agricultor
     * @param nomeUsuario      nome de usuário para login
     * @param nomeCompleto     nome completo do agricultor
     * @param usuarioId        ID herdado de {@link Usuario}
     * @param enderecoId       ID do endereço associado
     * @param dataDeNascimento data de nascimento no formato DD/MM/YYYY
     * @param idade            idade calculada do agricultor
     * @param celular          número de celular com DDD (11 dígitos)
     * @param cpf              CPF do agricultor (somente números, 11 dígitos)
     */
    public Agricultor(String agricultorId, String ultimoAcessoEm, boolean ativo, String senha, String email, String sobrenome, String primeiroNome, String nomeUsuario, String nomeCompleto, String usuarioId, String enderecoId, String dataDeNascimento, int idade, String celular, String cpf) {
        super(ultimoAcessoEm, ativo, senha, email, sobrenome, primeiroNome, nomeUsuario, nomeCompleto, usuarioId);
        this.agricultorId = agricultorId;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.idade = idade;
    }

    /**
     * Cria um agricultor a partir de dados básicos. A idade é calculada automaticamente.
     *
     * @param nomeCompleto     nome completo do agricultor
     * @param email            endereço de e-mail
     * @param senha            senha de acesso
     * @param nomeUsuario      nome de usuário para login
     * @param enderecoId       ID do endereço associado
     * @param dataDeNascimento data de nascimento no formato DD/MM/YYYY
     * @param celular          número de celular com DDD (11 dígitos)
     * @param cpf              CPF do agricultor (somente números, 11 dígitos)
     */
    public Agricultor(String nomeCompleto, String email, String senha, String nomeUsuario, String enderecoId, String dataDeNascimento, String celular, String cpf) {
        super(nomeCompleto, nomeUsuario, email, senha);
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.cpf = cpf;
        this.idade = calcularIdade();
    }

    /**
     * Cria um agricultor a partir de um {@link Usuario} existente. O ID do agricultor
     * é gerado automaticamente e a idade é calculada com base na data de nascimento.
     *
     * @param usuario          instância de {@link Usuario} com os dados base
     * @param cpf              CPF do agricultor (somente números, 11 dígitos)
     * @param enderecoId       ID do endereço associado
     * @param dataDeNascimento data de nascimento no formato DD/MM/YYYY
     * @param celular          número de celular com DDD (11 dígitos)
     */
    public Agricultor(Usuario usuario, String cpf, String enderecoId, String dataDeNascimento, String celular) {
        super(usuario.getUltimoAcessoEm(), usuario.getAtivo(), usuario.getSenha(), usuario.getEmail(), usuario.getSobrenome(), usuario.getPrimeiroNome(), usuario.getNomeUsuario(), usuario.getNomeCompleto(), usuario.getUsuarioId());
        this.agricultorId = UUID.randomUUID().toString();
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.idade = calcularIdade();
    }

    /**
     * Retorna a data de nascimento do agricultor.
     * @return {@link String} Data de nascimento do Agricultor
     */
    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    /**
     * Define a data de nascimento do agricultor.
     * @param dataDeNascimento {@link String} Data de nascimento do Agricultor
     */
    private void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    /**
     * Retorna o número de celular do agricultor.
     * @return {@link String} Número de celular do Agricultor
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Define o número de celular do agricultor.
     * @param celular {@link String} Número de celular do Agricultor
     */
    private void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Retorna o CPF do agricultor.
     * @return {@link String} CPF do Agricultor
     */
    public String getCpf() {
        return this.cpf;
    }

    /**
     * Define o CPF do agricultor.
     * @param cpf {@link String} CPF do Agricultor
     */
    private void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o identificador do endereço do agricultor.
     * @return {@link String} ID do endereço do Agricultor
     */
    public String getEnderecoId() {
        return enderecoId;
    }

    /**
     * Define o identificador do endereço do agricultor.
     * @param enderecoId {@link String} ID do endereço do Agricultor
     */
    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    /**
     * Retorna o identificador único do agricultor.
     * @return {@link String} ID do Agricultor
     */
    public String getAgricultorId() {
        return agricultorId;
    }

    /**
     * Define o identificador único do agricultor.
     * @param agricultorId {@link String} ID do Agricultor
     */
    private void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    /**
     * Retorna a idade do agricultor.
     * @return int Idade do Agricultor em anos
     */
    public int getIdade() {
        return idade;
    }

    /**
     * Define a idade do agricultor.
     * @param idade int Idade do Agricultor em anos
     */
    private void setIdade(int idade) {
        this.idade = idade;
    }

    /**
     * {@inheritDoc}
     */
    public int calcularIdade() {
        String dataNascimento = getDataDeNascimento();

        if (!Verificador.verificarData(dataNascimento))
            return 0;

        LocalDate nascimento = Interacao.formataDataLocalDate(dataNascimento);
        LocalDate hoje = LocalDate.now();

        int idade = hoje.getYear() - nascimento.getYear();

        if (hoje.getMonthValue() < nascimento.getMonthValue() ||
                (hoje.getMonthValue() == nascimento.getMonthValue() && hoje.getDayOfMonth() < nascimento.getDayOfMonth()))
            idade--;

        return idade;
    }

    /**
     * Busca e retorna o {@link Agricultor} correspondente ao ID de usuário informado.
     *
     * @param usuarioId ID do usuário a ser buscado
     * @return instância de {@link Agricultor} encontrada, ou {@code null} se não existir
     */
    public static Agricultor getAgricultorPorUsuarioId(String usuarioId) {
        try {
            ArrayList<Agricultor> agricultores = database.lerRegistro(Agricultor.class);
            if (agricultores != null) {
                for (Agricultor agro : agricultores) {
                    if (agro.getUsuarioId().equals(usuarioId))
                        return agro;
                }
            }

            return null;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro Inesperado durante a busca pelo agricultor");
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     * Valida: CPF, tamanho e formato do celular, e intervalo válido da data de nascimento.
     */
    public ArrayList<String> verificarRegistro() {
        ArrayList<String> erros = new ArrayList<String>();

        if (!Verificador.verificarCPF(getCpf()))
            erros.add("CPF inválido. Formatação adequada: deve ter 11 caractéres e somente números.");

        if (getCelular().length() != 11)
            erros.add("Celular deve ter 11 caractéres");

        if (getCelular().charAt(2) != '9')
            erros.add("Celular deve começar com 9");

        if (Verificador.verificarData(getDataDeNascimento())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate cast = LocalDate.parse(getDataDeNascimento(), formatter);

            int ano = cast.getYear();
            if (ano < LocalDate.now().getYear() - 120 || ano > LocalDate.now().getYear() - 10)
                erros.add("Data de nascimento inválida");
        }
        else
            erros.add("Formatação de data de nascimento inválida: DD/MM/YYYY");

        return erros;
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return getAgricultorId();
    }

    /**
     * {@inheritDoc}
     */
    public void exibirMeuPerfil() {
        System.out.flush();
        System.out.println("\033[1;32m\n\n=====| INFORMAÇÕES PESSOAIS |=====\033[m");
        System.out.printf("Nome Completo: %s\n", getNomeCompleto());
        System.out.printf("Data de Nascimento: %s\n", getDataDeNascimento());
        System.out.printf("Idade: %d anos\n", getIdade());
        System.out.printf("Nome de Usuário: %s\n", getNomeUsuario());
        System.out.printf("CPF: %s\n", getCpf());
        System.out.printf("Celular: %s", getCelular());

        System.out.println("\033[1;32m\n\n=====| CREDENCIAIS DE ACESSO |=====\033[m");
        System.out.printf("Email: %s\n", getEmail());
        System.out.printf("Senha: %s\n", getSenha().substring(0, 2) + "**" + getSenha().substring(getSenha().length()-2));

        System.out.println("\033[1;32m\n=====| ENDEREÇO DO AGRICULTOR |=====\033[m");
        Endereco meuEndereco = Endereco.buscarPorId(getEnderecoId());
        if (meuEndereco == null) {
            System.out.println("Agricultor sem endereço cadastrado!");
            return;
        }
        meuEndereco.exibirInfosCep(true);
        System.out.printf("Complemento: %s\n", meuEndereco.getComplemento());
        System.out.printf("Número: %s\n", meuEndereco.getNumero());
    }

    /**
     * {@inheritDoc}
     */
    public boolean adicionar() {
        try {
            return database.criarRegistro(this, Agricultor.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro Inesperado durante a busca pelo agricultor");
            throw ex;
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
        try {
            return database.editarRegistro(this, Agricultor.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro Inesperado durante a busca pelo agricultor");
            throw ex;
        }
    }
}
