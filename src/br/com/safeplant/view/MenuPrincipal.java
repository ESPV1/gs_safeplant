package br.com.safeplant.view;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        String opcao;
        do {
            System.out.println("\n\nBEM VINDO AO SISTEMA SAFEPLANT\n");
            System.out.println("[1] Gerenciamento de Safra");
            System.out.println("[2] Gerenciamento de Produtos");
            System.out.println("[3] Meu Perfil");
            System.out.println("[4] Encerrar Sessão");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.next();

            switch (opcao) {
                case "1":
                    new MenuSafra().exibir();
                    break;
                case "2":
                    new MenuProduto().exibir();
                    break;
                case "3":
                    System.out.println("Meu perfil");
                    break;
                case "4" :
                    System.out.println("Programa encerrado! Volte Sempre!");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (!opcao.equals("4"));
    }
}
