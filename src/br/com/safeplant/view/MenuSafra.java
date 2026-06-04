package br.com.safeplant.view;

import java.util.Scanner;

public class MenuSafra {
    private final Scanner scanner;

    public MenuSafra() {
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        System.out.println("Menu Safra");
    }
}
