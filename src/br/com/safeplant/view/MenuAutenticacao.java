package br.com.safeplant.view;

import java.util.Scanner;

public class MenuAutenticacao {
    private final Scanner scanner;

    public MenuAutenticacao() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirLogin() {
        System.out.println("SEJA BEM VINDO AO SAFE PLANT");
        System.out.println("Faça o Login na plataforma");
        System.out.println("Digite 1 caso não possua login para cadastrar-se!\n\n");

        System.out.print("Usuário: ");
        String usuario = scanner.next();
        if (usuario.equals("1")) {
            System.out.flush(); // Limpa o console
            exibirCadastro();
            return;
        }
        String senha = new String(System.console().readPassword("Senha: "));



    }


    public void exibirCadastro() {
        System.out.println("Menu Registro");
    }
}
