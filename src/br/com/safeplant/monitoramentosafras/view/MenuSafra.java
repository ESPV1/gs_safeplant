package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;
import br.com.safeplant.monitoramentosafras.helper.Interacao;
import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Produto;
import br.com.safeplant.monitoramentosafras.models.Safra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Menu responsável pelo gerenciamento de safras do agricultor autenticado,
 * permitindo visualizar, registrar, editar e excluir safras.
 */
public class MenuSafra {
    private final Scanner scanner;
    private Agricultor agricultor;

    /**
     * Cria o menu de safras para o agricultor informado.
     *
     * @param agricultor instância de {@link Agricultor} autenticado na sessão
     */
    public MenuSafra(Agricultor agricultor) {
        this.scanner = new Scanner(System.in);
        this.agricultor = agricultor;
    }

    /**
     * Retorna o agricultor associado ao menu.
     * @return {@link Agricultor} atual do menu
     */
    public Agricultor getAgricultor() {
        return agricultor;
    }

    /**
     * Define o agricultor associado ao menu.
     * @param agricultor {@link Agricultor} a ser definido
     */
    private void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }


    /**
     * Exibe o menu principal de gerenciamento de safras,
     * permitindo navegar entre as opções disponíveis.
     */
    public void exibir() {
        String opcao;
        do {
            System.out.flush();
            System.out.println("\033[1;32m\n=====| GERENCIAMENTO DE SAFRAS | =====\033[m");
            System.out.println("\033[1;36m[1]\033[m Safras de " + getAgricultor().getPrimeiroNome());
            System.out.println("\033[1;36m[2]\033[m Registrar nova Safra");
            System.out.println("\033[1;36m[3]\033[m Editar Safras");
            System.out.println("\033[1;36m[4]\033[m Exibir Relatório Semanal");
            System.out.println("\033[1;36m[5]\033[m Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.next();

            switch (opcao) {
                case "1":
                    System.out.printf("\033[1;32m=====| SAFRAS DE %s |=====\033[m\n", getAgricultor().getPrimeiroNome().toUpperCase());
                    menuMinhasSafras();
                    break;
                case "2":
                    menuNovaSafra();
                    break;
                case "3":
                    System.out.println("\033[1;32m=====| EDITAR SAFRAS |=====\033[m");
                    menuEditarSafra();
                    break;
                case "4":
                    menuRelatorio();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            System.out.println();
        } while (!opcao.equals("5"));
    }


    /**
     * Exibe a lista de safras do agricultor autenticado e permite
     * selecionar uma para visualizar os seus detalhes.
     */
    public void menuMinhasSafras() {
        int safraIdx = -1;
        ArrayList<Safra> safras = Safra.pegarMinhasSafras(getAgricultor().getAgricultorId());

        if (safras.isEmpty()) {
            System.out.println("Nenhuma safra cadastrada");
            return;
        }

        while (safraIdx != 0) {
            Safra.exibirSafras(getAgricultor());
            System.out.println("\033[1;93mDigite 0 para voltar\033[m");
            safraIdx = Interacao.inputInteiro("Escolha uma Safra: ");

            if (safraIdx == 0)
                continue;

            if (safraIdx < 0 || safraIdx > safras.size()) {
                System.out.println("Opção selecionada é inválida!");
                continue;
            }
            Safra safraSelecionada = safras.get(safraIdx - 1);
            menuDetalhesSafra(safraSelecionada);
        }
    }

    /**
     * Exibe a lista de safras do agricultor e retorna a safra selecionada.
     *
     * @return {@link Safra} selecionada pelo usuário, ou {@code null} se voltar sem selecionar
     */
    public Safra verMinhasSafras() {
        int safraIdx = -1;
        ArrayList<Safra> safras = Safra.pegarMinhasSafras(getAgricultor().getAgricultorId());

        if (safras.isEmpty()) {
            System.out.println("Nenhuma safra cadastrada");
            return null;
        }
        while (safraIdx != 0) {
            Safra.exibirSafras(getAgricultor());
            System.out.println("\n\033[1;93mDigite 0 para voltar\033[m");
            safraIdx = Interacao.inputInteiro("Escolha uma Safra: ");

            if (safraIdx == 0)
                continue;

            if (safraIdx < 0 || safraIdx > safras.size()) {
                System.out.println("\033[1;31mOpção selecionada é inválida!\033[m");
                continue;
            }
            return safras.get(safraIdx - 1);
        }
        return null;
    }

    /**
     * Exibe o fluxo de registro de uma nova safra em duas etapas:
     * informações principais e dados do terreno, validando cada etapa antes de persistir.
     */
    public void menuNovaSafra() {
        boolean safraValida;
        Safra novaSafra;

        if (new Produto().pegarMeusProdutos(getAgricultor().getAgricultorId()).isEmpty()) {
            System.out.println("\033[1;31mÉ Obrigatório que exista ao menos 1 produto no estoque\033[m\n");
            return;
        }

        do {
            System.out.println("\033[1;32m=====| REGISTRO DE SAFRA |=====\033[m\n");
            System.out.println("Digite \033[1;31mSair\033[m para encerrar o cadastro.");

            System.out.println("\033[1;96m=====| INFORMAÇÕES PRINCIPAIS (1/2) |=====\033[m\n");
            String nomeSafra = Interacao.inputString("Nome (opcional): ");
            if (Interacao.verificarSaida(nomeSafra)) return;

            System.out.println("\n\033[93mDigite \033[4mhoje\033[0;93m para selecionar a data de hoje.\033[m");
            String dataInicio = Interacao.inputData("Data Inicial: ");
            if (dataInicio != null && Interacao.verificarSaida(dataInicio)) return;

            String dataFinal = Interacao.inputData("Final Estimado: ");
            if (dataFinal != null && Interacao.verificarSaida(dataFinal)) return;

            ArrayList<Produto> cultivados = selecionarCultivados();

            novaSafra = new Safra(nomeSafra, dataInicio, dataFinal, cultivados, getAgricultor().getAgricultorId());
            ArrayList<String> erros = novaSafra.verificarRegistroPrincipal();

            if (!erros.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                erros.forEach(msg -> System.out.println(msg));
                System.out.println();
                safraValida = false;
                continue;
            }
            safraValida = true;
        } while (!safraValida);

        do {
            System.out.println("\033[1;96m=====| TERRENO DA SAFRA (2/2) |=====\033[m\n");
            double latitude = Interacao.inputDouble("Distância Latitude (graus °): ");
            if (latitude == Double.NEGATIVE_INFINITY) return;

            double longitude = Interacao.inputDouble("Distância Longitude (graus °): ");
            if (longitude == Double.NEGATIVE_INFINITY) return;

            double areaTerreno = Interacao.inputDouble("Área (m²): ");
            if (areaTerreno == Double.NEGATIVE_INFINITY) return;

            novaSafra.setLatitude(latitude);
            novaSafra.setLongitude(longitude);
            novaSafra.setArea(areaTerreno);
            ArrayList<String> erros = novaSafra.verificarRegistro();

            if (!erros.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                erros.forEach(msg -> System.out.println(msg));
                System.out.println();
                safraValida = false;
                continue;
            }
            safraValida = true;

        } while (!safraValida);


        boolean safraSalva = novaSafra.adicionar();
        if (safraSalva)
            System.out.println("Nova safra \033[1;36m" + novaSafra.getNomeSafra().toUpperCase() + "\033[m adicionado!");
        else
            System.out.println("Falha ao salvar a Safra!");
    }

    /**
     * Exibe os detalhes da safra informada e aguarda confirmação do usuário para voltar.
     *
     * @param safra instância de {@link Safra} cujos detalhes serão exibidos
     */
    public void menuDetalhesSafra(Safra safra) {
        System.out.println("\n\033[1;96m====| DETALHES DE " + safra.getNomeSafra().toUpperCase() + " |=====\033[m");
        safra.exibir();
        Interacao.aguardarTecla(scanner);
    }

    /**
     * Exibe o relatório semanal das safras do agricultor.
     */
    public void menuRelatorio() {

    }

    /**
     * Exibe o menu de edição de safras, permitindo atualizar cultivos,
     * status, data de encerramento ou excluir a safra selecionada.
     */
    public void menuEditarSafra() {
        String opcao;
        do {
            Safra safra = verMinhasSafras();
            if (safra == null)
                 return;

            System.out.println("\n\033[1;96m=====| OPERAÇÕES |=====\033[m");
            System.out.println("\033[1;36m[1]\033[m Atualizar Cultivos");
            System.out.println("\033[1;36m[2]\033[m Atualizar Status");
            System.out.println("\033[1;36m[3]\033[m Atualizar Data de Encerramento");
            System.out.println("\033[1;36m[4]\033[m Excluir Safra");
            System.out.println("\033[1;36m[5]\033[m Voltar");
            opcao = Interacao.inputString("Escolha uma opção: ");

            switch (opcao) {
                case "1":
                    System.out.println("\033[1;31mDigite 0 para encerrar e voltar\033[m");
                    atualizarCultivados(safra);
                    return;
                case "2":
                    StatusSafra novoStatus = selecionarNovoStatus(safra.getStatus());
                    safra.modificarStatusSafra(novoStatus);
                    return;
                case "3":
                    atualizadorParaDataFinal(safra);
                    return;
                case "4":
                    excluirSafra(safra);
                    return;
                case "5":
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (!opcao.equals("5"));
    }

    /**
     * Atualiza a lista de produtos cultivados da safra informada,
     * permitindo adicionar novos itens do estoque disponível.
     *
     * @param safra instância de {@link Safra} a ter os cultivos atualizados
     */
    public void atualizarCultivados(Safra safra) {
        int tamanhoOriginal = safra.getCultivados().size();
        ArrayList<Produto> cultivados = new ArrayList<>(safra.getCultivados());
        ArrayList<Produto> novoCultivo = selecionarCultivados(cultivados);

        if (novoCultivo == null) {
            System.out.println("\033[1mNenhuma Alteração foi realizada.\033[m");
            return;
        }
        if (novoCultivo.isEmpty()) {
            System.out.println("\033[1;93mSafra deve conter ao menos 1 cultivo. Operação Cancelada.\033[m");
            return;
        }

        safra.setCultivados(novoCultivo);
        boolean sucesso = safra.editar();

        if (!sucesso)
            return;

        if (novoCultivo.size() > tamanhoOriginal)
            System.out.println("\033[1;92mNovos cultivos foram adicionados a Safra\033[m");
        else if (novoCultivo.size() < tamanhoOriginal)
            System.out.println("\033[1;31mCultivos foram removidos da Safra\033[m");
        else
            System.out.println("\033[1;92mCultivos da Safra foram atualizados\033[m");
    }

    /**
     * Solicita ao usuário que selecione os produtos a serem cultivados
     * na safra a partir do estoque disponível do agricultor.
     *
     * @return {@link ArrayList} de {@link Produto} selecionados para cultivo
     */
    public ArrayList<Produto> selecionarCultivados() {
        MenuProduto menuProduto = new MenuProduto(getAgricultor());
        ArrayList<Produto> cultivados = new ArrayList<Produto>();
        return definirCultivos(cultivados, menuProduto, true);
    }

    /**
     * Solicita ao usuário que selecione novos produtos a serem cultivados,
     * exibindo previamente os já cadastrados na safra e excluindo-os do estoque disponível.
     *
     * @param jaCultivados {@link ArrayList} de {@link Produto} já presentes na safra
     * @return {@link ArrayList} de {@link Produto} atualizada com os novos cultivos
     */
    public ArrayList<Produto> selecionarCultivados(ArrayList<Produto> jaCultivados) {
        MenuProduto menuProduto = new MenuProduto(getAgricultor());
        ArrayList<Produto> listaFinal = new ArrayList<Produto>();

        if (jaCultivados.isEmpty()) {
            return definirCultivos(jaCultivados, menuProduto, true);
        }

        String opcaoEscolhida = "";
        do {
            System.out.println("\033[1;36m=====| ATUALMENTE CULTIVADOS |=====\033[m");
            jaCultivados.forEach(c -> System.out.println(c.getNomeFormatado()));
            System.out.println("\n\033[1;94m=====| OPERAÇÃO |=====\033[m");
            System.out.println("\033[1;36m[1]\033[m Adicionar Cultivo");
            System.out.println("\033[1;36m[2]\033[m Remover Cultivo ");
            System.out.println("\033[1;36m[3]\033[m Voltar e salvar ");
            System.out.println("\033[1;36m[4]\033[m Voltar sem salvar ");
            opcaoEscolhida = Interacao.inputString("Escolha: ");

            switch (opcaoEscolhida) {
                case "1":
                    listaFinal = definirCultivos(jaCultivados, menuProduto, true);
                    break;
                case "2":
                    listaFinal = definirCultivos(jaCultivados, menuProduto, false);
                    break;
                case "3":
                    break;
                case "4":
                    listaFinal = null;
                    break;
                default:
                    System.out.println("\033[1;31mOpção inválida\033[m");
                    break;
            }
        } while (!(opcaoEscolhida.equals("3") || opcaoEscolhida.equals("4")));

        return listaFinal;
    }

    /**
     * Conduz o fluxo interativo de seleção de produtos do estoque para cultivo,
     * permitindo adicionar múltiplos itens até que o usuário encerre.
     *
     * @param cultivados lista inicial de {@link Produto} já selecionados
     * @param menu       instância de {@link MenuProduto} utilizada para exibição formatada
     * @return {@link ArrayList} de {@link Produto} com todos os cultivos definidos
     */
    private ArrayList<Produto> definirCultivos(ArrayList<Produto> cultivados, MenuProduto menu, boolean adicionando) {
        boolean finalizarCultivados = false;
        do {
            System.out.println("\033[1;93mDigite 0 para encerrar e retornar\033[m");
            System.out.printf("\033[1;32m=====| ESTOQUE DISPONÍVEL DE %s |=====\033[m\n", getAgricultor().getPrimeiroNome().toUpperCase());
            ArrayList<Produto> produtos = adicionando ? new Produto().pegarMeusProdutos(getAgricultor().getAgricultorId(), cultivados) : cultivados;

            if (produtos.isEmpty()) {
                System.out.println("\n\033[1;31mEstoque dísponível está vazio.\033[m");
                return new ArrayList<Produto>();
            }

            System.out.println("\033[1;96m=====| SUMÁRIO |=====\033[m");
            System.out.print("\033[1;35m[LEGUME]\033[m - ");
            System.out.print("\033[1;92m[VEGETAL]\033[m - ");
            System.out.print("\033[1;33m[FRUTA]\033[m - ");
            System.out.print("\033[1;93m[CEREAL]\033[m - ");
            System.out.println("\033[1;34m[LACTINIO]\033[m\n");
            System.out.println("\033[1;96m=====| ESTOQUE |=====\033[m");
            for (int i = 0; i < produtos.size(); i++) {
                menu.mostrarProdutoPorCor(produtos.get(i).getNome(), produtos.get(i).getNomeCientifico(), produtos.get(i).getTipoProduto(), i+1);
            }
            int idx = Interacao.inputInteiro("Selecione o produto que deseja: ");

            if (idx == 0)
                break;

            if (idx < 0 || idx > produtos.size()) {
                System.out.println("\033[1;31mOpção Inválida. Escolha outra\033[m");
                continue;
            }
            Produto cultivo = produtos.get(idx-1);
            if (adicionando) {
                System.out.printf("\033[1;92m\n%s %s adicionado(a)!\n\033[m", cultivo.getTipoProduto().getDescricao().toUpperCase(), cultivo.getNome().toUpperCase());
                cultivados.add(cultivo);
            }
            else {
                System.out.printf("\033[1;31m\n%s %s removido!\n\033[m", cultivo.getTipoProduto().getDescricao().toUpperCase(), cultivo.getNome().toUpperCase());
                cultivados.remove(cultivo);
            }

            finalizarCultivados = Interacao.inputBooleano("Encerrar registro de cultivos do estoque? [s/n]: ");
        } while (!finalizarCultivados);

        return cultivados;
    }

    /**
     * Solicita ao usuário que selecione um novo {@link StatusSafra} diferente do atual.
     *
     * @param statusAtual status atual da safra, impedido de ser reselecionado
     * @return novo {@link StatusSafra} selecionado pelo usuário
     */
    private StatusSafra selecionarNovoStatus(StatusSafra statusAtual) {
        StatusSafra novoStatus = statusAtual;
        System.out.println("\n\033[1;36m=====| ATUALIZAR STATUS SAFRA |=====\033[m");
        while (novoStatus == statusAtual) {
            System.out.println("\033[1;93mDigite 0 para encerrar e voltar\033[m\n");
            System.out.println("\033[1mSTATUS SAFRA ATUAL: \033[1;93m" + statusAtual.getDescricao().toUpperCase() + "\033[m");

            System.out.print("\033[1mStatus Disponíveis: \033[m");
            String status = Arrays.stream(StatusSafra.values())
                    .map(s -> s.getDescricao())
                    .collect(Collectors.joining(" - "));
            System.out.println(String.join(", ", status));
            try {
                String novoStatusStr = Interacao.inputString("\nInforme o novo status: ");
                if (novoStatusStr.equals("0"))
                    break;
                String[] formatted = novoStatusStr.split(" ");
                if (formatted.length > 1)
                    novoStatusStr = novoStatusStr.replace(" ", "_");
                novoStatus = StatusSafra.valueOf(novoStatusStr.toUpperCase());
            }
            catch (IllegalArgumentException ex) {
                System.out.println("\033[1;31mStatus Inválido, selecione outro.\033[m");
            }
        }
        return novoStatus;
    }

    /**
     * Exibe o fluxo de atualização da data de encerramento da safra,
     * validando o formato antes de persistir a alteração.
     *
     * @param safra instância de {@link Safra} a ter a data de encerramento atualizada
     */
    private void atualizadorParaDataFinal(Safra safra) {
        String novaData;
        boolean validoDataFinal = false;
        System.out.println("\n\033[1;36m=====| ATUALIZAR DATA ENCERRAMENTO |=====\033[m");
        do {
            System.out.println("Data de Encerramento atual: " + Interacao.formataData(safra.getDataFim()));
            System.out.println("\033[1;93mDigite 0 para encerrar e voltar\033[m\n");
            novaData = Interacao.inputString("Nova Data de Encerramento: ");

            if (novaData.equals("0"))
                break;

            if (!Verificador.verificarData(novaData)) {
                System.out.println("Data fornecida é inválida. Siga: DD/MM/YYYY");
                continue;
            }
            validoDataFinal = true;

        } while (!validoDataFinal);
        if (novaData.equals("0")) return;
        System.out.println("Nova data de encerramento selecionada: " + novaData);
        novaData = Interacao.formataData(novaData);
        safra.setDataFim(novaData);
        boolean sucesso = safra.editar();
        if (!sucesso) {
            System.out.println("\033[1;31mOcorreu um erro ao salvar as informações!\033[m");
        } else {
            System.out.println("\033[1;92mNovas informações salvas com sucesso!\033[m");
        }
    }

    /**
     * Solicita confirmação do usuário e exclui a safra informada do sistema.
     *
     * @param safra instância de {@link Safra} a ser excluída
     */
    private void excluirSafra(Safra safra) {
        safra.exibir();
        System.out.println("\033[1;96m==================\033[m\n");
        boolean safraIraExcluir = Interacao.inputBooleano("Você deseja realmente excluir a \033[1;93m" + safra.getNomeSafra() + "\033[m [s/n]: ");
        if (!safraIraExcluir) {
            System.out.println("\033[1;92mOperação Cancelada\033[m");
            return;
        }
        safra.remover();
    }
}
