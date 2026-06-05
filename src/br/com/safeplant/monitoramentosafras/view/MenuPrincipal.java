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
        System.out.println("\033[1;32m\n\nBEM VINDO AO MONITORAMENTO DE SAFRAS - BY SAFEPLANT\033[m");
        do {
            System.out.flush();
            System.out.println("\033[1;36mOlá " + agricultorAutenticado.getPrimeiroNome() + "!!\033[m\n");
            System.out.println("\033[1;36m[1]\033[m Gerenciamento de Safra");
            System.out.println("\033[1;36m[2]\033[m Gerenciamento de Produtos");
            System.out.println("\033[1;36m[3]\033[m Meu Perfil");
            System.out.println("\033[1;36m[4]\033[m Encerrar Sessão");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.next();

            switch (opcao) {
                case "1":
                    new MenuSafra(agricultorAutenticado).exibir();
                    break;
                case "2":
                    new MenuProduto(agricultorAutenticado).exibir();
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
