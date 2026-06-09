package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.models.Agricultor;

import java.util.Scanner;

/**
 * Menu principal do sistema, exibido após autenticação bem-sucedida,
 * permitindo navegar entre as funcionalidades disponíveis.
 */
public class MenuPrincipal {
    private final Scanner scanner;
    private final Agricultor agricultorAutenticado;

    /**
     * Cria o menu principal buscando o {@link Agricultor} correspondente
     * ao ID de usuário autenticado.
     *
     * @param usuarioId ID do usuário autenticado na sessão
     */
    public MenuPrincipal(String usuarioId) {
        this.scanner = new Scanner(System.in);
        this.agricultorAutenticado = Agricultor.getAgricultorPorUsuarioId(usuarioId);
    }

    /**
     * Exibe o menu principal do sistema, permitindo navegar entre
     * gerenciamento de safras, produtos, perfil e encerramento de sessão.
     */
    public void exibir() {
        String opcao;
        do {
            System.out.println("\033[1;32m\n=====| SISTEMA DE MONITORAMENTO DE SAFRAS BY SAFEPLANT |=====\033[m");
            System.out.println("\n\033[1;36mBem vindo de volta " + agricultorAutenticado.getPrimeiroNome() + "!!\033[m");
            System.out.println("\033[1;36m[1]\033[m Gerenciamento de Safra");
            System.out.println("\033[1;36m[2]\033[m Gerenciamento de Produtos");
            System.out.println("\033[1;36m[3]\033[m Meu Perfil");
            System.out.println("\033[1;36m[4]\033[m Encerrar Sessão");
            System.out.print("\033[1mEscolha uma opção:\033[m ");
            opcao = scanner.next().trim();

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
                    System.out.println("\033[1;93mPrograma encerrado! Volte Sempre!\033[m");
                    break;
                default:
                    System.out.println("\033[1;31mOpção inválida.\033[m");
                    break;
            }
        } while (!opcao.equals("4"));
    }
}
