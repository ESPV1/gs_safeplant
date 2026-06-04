package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.interfaces.IAgricultor;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Agricultor extends Usuario implements IAgricultor {
    private String agricultorId;
    private String enderecoId;
    private LocalDateTime dataDeNascimento;
    private int idade;
    private String celular;
    private String cpf;
    private static IDatabase<Agricultor> database = new Database<Agricultor>();

    public Agricultor(String agricultorId, LocalDateTime ultimoAcessoEm, boolean ativo, boolean temMfa, String senha, String email, String sobrenome, String primeiroNome, String nomeUsuario, String nomeCompleto, String usuarioId, String enderecoId, LocalDateTime dataDeNascimento, int idade, String celular, String cpf) {
        super(ultimoAcessoEm, ativo, temMfa, senha, email, sobrenome, primeiroNome, nomeUsuario, nomeCompleto, usuarioId);
        this.agricultorId = agricultorId;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.idade = idade;
    }

    public Agricultor(String nomeCompleto, String email, String senha, String nomeUsuario, String enderecoId, LocalDateTime dataDeNascimento, String celular, String cpf) {
        super(nomeCompleto, nomeUsuario, email, senha);
        this.enderecoId = enderecoId;
        this.dataDeNascimento = dataDeNascimento;
        this.celular = celular;
        this.cpf = cpf;
        this.idade = calcularIdade();
    }

    public LocalDateTime getDataDeNascimento() {
        return dataDeNascimento;
    }

    private void setDataDeNascimento(LocalDateTime dataDeNascimento) {
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
        if (cpf.length() != 11)
            return;
        if (cpf.matches("[a-zA-Z]"))
            return;
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
        LocalDateTime nascimento = getDataDeNascimento();
        LocalDateTime hoje = LocalDateTime.now();

        int idade = hoje.getYear() - nascimento.getYear();

        if (hoje.getMonthValue() < nascimento.getMonthValue() ||
                (hoje.getMonthValue() == nascimento.getMonthValue() && hoje.getDayOfMonth() < nascimento.getDayOfMonth())) {
            idade--;
        }

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

    public void exibirMeuPerfil() {
        System.out.flush();
        System.out.println("PERFIL DO AGRICULTOR(A) " + getPrimeiroNome().toUpperCase() + "\n");
        System.out.println("=====| INFORMAÇÕES PESSOAIS |=====");
        System.out.printf("Nome Completo: %s\n", getNomeCompleto());
        System.out.printf("Data de Nascimento: %s\n", getDataDeNascimento());
        System.out.printf("Idade: %d anos\n", getIdade());
        System.out.printf("Nome de Usuário: %s\n", getNomeUsuario());
        System.out.printf("CPF: %s\n", getCpf());
        System.out.printf("Celular: %s", getCelular());

        System.out.println("\n=====| CREDENCIAIS |=====");
        System.out.printf("Email: %s\n", getEmail());
        System.out.printf("Senha: %s", getSenha());

        System.out.println("\n=====| ENDEREÇO |=====");
        System.out.printf("EndereçoId: %s\n", getEnderecoId());
    }
}
