package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;
import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.ISafra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Representa uma safra agrícola, associando um terreno, período,
 * status e lista de produtos cultivados a um agricultor.
 */
public class Safra extends Terreno implements ISafra {
    private String safraId;
    private String nomeSafra;
    private String dataInicio;
    private String dataFim;
    private StatusSafra status;
    private ArrayList<Produto> cultivados;
    private String agricultorId;
    private static final IDatabase<Safra> database = new Database<Safra>();;

    /**
     * Inicializa uma safra a partir de todos os seus dados já persistidos, incluindo terreno.
     *
     * @param id           identificador único da safra
     * @param nomeSafra    nome da safra
     * @param dataInicio   data de início no formato DD/MM/YYYY
     * @param dataFim      data de encerramento no formato DD/MM/YYYY
     * @param agricultorId ID do agricultor responsável
     * @param status       status atual da safra
     * @param cultivados   lista de produtos cultivados
     * @param latitude     latitude do terreno
     * @param longitude    longitude do terreno
     * @param area         área do terreno em metros quadrados
     */
    public Safra(String id, String nomeSafra, String dataInicio, String dataFim, String agricultorId, StatusSafra status, ArrayList<Produto> cultivados, double latitude, double longitude, double area) {
        super(latitude, longitude, area);
        this.safraId = id;
        this.nomeSafra = nomeSafra;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.cultivados = cultivados;
        this.agricultorId = agricultorId;
    }

    /**
     * Inicializa uma safra sem informações de terreno.
     * O status é definido automaticamente como {@link StatusSafra#PLANEJADA}.
     *
     * @param nomeSafra    nome da safra
     * @param dataInicio   data de início no formato DD/MM/YYYY
     * @param dataFim      data de encerramento no formato DD/MM/YYYY
     * @param cultivados   lista de produtos a serem cultivados
     * @param agricultorId ID do agricultor responsável
     */
    public Safra(String nomeSafra, String dataInicio, String dataFim, ArrayList<Produto> cultivados, String agricultorId) {
        super(0, 0, 0);
        this.safraId = UUID.randomUUID().toString();
        this.nomeSafra = nomeSafra;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cultivados = cultivados;
        this.status = StatusSafra.PLANEJADA;
        this.agricultorId = agricultorId;
    }

    /**
     * Inicializa uma safra completa com informações de terreno.
     * O status é definido automaticamente como {@link StatusSafra#PLANEJADA}.
     *
     * @param nomeSafra    nome da safra
     * @param dataInicio   data de início no formato DD/MM/YYYY
     * @param dataFim      data de encerramento no formato DD/MM/YYYY
     * @param agricultorId ID do agricultor responsável
     * @param cultivados   lista de produtos a serem cultivados
     * @param latitude     latitude do terreno
     * @param longitude    longitude do terreno
     * @param area         área do terreno em metros quadrados
     */
    public Safra(String nomeSafra, String dataInicio, String dataFim, String agricultorId, ArrayList<Produto> cultivados, double latitude, double longitude, double area) {
        super(latitude, longitude, area);
        this.safraId = UUID.randomUUID().toString();
        this.nomeSafra = nomeSafra;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusSafra.PLANEJADA;
        this.agricultorId = agricultorId;
        this.cultivados = cultivados;
    }

    /**
     * Retorna o identificador único da safra.
     * @return {@link String} ID da safra
     */
    public String getSafraId() {
        return safraId;
    }

    /**
     * Define o identificador único da safra.
     * @param safraId {@link String} ID da safra
     */
    private void setSafraId(String safraId) {
        this.safraId = safraId;
    }

    /**
     * Retorna o nome da safra.
     * @return {@link String} Nome da safra
     */
    public String getNomeSafra() {
        return nomeSafra;
    }

    /**
     * Define o nome da safra.
     * @param nomeSafra {@link String} Nome da safra
     */
    public void setNomeSafra(String nomeSafra) {
        this.nomeSafra = nomeSafra;
    }

    /**
     * Retorna a data de início da safra.
     * @return {@link String} Data de início da safra no formato DD/MM/YYYY
     */
    public String getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início da safra.
     * @param dataInicio {@link String} Data de início da safra no formato DD/MM/YYYY
     */
    private void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Retorna a data de encerramento da safra.
     * @return {@link String} Data de encerramento da safra no formato DD/MM/YYYY
     */
    public String getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de encerramento da safra.
     * @param dataFim {@link String} Data de encerramento da safra no formato DD/MM/YYYY
     */
    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Retorna o status atual da safra.
     * @return {@link StatusSafra} Status atual da safra
     */
    public StatusSafra getStatus() {
        return status;
    }

    /**
     * Retorna a lista de produtos cultivados na safra.
     * @return {@link ArrayList} de {@link Produto} cultivados na safra
     */
    public ArrayList<Produto> getCultivados() {
        return cultivados;
    }

    /**
     * Define a lista de produtos cultivados na safra.
     * @param cultivados {@link ArrayList} de {@link Produto} cultivados na safra
     */
    public void setCultivados(ArrayList<Produto> cultivados) {
        this.cultivados = cultivados;
    }

    /**
     * Define o status atual da safra.
     * @param status {@link StatusSafra} Novo status da safra
     */
    private void setStatus(StatusSafra status) {
        this.status = status;
    }

    /**
     * Retorna o identificador do agricultor responsável pela safra.
     * @return {@link String} ID do agricultor
     */
    public String getAgricultorId() {
        return agricultorId;
    }

