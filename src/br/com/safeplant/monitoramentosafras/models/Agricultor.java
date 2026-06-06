package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.interfaces.IAgricultor;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class Agricultor extends Usuario implements IAgricultor {
    private String agricultorId;
    private String enderecoId;
    private String dataDeNascimento;
    private int idade;
    private String celular;
    private String cpf;
    private static final IDatabase<Agricultor> database = new Database<Agricultor>();

    public Agricultor(String agricultorId, String ultimoAcessoEm, boolean ativo, String senha, String email, String sobrenome, String primeiroNome, String nomeUsuario, String nomeCompleto, String usuarioId, String enderecoId, String dataDeNascimento, int idade, String celular, String cpf) {
        super(ultimoAcessoEm, ativo, senha, email, sobrenome, primeiroNome, nomeUsuario, nomeCompleto, usuarioId);
        this.agricultorId = agricultorId;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.idade = idade;
    }

    public Agricultor(String nomeCompleto, String email, String senha, String nomeUsuario, String enderecoId, String dataDeNascimento, String celular, String cpf) {
        super(nomeCompleto, nomeUsuario, email, senha);
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.cpf = cpf;
        this.idade = calcularIdade();
    }

    public Agricultor(Usuario usuario, String cpf, String enderecoId, String dataDeNascimento, String celular) {
        super(usuario.getUltimoAcessoEm(), usuario.getAtivo(), usuario.getSenha(), usuario.getEmail(), usuario.getSobrenome(), usuario.getPrimeiroNome(), usuario.getNomeUsuario(), usuario.getNomeCompleto(), usuario.getUsuarioId());
        this.agricultorId = UUID.randomUUID().toString();
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.idade = calcularIdade();
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    private void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCelular() {
        return celular;
    }

    private void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return this.cpf;
    }

    private void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int calcularIdade() {
        String dataNascimento = getDataDeNascimento();

        if (!Verificador.verificarDataNascimento(dataNascimento))
            return 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
        LocalDate hoje = LocalDate.now();

        int idade = hoje.getYear() - nascimento.getYear();

        if (hoje.getMonthValue() < nascimento.getMonthValue() ||
                (hoje.getMonthValue() == nascimento.getMonthValue() && hoje.getDayOfMonth() < nascimento.getDayOfMonth()))
            idade--;

        return idade;
    }

    public static Agricultor GetAgricultorPorUsuarioId(String usuarioId) {
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

    public ArrayList<String> verificarAgro() {
        ArrayList<String> erros = new ArrayList<String>();

        if (!Verificador.verificarCPF(getCpf()))
            erros.add("CPF inválido. Formatação adequada: deve ter 11 caractéres e somente números.");

        if (getCelular().length() != 11)
            erros.add("Celular deve ter 11 caractéres");

        if (getCelular().charAt(2) != '9')
            erros.add("Celular deve começar com 9");

        if (Verificador.verificarDataNascimento(getDataDeNascimento())) {
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
        System.out.printf("Senha: %s", getSenha());

        Endereco meuEndereco = Endereco.BuscarPorId(getEnderecoId());
        if (meuEndereco == null) return;
        System.out.println("\033[1;32m\n\n=====| ENDEREÇO REGISTRADO |=====\033[m");
        meuEndereco.exibirInfosCep(true);
        System.out.printf("Complemento: %s\n", meuEndereco.getComplemento());
        System.out.printf("Número: %s\n", meuEndereco.getNumero());
    }

    /**
     * @return
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
     * @return
     */
    public boolean remover() {
        return false;
    }

    /**
     * @return
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
