package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.models.Agricultor;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner;
    private final Agricultor agricultorAutenticado;

    public MenuPrincipal(String usuarioId) {
        this.scanner = new Scanner(System.in);
        this.agricultorAutenticado = Agricultor.GetAgricultorPorUsuarioId(usuarioId);
    }

    public void exibir() {
        String opcao;
        do {
            System.out.flush();
            System.out.println("Olá " + agricultorAutenticado.getPrimeiroNome() + "!!");
            System.out.println("\n\nBEM VINDO AO MONITORAMENTO DE SAFRAS BY SAFEPLANT\n");
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
                    agricultorAutenticado.exibirMeuPerfil();
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
