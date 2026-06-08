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

/**
 * Representa o endereço de um agricultor, com suporte a busca
 * automática de dados via API ViaCEP.
 */
public class Endereco implements IOperacoesPadrao {
    private String enderecoId;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String regiao;
    private String uf;
    private String complemento;
    private static final IDatabase<Endereco> database = new Database<>();;

    /**
     * Construtor padrão. Gera automaticamente um identificador único para o endereço.
     */
    public Endereco() {
        this.enderecoId = UUID.randomUUID().toString();
    }

    /**
     * Cria um endereço com todos os campos preenchidos.
     * O identificador único é gerado automaticamente.
     *
     * @param cep        CEP do endereço
     * @param logradouro nome do logradouro
     * @param numero     número da residência
     * @param bairro     nome do bairro
     * @param localidade nome da cidade
     * @param uf         sigla do estado (2 caracteres)
     * @param complemento complemento do endereço
     * @param regiao     região do país
     */
    public Endereco(String cep, String logradouro, String numero, String bairro, String localidade, String uf, String complemento, String regiao) {
        this.enderecoId = UUID.randomUUID().toString();
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.complemento = complemento;
        this.regiao = regiao;
    }

    /**
     * Retorna o identificador único do endereço.
     * @return {@link String} ID do endereço
     */
    public String getEnderecoId() {
        return enderecoId;
    }

    /**
     * Define o identificador único do endereço.
     * @param enderecoId {@link String} ID do endereço
     */
    private void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    /**
     * Retorna o CEP do endereço.
     * @return {@link String} CEP do endereço
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o CEP do endereço.
     * @param cep {@link String} CEP do endereço
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Retorna o logradouro do endereço.
     * @return {@link String} Logradouro do endereço
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Define o logradouro do endereço.
     * @param logradouro {@link String} Logradouro do endereço
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * Retorna o número do endereço.
     * @return {@link String} Número do endereço
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define o número do endereço.
     * @param numero {@link String} Número do endereço
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Retorna o bairro do endereço.
     * @return {@link String} Bairro do endereço
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o bairro do endereço.
     * @param bairro {@link String} Bairro do endereço
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Retorna a sigla do estado do endereço.
     * @return {@link String} UF do endereço
     */
    public String getUf() {
        return uf;
    }

    /**
     * Define a sigla do estado do endereço.
     * @param uf {@link String} UF do endereço
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * Retorna o complemento do endereço.
     * @return {@link String} Complemento do endereço
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Define o complemento do endereço.
     * @param complemento {@link String} Complemento do endereço
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * Retorna a localidade (cidade) do endereço.
     * @return {@link String} Localidade do endereço
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Define a localidade (cidade) do endereço.
     * @param localidade {@link String} Localidade do endereço
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Retorna a região do endereço.
     * @return {@link String} Região do endereço
     */
    public String getRegiao() {
        return regiao;
    }

    /**
     * Define a região do endereço.
     * @param regiao {@link String} Região do endereço
     */
    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }


    /**
     * Consulta a API ViaCEP e retorna um {@link Endereco} com os dados correspondentes ao CEP informado.
     *
     * @param cep CEP a ser consultado (somente números, 8 dígitos)
     * @return instância de {@link Endereco} preenchida, ou {@code null} se o CEP for inválido ou ocorrer erro
     */
    public static Endereco buscarEnderecoPorCep(String cep) {
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

    /**
     * Busca e retorna o {@link Endereco} correspondente ao ID informado.
     *
     * @param enderecoId ID do endereço a ser buscado
     * @return instância de {@link Endereco} encontrada, ou {@code null} se não existir
     */
    public static Endereco buscarPorId(String enderecoId) {
        try {
            ArrayList<Endereco> enderecos = database.lerRegistro(Endereco.class);
            for (Endereco end : enderecos) {
                if (end.getEnderecoId().equalsIgnoreCase(enderecoId))
                    return end;
            }
            return null;
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro durante a busca pelo ID");
            return null;
        }

    }

    /**
     * {@inheritDoc}
     * Valida: formato do CEP, tamanho mínimo do logradouro, bairro e localidade,
     * tamanho da UF e número contendo ao menos um dígito.
     */
    public ArrayList<String> verificarRegistro() {
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
     * {@inheritDoc}
     */
    public String getId() {
        return getEnderecoId();
    }

    /**
     * {@inheritDoc}
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
            return database.editarRegistro(this, Endereco.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro inesperado durante a edição do usuário");
            return false;
        }
    }

    /**
     * Exibe no console as informações do CEP formatadas.
     *
     * @param removeTitulo se {@code true}, suprime o cabeçalho da exibição
     */
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
