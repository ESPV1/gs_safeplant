package br.com.safeplant.monitoramentosafras.view;

import java.util.Scanner;

public class MenuProduto {
    private final Scanner scanner;

    public MenuProduto() {
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        System.out.println("Menu Produto");
    }
}
