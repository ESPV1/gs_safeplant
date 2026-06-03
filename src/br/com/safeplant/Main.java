package br.com.safeplant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n\nBEM VINDO AO SISTEMA SAFEPLANT\n");

            System.out.println("[1] Gerenciamento de Safra");
            System.out.println("[2] Gerenciamento de Produtos");
            System.out.println("[3] Meu Perfil");
            System.out.println("[4] Encerrar Sessão");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.next();

            switch (opcao) {
                case "1":
                    System.out.println("Gerenciando Safras");
                    break;
                case "2":
                    System.out.println("Gerenciando Produtos");
                    break;
                case "3":
                    System.out.println("Meu perfil");
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

            if (opcao.equals("4"))
                break;

        } while (true);

        System.out.println("Programa encerrado! Volte Sempre!");

    }
}