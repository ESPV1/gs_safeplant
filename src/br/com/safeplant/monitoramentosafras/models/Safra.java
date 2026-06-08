package br.com.safeplant.monitoramentosafras.models;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;
import br.com.safeplant.monitoramentosafras.helper.Interacao;
import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.interfaces.IDatabase;
import br.com.safeplant.monitoramentosafras.interfaces.ISafra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.UUID;

public class Safra extends Terreno implements ISafra {
    private String safraId;
    private String nomeSafra;
    private String dataInicio;
    private String dataFim;
    private StatusSafra status;
    private ArrayList<Produto> cultivados;
    private String agricultorId;
    private static final IDatabase<Safra> database = new Database<Safra>();;

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

    public String getSafraId() {
        return safraId;
    }

    private void setSafraId(String safraId) {
        this.safraId = safraId;
    }

    public String getNomeSafra() {
        return nomeSafra;
    }

    public void setNomeSafra(String nomeSafra) {
        this.nomeSafra = nomeSafra;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    private void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public StatusSafra getStatus() {
        return status;
    }

    public ArrayList<Produto> getCultivados() {
        return cultivados;
    }

    public void setCultivados(ArrayList<Produto> cultivados) {
        this.cultivados = cultivados;
    }

    private void setStatus(StatusSafra status) {
        this.status = status;
    }

    public String getAgricultorId() {
        return agricultorId;
    }

    public void setAgricultorId(String agricultorId) {
        this.agricultorId = agricultorId;
    }

    public void modificarStatusSafra(StatusSafra status) {
        setStatus(status);
        boolean sucesso = editar();
        if (sucesso) System.out.println("\033[1;92mSTATUS da Safra atualizado para " + getStatus().getDescricao().toUpperCase() + "!!\033[m");
    }

    public boolean adicionar() {
        try {
            return database.criarRegistro(this, Safra.class);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro inesperado ao adicionar a safra\033[m");
            throw ex;
        }
    }

    public boolean editar() {
        try {
            return database.editarRegistro(this, Safra.class);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro inesperado ao editar a safra\033[m");
            throw ex;
        }
    }

    public void exibir() {
        System.out.printf("\033[1mData de Início: \033[32m%s\033[m\n", getDataInicio());
        System.out.printf("\033[1mData de Encerramento: \033[33m%s\033[m\n", getDataFim());
        System.out.printf("\033[1mStatus atual: \033[34m%s\033[m\n", getStatus().getDescricao());
        System.out.print("\033[1mCultivados na Safra: \033[36m");
        for (int i = 0; i < getCultivados().size(); i++) {
            System.out.print(getCultivados().get(i).getNomeFormatado());
            if (i < getCultivados().size() - 1)
                System.out.print(", ");
        }
        System.out.println("\n");
    }

    /**
     * @return
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
     * @return
     */
    public String getId() {
        return getSafraId();
    }


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

    public static ArrayList<Safra> pegarMinhasSafras(String agroAtualId) {
        ArrayList<Safra> todasSafras = database.lerRegistro(Safra.class);
        ArrayList<Safra> minhasSafras = new ArrayList<Safra>();

        for (Safra safra : todasSafras) {
            if (safra.getAgricultorId().equalsIgnoreCase(agroAtualId))
                minhasSafras.add(safra);
        }
        return minhasSafras;
    }

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
