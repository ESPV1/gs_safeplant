package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.IOperacoesPadrao;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.UUID;

public class Endereco implements IOperacoesPadrao {
    private String id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String regiao;
    private String uf;
    private String complemento;
    private static transient final IDatabase<Endereco> database = new Database<>();;

    public Endereco() {
        this.id = UUID.randomUUID().toString();
    }

    public Endereco(String cep, String logradouro, String numero, String bairro, String localidade, String uf, String complemento, String regiao) {
        this.id = UUID.randomUUID().toString();
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.complemento = complemento;
        this.regiao = regiao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public static Endereco BuscarEnderecoPorCep(String cep) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String viaCepUrl = "https://viacep.com.br/ws/" + cep + "/json";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(viaCepUrl)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200 || response.body().contains("\"erro\"")) {
                System.out.println("CEP informado é inválido");
                return null;
            }

            return database.converterJsonParaJava(response.body(), Endereco.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro durante a busca do CEP");
            return null;
        }
    }

    public static Endereco BuscarPorId(String enderecoId) {
        try {
            ArrayList<Endereco> enderecos = database.lerRegistro(Endereco.class);
            for (Endereco end : enderecos) {
                if (end.getId().equalsIgnoreCase(enderecoId))
                    return end;
            }
            return null;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro durante a busca pelo ID");
            return null;
        }

    }

    public ArrayList<String> verificarEndereco() {
        ArrayList<String> erros = new ArrayList<String>();

        if (!Verificador.verificarCEP(getCep()))
            erros.add("CEP informado é inválido");
        if (getLogradouro().length() < 5)
            erros.add("Logradouro deve ter pelo menos 5 caractéres");
        if (getUf().length() != 2)
            erros.add("UF deve ter 2 caractéres");
        if (getBairro().length() < 5)
            erros.add("Bairro deve ter pelo menos 5 caractéres");
        if (getLocalidade().length() < 5)
            erros.add("Cidade deve ter pelo menos 5 caractéres");
        if (!getNumero().matches(".*\\d.*"))
            erros.add("Número da casa não deve conter letras");

        return erros;
    }

    /**
     * @return
     */
    public boolean adicionar() {
        try {
            return database.criarRegistro(this, Endereco.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro durante a busca do CEP");
            return false;
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
            return database.editarRegistro(this, Endereco.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro inesperado durante a edição do usuário");
            return false;
        }
    }

    public void exibirInfosCep(boolean removeTitulo) {
        if (!removeTitulo)
            System.out.println("\033[1;32m=====| Informações do CEP |=====\033[m");
        System.out.printf("CEP: %s\n", getCep());
        System.out.printf("Logradouro: %s\n", getLogradouro());
        System.out.printf("Bairro: %s\n", getBairro());
        System.out.printf("Localidade: %s\n", getLocalidade());
        System.out.printf("UF: %s\n", getUf());
        System.out.printf("Região: %s\n", getRegiao());
    }
}
