package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.models.Agricultor;

import java.util.Scanner;

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
            opcao = scanner.next();

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

    }

    public void menuExibirEstoque() {

    }

    public void menuRegistrarNovoProduto() {

    }

    public void menuEditarProduto() {

    }
}
