package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Safra;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public void exibir() {
        String opcao;
        do {
            System.out.flush();
            System.out.println("\033[1;32m\n=====| GERENCIAMENTO DE SAFRAS | =====\033[m");
            System.out.println("\033[1;36m[1]\033[m Safras de " + agricultor.getPrimeiroNome());
            System.out.println("\033[1;36m[2]\033[m Registrar nova Safra");
            System.out.println("\033[1;36m[3]\033[m Editar Safras");
            System.out.println("\033[1;36m[4]\033[m Exibir Relatório Semanal");
            System.out.println("\033[1;36m[5]\033[m Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.next();

            switch (opcao) {
                case "1":
                    menuMinhasSafras();
                    break;
                case "2":
                    menuNovaSafra();
                    break;
                case "3":
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
        } while (!opcao.equals("5"));
    }


    public void menuMinhasSafras() {

    }

    public void menuNovaSafra() {
        boolean safraValida = false;
        do {



        } while (!safraValida);
    }

    public void menuEditarSafra() {

    }

    public void menuRelatorio() {

    }
}
