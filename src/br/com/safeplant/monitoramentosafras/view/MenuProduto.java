package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.enums.StatusSafra;
import br.com.safeplant.monitoramentosafras.enums.TipoProduto;
import br.com.safeplant.monitoramentosafras.helper.Interacao;
import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Produto;
import br.com.safeplant.monitoramentosafras.models.Safra;
import com.sun.source.doctree.EscapeTree;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Menu responsável pelo gerenciamento de produtos do agricultor autenticado,
 * permitindo visualizar, registrar e editar produtos.
 */
public class MenuProduto {
    private final Scanner scanner;
    private Agricultor agricultor;

    /**
     * Cria o menu de produtos para o agricultor informado.
     *
     * @param agricultor instância de {@link Agricultor} autenticado na sessão
     */
    public MenuProduto(Agricultor agricultor) {
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
    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    /**
     * Exibe o menu principal de gerenciamento de produtos,
     * permitindo navegar entre as opções disponíveis.
     */
    public void exibir() {
        String opcao;
        do {
            System.out.flush();
            System.out.println("\033[1;32m\n=====| GERENCIAMENTO DE PRODUTOS E CULTIVOS |=====\033[1;32m");
            System.out.println("\033[1;36m[1]\033[m Produtos Cultivados");
            System.out.println("\033[1;36m[2]\033[m Exibir Estoque");
            System.out.println("\033[1;36m[3]\033[m Registrar Novo Produto");
            System.out.println("\033[1;36m[4]\033[m Editar Produto");
            System.out.println("\033[1;36m[5]\033[m Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    menuProdutosCultivados();
                    break;
                case "2":
                    menuExibirEstoque();
                    break;
                case "3":
                    menuRegistrarNovoProduto();
                    break;
                case "4":
                    menuEditarProduto();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (!opcao.equals("5"));
    }

    /**
     * Exibe os produtos cultivados pelo agricultor autenticado.
     */
    public void menuProdutosCultivados() {
        try {
            System.out.println("\033[1;32m=====| PRODUTOS CULTIVADOS |=====\033[m");
            ArrayList<Safra> safraDoAgricultor = Safra.pegarMinhasSafras(getAgricultor().getAgricultorId());
            System.out.printf("\033[1m%s possui %d Safras em seu nome\033[m", getAgricultor().getPrimeiroNome(), safraDoAgricultor.size());
            for (Safra safra : safraDoAgricultor) {
                System.out.printf("\n\n\033[1;94m=====| %s |=====\033[m\n", safra.getNomeSafra().toUpperCase());
                safra.exibir();
            }
            System.out.println("\n\033[1;94m===========================\033[m");
            Interacao.aguardarTecla(scanner);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro ao tentar ver os produtos cultivados do agricultor\033[m\n");
        }
    }

    /**
     * Exibe o estoque de produtos do agricultor autenticado,
     * listando cada item com a sua cor correspondente ao tipo.
     */
    private void menuExibirEstoque() {
        System.out.printf("\033[1;32m=====| ESTOQUE DE %s |=====\033[m]\n", agricultor.getPrimeiroNome().toUpperCase());
        ArrayList<Produto> produtos = new Produto().pegarMeusProdutos(this.agricultor.getAgricultorId());

        if (produtos.isEmpty()) {
            System.out.println("Estoque Vazio.");
            return;
        }

        for (Produto prod : produtos) {
            mostrarProdutoPorCor(prod);
        }
        Interacao.aguardarTecla(scanner);
    }

    /**
     * Exibe o estoque de produtos sobre uma lista enviada,
     * listando cada item com a sua cor correspondente ao tipo.
     *
     * @param produtosAListar Lista de produtos que será listada.
     */
    private void menuExibirEstoque(ArrayList<Produto> produtosAListar) {
        System.out.printf("\033[1;32m=====| ESTOQUE DE %s |=====\033[m]\n", agricultor.getPrimeiroNome().toUpperCase());
        if (produtosAListar.isEmpty()) {
            System.out.println("Estoque Vazio.");
            return;
        }

        System.out.println("\033[1;94m=====| SUMÁRIO |=====\033[m");
        System.out.println("\033[1;35m[LEGUME]\033[m");
        System.out.println("\033[1;92m[VEGETAL]\033[m");
        System.out.println("\033[1;33m[FRUTA]\033[m");
        System.out.println("\033[1;93m[CEREAL]\033[m");
        System.out.println("\033[1;34m[LACTINIO]\033[m\n");
        System.out.println("\033[1;94m=====| ESTOQUE |=====\033[m");
        for (int i = 0; i < produtosAListar.size(); i++) {
            Produto prod = produtosAListar.get(i);
            mostrarProdutoPorCor(prod.getNome(), prod.getNomeCientifico(), prod.getTipoProduto(), i+1);
        }
    }

    /**
     * Exibe o fluxo de registro de um novo produto, coletando
     * nome, nome científico, tipo e tempo de colheita, validando
     * os dados antes de persistir.
     */
    public void menuRegistrarNovoProduto() {
        System.out.println("\033[1;32m=====| REGISTRO DE PRODUTO |=====\033[m");

        boolean produtoValido;
        Produto novoProduto;
        do {
            System.out.println("Digite as informações solicitadas abaixo: ");
            System.out.println("Digite \033[1;31mSair\033[m para encerrar o cadastro.");

            String nome = Interacao.inputString("Nome: ");
            if (Interacao.verificarSaida(nome)) return;

            String nomeCientifico = Interacao.inputString("Nome Científico: ");
            if (Interacao.verificarSaida(nomeCientifico)) return;

            TipoProduto tipoProduto = selecionarTipoProduto();

            int diasColheita = Interacao.inputInteiro("Tempo médio da colheita em dias: ");
            if (diasColheita == -1) return;

            novoProduto = new Produto(nome, nomeCientifico, diasColheita, tipoProduto, agricultor.getAgricultorId());
            ArrayList<String> erros = novoProduto.verificarRegistro();

            if (!erros.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                erros.forEach(msg -> System.out.println(msg));
                System.out.println();
                produtoValido = false;
                continue;
            }
            produtoValido = true;
        } while(!produtoValido);

        boolean produtoSalvo = novoProduto.adicionar();
        if (produtoSalvo)
            System.out.println("Novo produto adicionado!");
        else
            System.out.println("Falha ao salvar o produto!");
    }

    /**
     * Exibe o fluxo para gerenciamento dos produtos existentes.
     */
    public void menuEditarProduto() {
        try {
            String opcao;
            do {
                Produto produto = escolherProduto();

                if (produto == null)
                    return;

                System.out.printf("\n\033[1;96m=====| OPERAÇÕES PARA %s |=====\033[m\n", produto.getNome().toUpperCase());
                System.out.println("\033[1;36m[1]\033[m Atualizar Nome");
                System.out.println("\033[1;36m[2]\033[m Atualizar Nome Científico");
                System.out.println("\033[1;36m[3]\033[m Atualizar Tempo médio de colheita");
                System.out.println("\033[1;36m[4]\033[m Atualizar Tipo do Produto");
                System.out.println("\033[1;36m[5]\033[m Excluir Produto");
                System.out.println("\033[1;36m[6]\033[m Voltar");
                opcao = Interacao.inputString("Escolha uma opção: ");

                switch (opcao) {
                    case "1":
                        atualizarNomes(produto, "comum");
                        return;
                    case "2":
                        atualizarNomes(produto, "cientifico");
                        return;
                    case "3":
                        atualizarTempoMedioColheita(produto);
                        return;
                    case "4":
                        atualizarTipoProduto(produto);
                        return;
                    case "5":
                        excluirProduto(produto);
                        return;
                    case "6":
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } while (!opcao.equals("6"));
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro durante a execução \033[m");
            return;
        }
    }

    /**
     * Escolhe o produto que será editado no menuEditarProduto
     *
     * @return {@link Produto} Retorna um objeto do tipo Produto que será manipulado
     * */
    public Produto escolherProduto() {
        try {
            ArrayList<Produto> meusProdutos = new Produto().pegarMeusProdutos(getAgricultor().getAgricultorId());
            menuExibirEstoque(meusProdutos);
            System.out.println("\n\033[1;93mDigite 0 para voltar ao menu\033[m");
            int opcao = Interacao.inputInteiro("Escolha o produto: ");

            if (opcao == 0)
                return null;

            while (opcao < 0 || opcao > meusProdutos.size()) {
                System.out.println("\033[1;31mOpção inválida.\033[m");
                opcao = Interacao.inputInteiro("Escolha o produto: ");
            }
            return meusProdutos.get(opcao-1);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mProduto selecionado não existe\033[m");
            return null;
        }
    }

    /**
     * Atualiza o nome cientifico ou comum com base num objeto produto e tipo do nome enviado
     *
     * @param produto Produto que será editado
     * @param tipoNome do tipo {@link String} indicando qual nome será editado
     * */
    public void atualizarNomes(Produto produto, String tipoNome) {
        if (!(tipoNome.equalsIgnoreCase("cientifico") || tipoNome.equalsIgnoreCase("comum"))) {
            System.out.println("Tipo de nome inválido");
            return;
        }
        try {
            if (tipoNome.equalsIgnoreCase("cientifico")) {
                System.out.println("\n\033[1;93mDigite 0 para voltar o menu\033[m");
                System.out.printf("\033[1mNome Científico atual: %s\033[m\n", produto.getNomeCientifico());
                String novoNomeCientifico = Interacao.inputString("\033[1mNovo nome científico: \033[m ");

                while (novoNomeCientifico.isEmpty() || novoNomeCientifico.equalsIgnoreCase(produto.getNomeCientifico()) || novoNomeCientifico.split(" ").length != 2) {
                    if (novoNomeCientifico.equalsIgnoreCase("0")) return;

                    System.out.println("\n\033[1;93mNome científico inválido. Tente novamente\033[m");
                    System.out.printf("\033[1mNome Científico atual: %s\033[m\n", produto.getNomeCientifico());
                    novoNomeCientifico = Interacao.inputString("\033[1mNovo nome científico: \033[m ");
                }
                produto.setNomeCientifico(novoNomeCientifico);
            } else {
                System.out.println("\n\033[1;93mDigite 0 para voltar o menu\033[m");
                System.out.printf("\033[1mNome atual: %s\033[m\n", produto.getNome());
                String novoNome = Interacao.inputString("\033[1mNovo nome: \033[m ");

                while (novoNome.isEmpty() || novoNome.equalsIgnoreCase(produto.getNome())) {
                    if (novoNome.equalsIgnoreCase("0")) return;

                    System.out.println("\n\033[1;93mNome inválido. Tente novamente\033[m");
                    System.out.printf("\033[1mNome atual: %s\033[m\n", produto.getNome());
                    novoNome = Interacao.inputString("\033[1mNovo nome: \033[m ");
                }
                produto.setNome(novoNome);
            }

            boolean produtoEditado = produto.editar();
            if (produtoEditado)
                System.out.println("\033[1;92mNome do produto alterado com sucesso!\033[m");
            else
                System.out.println("\033[1;31mFalha ao editar nome do produto\033[m");
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro durante a atualização do nome\033[m");
        }
    }

    /**
     * Exibe o fluxo e atualiza o Tempo médio de colheita do produto em dias
     *
     * @param produto {@link Produto} que será editado
     * */
    public void atualizarTempoMedioColheita(Produto produto) {
        try {
            System.out.println("\033[1;93mDigite 0 para voltar o menu\033[m");
            System.out.printf("\033[1mTempo médio de colheita atual: %d dias\033[m\n", produto.getTempoColheitaEmDias());
            int novoTempoColheita = Interacao.inputInteiro("\033[1mNovo tempo de colheita: \033[m ");

            while (novoTempoColheita < 1 || novoTempoColheita > 999) {
                if (novoTempoColheita == 0) return;

                System.out.println("\033[1;93mDigite 0 para voltar o menu\033[m");
                System.out.printf("\033[1mTempo médio de colheita atual: %d dias\033[m\n", produto.getTempoColheitaEmDias());
                novoTempoColheita = Interacao.inputInteiro("\033[1mNovo tempo de colheita: \033[m ");
            }

            produto.setTempoColheitaEmDias(novoTempoColheita);

            boolean produtoEditado = produto.editar();
            if (produtoEditado)
                System.out.println("\033[1;92mTempo de colheita em dias do produto alterado com sucesso!\033[m");
            else
                System.out.println("\033[1;31mFalha ao editar o tempo de colheita\033[m");
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro durante a atualização do tempo médio de colheita\033[m");
        }
    }

    /**
     * Exibe o fluxo e atualiza o tipo do produto
     *
     * @param produto {@link Produto} que será editado
     * */
    public void atualizarTipoProduto(Produto produto) {
        try {
            TipoProduto novoTipoProduto;
            do {
                novoTipoProduto = selecionarTipoProduto(produto.getTipoProduto());
                if (novoTipoProduto == null)
                    return;

                if (novoTipoProduto.equals(produto.getTipoProduto())) {
                    System.out.println("\033[1;31mNovo tipo do produto não pode ser igual ao atual.\033[m");
                } else {
                    produto.setTipoProduto(novoTipoProduto);

                    boolean produtoEditado = produto.editar();
                    if (produtoEditado) {
                        System.out.println("\033[1;92mTipo do produto alterado com sucesso!\033[m");
                        return;
                    } else {
                        System.out.println("\033[1;31mFalha ao editar o tipo do produto\033[m");
                    }
                }
            } while (true);
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro durante a atualização do tipo do produto\033[m");
        }
    }

    /**
     * Exibe as informações do produto e excluí o produto
     * do sistema após confirmação
     *
     * @param produto {@link Produto} que será removido
     * */
    public void excluirProduto(Produto produto) {
        try {
            mostrarProduto(produto);
            System.out.println("\033[1;94m==================\033[m\n");
            boolean produtoIraExcluir = Interacao.inputBooleano("Você deseja realmente excluir o produto " + produto.getNomeFormatado() + " [s/n]: ");
            if (!produtoIraExcluir) {
                System.out.println("\033[1;92mOperação Cancelada\033[m");
                return;
            }
            boolean produtoFoiExcluido = produto.remover();
            if (produtoFoiExcluido)
                System.out.println("\033[1;92mProduto " + produto.getNome() +" foi excluído com sucesso do estoque!\033[m");
            else
                System.out.println("\033[1;31mFalha ao tentar excluir o produto"+ produto.getNome() +"\033[m");
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro durante a remoção do produto\033[m");
        }
    }

    /**
     * Solicita ao usuário que selecione um {@link TipoProduto} válido
     * dentre os disponíveis no sistema.
     *
     * @return {@link TipoProduto} selecionado pelo usuário
     */
    public TipoProduto selecionarTipoProduto() {
        boolean valorTipoValido;

        String tipos = Arrays.stream(TipoProduto.values())
                .map(Enum::name)
                .collect(Collectors.joining(" - "));
        String valor;
        do {
            System.out.printf("\033[1mTipos Disponíveis:\033[m %s", tipos);
            System.out.print("\033[1mTipo do Produto:\033[m ");
            valor = scanner.nextLine();

            String finalValor = valor;
            valorTipoValido = Arrays.stream(TipoProduto.values())
                    .anyMatch(t -> t.name().equalsIgnoreCase(finalValor));
            if (!valorTipoValido) {
                System.out.println();
                System.out.println("\033[1;31mTipo de Produto inválido\033[m\n");
            }

        } while (!valorTipoValido);

        return TipoProduto.valueOf(valor.toUpperCase());
    }

    /**
     * Solicita ao usuário que selecione um {@link TipoProduto} válido
     * dentre os disponíveis no sistema.
     *
     * @param tipoAtualProduto Tipo atual do produto antes da edição
     * @return {@link TipoProduto} selecionado pelo usuário
     */
    public TipoProduto selecionarTipoProduto(TipoProduto tipoAtualProduto) {
        boolean valorTipoValido;

        String tipos = Arrays.stream(TipoProduto.values())
                .map(tp -> tp.getDescricao().toUpperCase())
                .collect(Collectors.joining(" - "));
        String valor;
        do {
            System.out.println("\033[1;93mDigite 0 para voltar ao menu\033[m");
            System.out.println("\033[1mTipo atual do produto: \033[1;94m" + tipoAtualProduto.getDescricao().toUpperCase() + "\033[m");
            System.out.printf("\033[1mTipos Disponíveis:\033[m %s\n", tipos);
            System.out.print("\033[1mNovo tipo do Produto:\033[m ");
            valor = scanner.nextLine();

            if (valor.equalsIgnoreCase("0"))
                return null;

            String finalValor = valor;
            valorTipoValido = Arrays.stream(TipoProduto.values())
                    .anyMatch(t -> t.name().equalsIgnoreCase(finalValor));
            if (!valorTipoValido) {
                System.out.println();
                System.out.println("Tipo de Produto inválido");
            }

        } while (!valorTipoValido);

        return TipoProduto.valueOf(valor.toUpperCase());
    }

    /**
     * Exibe o nome e nome científico do produto formatados com a cor
     * ANSI correspondente ao seu {@link TipoProduto}.
     *
     * @param produto           Produto a ser exibido
     */
    public void mostrarProdutoPorCor(Produto produto) {
        String textoBase = "▸ \033[1;corm%s\033[m \033[1m| %s (%s) | Média de \033[4m%d dias\033[m para colheita\033[m\n";

        String textoFinal;
        switch (produto.getTipoProduto().getDescricao().toLowerCase()) {
            case "legume":
                textoFinal = textoBase.replace("cor", "35"); // roxo
                break;
            case "vegetal":
                textoFinal = textoBase.replace("cor", "92"); // verde claro
                break;
            case "fruta":
                textoFinal = textoBase.replace("cor", "33"); // laranja
                break;
            case "cereal":
                textoFinal = textoBase.replace("cor", "93"); // amarelo
                break;
            case "lactinio":
                textoFinal = textoBase.replace("cor", "34"); // azul
                break;
            default:
                textoFinal = textoBase.replace("cor", "36"); // ciano
                break;
        }
        System.out.printf(textoFinal, produto.getTipoProduto().getDescricao().toUpperCase(), produto.getNome(), produto.getNomeCientifico(), produto.getTempoColheitaEmDias());
    }

    /**
     * Exibe o nome e nome científico do produto formatados com a cor
     * ANSI correspondente ao seu {@link TipoProduto}, precedidos por um índice.
     *
     * @param nome           nome do produto
     * @param nomeCientifico nome científico do produto
     * @param tipoProduto    tipo do produto para definição da cor
     * @param index          índice a ser exibido antes do nome do produto
     */
    public void mostrarProdutoPorCor(String nome, String nomeCientifico, TipoProduto tipoProduto, int index) {
        String textoBase = "\033[1;36m[%d]\033[m \033[1;corm%s (%s)\033[m\n";

        String textoFinal;
        switch (tipoProduto.getDescricao().toLowerCase()) {
            case "legume":
                textoFinal = textoBase.replace("cor", "35"); // roxo
                break;
            case "vegetal":
                textoFinal = textoBase.replace("cor", "92"); // verde claro
                break;
            case "fruta":
                textoFinal = textoBase.replace("cor", "33"); // laranja
                break;
            case "cereal":
                textoFinal = textoBase.replace("cor", "93"); // amarelo
                break;
            case "lactinio":
                textoFinal = textoBase.replace("cor", "34"); // azul
                break;
            default:
                textoFinal = textoBase.replace("cor", "36"); // ciano
                break;
        }
        System.out.printf(textoFinal, index, nome, nomeCientifico);
    }

    /**
     * Exibir detalhes untitários de um produto
     *
     * @param produto {@link Produto} que será exibido na tela
     * */
    public void mostrarProduto(Produto produto) {
        System.out.println("\033[1;93m=====| DETALHES DO PRODUTO |=====\033[m");
        System.out.printf("\033[1mNome:\033[m %s\n", produto.getNomeFormatado());
        System.out.printf("\033[1mNome Científico:\033[m \033[4m%s\033[m\n", produto.getNomeCientifico());
        System.out.printf("\033[1mTipo do Produto:\033[m %s\n", produto.getTipoProduto().getDescricao());
        System.out.printf("\033[1mTempo de Colheita:\033[m %d dias\n", produto.getTempoColheitaEmDias());
    }
}
