package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.enums.TipoProduto;
import br.com.safeplant.monitoramentosafras.helper.Verificador;
import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuProduto {
    private final Scanner scanner;
    private Agricultor agricultor;

    public MenuProduto(Agricultor agricultor) {
        this.scanner = new Scanner(System.in);
        this.agricultor = agricultor;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public void exibir() {
        String opcao;
        do {
            System.out.flush();
            System.out.println("\033[1;32m\n=====| GERENCIAMENTO DE PRODUTOS E CULTIVOS | =====\033[1;32m");
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

    public void menuProdutosCultivados() {
        System.out.println("Meus produtos cultivados");
    }

    public void menuExibirEstoque() {
        System.out.printf("\033[1;32m=====| ESTOQUE DE %s |=====\033[m]\n", agricultor.getPrimeiroNome().toUpperCase());
        ArrayList<Produto> produtos = new Produto().pegarMeusProdutos(this.agricultor.getAgricultorId());

        if (produtos.isEmpty()) {
            System.out.println("Estoque Vazio.");
            return;
        }

        System.out.println("SUMÁRIO");
        System.out.println("\033[1;35m[LEGUME]\033[m");
        System.out.println("\033[1;92m[VEGETAL]\033[m");
        System.out.println("\033[1;33m[FRUTA]\033[m");
        System.out.println("\033[1;93m[CEREAL]\033[m");
        System.out.println("\033[1;34m[LACTINIO]\033[m\n");
        System.out.println("ESTOQUE: ");
        for (Produto prod : produtos) {
            mostrarProdutoPorCor(prod.getNome(), prod.getNomeCientifico(), prod.getTipoProduto());
        }
        System.out.println("Pressione qualquer tecla para encerrar...");
        String tmp = scanner.nextLine();
    }

    public void menuRegistrarNovoProduto() {
        System.out.println("\033[1;32m=====| REGISTRO DE PRODUTO |=====\033[m");

        boolean produtoValido;
        Produto novoProduto;
        do {
            System.out.println("Digite as informações solicitadas abaixo: ");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Nome Científico: ");
            String nomeCientifico = scanner.nextLine();

            TipoProduto tipoProduto = selecionarTipoProduto();

            System.out.print("Tempo médio da colheita em dias: ");
            int diasColheita = Verificador.verificarInteiro(scanner.nextLine());

            novoProduto = new Produto(nome, nomeCientifico, diasColheita, tipoProduto, agricultor.getAgricultorId());
            ArrayList<String> erros = novoProduto.verificarRegistroProduto();

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

    public void menuEditarProduto() {

    }

    public TipoProduto selecionarTipoProduto() {
        boolean valorTipoValido;

        String tipos = Arrays.stream(TipoProduto.values())
                .map(Enum::name)
                .collect(Collectors.joining(" - "));
        String valor;
        do {
            System.out.printf("Tipos Disponíveis: %s%n", tipos);
            System.out.print("Tipo do Produto: ");
            valor = scanner.nextLine();

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

    public void mostrarProdutoPorCor(String nome, String nomeCientifico, TipoProduto tipoProduto) {
        String textoBase = "▸ \033[1;corm%s (%s)\033[m\n";

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
        System.out.printf(textoFinal, nome, nomeCientifico);
    }
}