    /**
     * Define o identificador do agricultor responsável pela safra.
     * @param agricultorId {@link String} ID do agricultor
     */
    public void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    /**
     * {@inheritDoc}
     *
     * Atualiza o status internamente e persiste a alteração via {@link #editar()},
     * exibindo mensagem de confirmação no terminal em caso de sucesso.
     */
    public void modificarStatusSafra(StatusSafra status) {
        setStatus(status);
        boolean sucesso = editar();
        if (sucesso) System.out.println("\033[1;92mSTATUS da Safra atualizado para " + getStatus().getDescricao().toUpperCase() + "!!\033[m");
    }

    /** {@inheritDoc} */
    public boolean adicionar() {
        try {
            return database.criarRegistro(this, Safra.class);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro inesperado ao adicionar a safra\033[m");
            throw ex;
        }
    }

    /** {@inheritDoc} */
    public boolean editar() {
        try {
            return database.editarRegistro(this, Safra.class);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro inesperado ao editar a safra\033[m");
            throw ex;
        }
    }
    /**
     * Exibe no console as informações da safra formatada de maneira simples:
     * data de início, data de encerramento, status e produtos cultivados.
     */
    public void exibir() {
        System.out.printf("\033[1mData de Início: \033[32m%s\033[m\n", getDataInicio());
        System.out.printf("\033[1mData de Encerramento: \033[33m%s\033[m\n", getDataFim());
        System.out.printf("\033[1mStatus atual: \033[34m%s\033[m\n", getStatus().getDescricao());
        System.out.print("\033[1mCultivados na Safra: \033[36m");
        for (int i = 0; i < getCultivados().size(); i++) {
            System.out.printf("%s (%s)",getCultivados().get(i).getNomeFormatado(), getCultivados().get(i).getNomeCientifico());
            if (i < getCultivados().size() - 1)
                System.out.print(", ");
        }
        System.out.println("\n");
    }

    /**
     * {@inheritDoc}
     * Valida: latitude, longitude e área com valores numéricos, e os intervalos válidos de cada coordenada.
     */
    public ArrayList<String> verificarRegistro() {
        ArrayList<String> erros = new ArrayList<String>();

        if (Double.isNaN(getLatitude()))
            erros.add("Latitude deve conter valor numérico");

        if (Double.isNaN(getLongitude()))
            erros.add("Longitude deve conter valor numérico");

        if (Double.isNaN(getArea()))
            erros.add("Area deve conter valor numérico");

        if (!Verificador.verificarLatitude(getLatitude()))
            erros.add("Latitude informada é inválida");

        if (!Verificador.verificarLongitude(getLatitude()))
            erros.add("Longitude informada é inválida");

        return erros;
    }

    /**
     * Valida as informações principais da safra: nome, datas, produtos cultivados
     * e consistência entre data de início e data fim.
     *
     * @return {@link ArrayList} de {@link String} com os erros encontrados, ou lista vazia se válido
     */
    public ArrayList<String> verificarRegistroPrincipal() {
        ArrayList<String> erros = new ArrayList<String>();

        if (!getNomeSafra().trim().isEmpty() && getNomeSafra().length() < 3)
            erros.add("Nome deve conter pelo menos 3 caractéres");

        if (getNomeSafra().trim().isEmpty())
            setNomeSafra("Safra para cultivo de " + getCultivados().getFirst().getNome());

        if (!Verificador.verificarData(getDataFim()))
            erros.add("Data Fim Inválida. Formato: DD/MM/YYYY");

        if (!Verificador.verificarData(getDataInicio()))
            erros.add("Data Inicio Inválida. Formato: DD/MM/YYYY");

        if (getCultivados().isEmpty())
            erros.add("Nenhum produto para ser cultivado foi selecionado.");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicio = LocalDate.parse(getDataInicio(), formatter);
            LocalDate encerramento = LocalDate.parse(getDataFim(), formatter);
            if (encerramento.isBefore(inicio))
                erros.add("Data Final deve ser superior a data inicial");

            if (inicio.isAfter(LocalDate.now()))
                erros.add("Data inicio deve ser inferior ao dia de hoje");
        }
        catch (DateTimeParseException ex) {
            erros.add("Erro na verificação de datas.");
        }
        catch (NullPointerException ex) {
            erros.add("Datas devem conter valores.");
        }

        return erros;
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return getSafraId();
    }

    /**
     * Exibe no console as safras pertencentes ao agricultor informado,
     * numeradas em ordem crescente.
     *
     * @param agricultor instância de {@link Agricultor} cujas safras serão exibidas
     */
    public static void exibirSafras(Agricultor agricultor) {
        try {
            ArrayList<Safra> minhasSafras = pegarMinhasSafras(agricultor.getAgricultorId());

            for (int i = 0; i < minhasSafras.size(); i++) {
                Safra safra = minhasSafras.get(i);
                System.out.printf("\033[1;94m[%d]\033[m %s \n", i+1, safra.getNomeSafra());
            }
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro inesperado ao exibir as safras do agricultor\033[m");
            throw ex;
        }
    }

    /**
     * Retorna todas as safras pertencentes ao agricultor com o ID informado.
     *
     * @param agroAtualId ID do agricultor cujas safras serão buscadas
     * @return {@link ArrayList} de {@link Safra} do agricultor
     */
    public static ArrayList<Safra> pegarMinhasSafras(String agroAtualId) {
        ArrayList<Safra> todasSafras = database.lerRegistro(Safra.class);
        ArrayList<Safra> minhasSafras = new ArrayList<Safra>();

        for (Safra safra : todasSafras) {
            if (safra.getAgricultorId().equalsIgnoreCase(agroAtualId))
                minhasSafras.add(safra);
        }
        return minhasSafras;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remover() {
        try {
            return database.removerRegistro(this, Safra.class);
        }
        catch (Exception ex) {
            System.out.println("Ocorreu um erro Inesperado durante a busca pelo agricultor");
            throw ex;
        }
    }
}
