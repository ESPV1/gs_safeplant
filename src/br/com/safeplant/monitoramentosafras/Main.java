package br.com.safeplant.monitoramentosafras;

import br.com.safeplant.monitoramentosafras.view.MenuAutenticacao;

public class Main {
    public static void main(String[] args) {
        try {
            MenuAutenticacao menuAutenticacao = new MenuAutenticacao();
            menuAutenticacao.exibirLogin();
        }
        catch (Exception ex) {
            System.out.println("\033[1;31mOcorreu um erro Inesperado durante o programa SafePlant\033[m");
            ex.printStackTrace();
        }
    }
}