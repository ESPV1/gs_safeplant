package br.com.safeplant.view;

import br.com.safeplant.models.Usuario;

import java.awt.*;
import java.io.Console;
import java.util.Scanner;

public class MenuAutenticacao {
    private final Scanner scanner;
    private final Console console;

    public MenuAutenticacao() {
        this.scanner = new Scanner(System.in);
        this.console = System.console();
    }

    public void exibirLogin() {
        System.out.println("\n\nSEJA BEM VINDO AO SAFE PLANT");

        boolean foiAutenticado = false;
        MenuPrincipal menu = new MenuPrincipal();
        do {

            System.out.println("Faça o Login na plataforma ou digite 1 para cadastrar-se!");
            System.out.print("Usuário: ");
            String usuario = this.scanner.next();
            if (usuario.equals("1")) {
                System.out.flush(); // Limpa o console
                exibirCadastro();
                return;
            }
            String senha;
            if (console != null)
                senha = new String(this.console.readPassword("Senha: "));
            else {
                System.out.print("Senha: ");
                senha = scanner.next();
            }

            Usuario usuarioAutenticado = new Usuario();
            foiAutenticado = usuarioAutenticado.autenticarUsuario(usuario, senha);

            if (!foiAutenticado) {
                System.out.println("Usuário ou senha inválidos.\n");
            }
        } while (!foiAutenticado);

        System.out.println("Perfil Autenticado com sucoesso!\nSeja Bem vindo!");
        menu.exibir();
    }


    public void exibirCadastro() {
        System.out.println("Menu Registro");
    }
}
