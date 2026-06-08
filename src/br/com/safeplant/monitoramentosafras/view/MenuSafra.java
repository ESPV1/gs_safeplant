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

public class MenuSafra {
    private final Scanner scanner;
    private Agricultor agricultor;

    public MenuSafra(Agricultor agricultor) {
        this.scanner = new Scanner(System.in);
        this.agricultor = agricultor;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    private void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

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

    public void menuDetalhesSafra(Safra safra) {
        System.out.println("\n\033[1;96m====| DETALHES DE " + safra.getNomeSafra().toUpperCase() + " |=====\033[m");
        safra.exibir();
        Interacao.aguardarTecla(scanner);
    }

    public void menuRelatorio() {

    }

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

    public void atualizarCultivados(Safra safra) {
        ArrayList<Produto> cultivados = safra.getCultivados();
        if (cultivados.isEmpty()) {
            System.out.println("\033[1;31mNenhum produto cultivado. Safra inválida.\033[m");
            return;
        }
        safra.setCultivados(selecionarCultivados(cultivados));
        boolean sucesso = safra.editar();
        if (sucesso) System.out.println("\033[1;92mNovos cultivos foram adicionados a Safra\033[m");


    }

    public ArrayList<Produto> selecionarCultivados() {
        MenuProduto menuProduto = new MenuProduto(getAgricultor());
        ArrayList<Produto> cultivados = new ArrayList<Produto>();
        return definirCultivos(cultivados, menuProduto);
    }

    public ArrayList<Produto> selecionarCultivados(ArrayList<Produto> jaCultivados) {
        MenuProduto menuProduto = new MenuProduto(getAgricultor());
        if (jaCultivados.isEmpty()) {
            return new ArrayList<>();
        }
        System.out.println("\033[1;36m=====| ATUALMENTE CULTIVADOS |=====\033[m");
        jaCultivados.forEach(c -> System.out.println(c.getNomeFormatado()));
        return definirCultivos(jaCultivados, menuProduto);
    }

    private ArrayList<Produto> definirCultivos(ArrayList<Produto> cultivados, MenuProduto menu) {
        boolean finalizarCultivados = false;
        do {
            System.out.printf("\033[1;32m=====| ESTOQUE DISPONÍVEL DE %s |=====\033[m\n", getAgricultor().getPrimeiroNome().toUpperCase());
            ArrayList<Produto> produtos = new Produto().pegarMeusProdutos(getAgricultor().getAgricultorId(), cultivados);

            if (produtos.isEmpty()) {
                System.out.println("Estoque Vazio.");
                return null;
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
            System.out.printf("\033[1;92m\n%s %s adicionado(a)!\n\033[m", cultivo.getTipoProduto().getDescricao().toUpperCase(), cultivo.getNome().toUpperCase());
            cultivados.add(cultivo);

            finalizarCultivados = Interacao.inputBooleano("Encerrar registro de cultivos do estoque? [s/n]: ");
        } while (!finalizarCultivados);

        return cultivados;
    }

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
