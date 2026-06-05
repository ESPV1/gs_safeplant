package br.com.safeplant.monitoramentosafras.view;

import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Usuario;
 import br.com.safeplant.monitoramentosafras.view.MenuPrincipal;

 import java.io.Console;
 import java.util.ArrayList;
 import java.util.Scanner;

public class MenuAutenticacao {
    private final Scanner scanner;
    private final Console console;

    public MenuAutenticacao() {
        this.scanner = new Scanner(System.in);
        this.console = System.console();
    }

    public void exibirLogin() {
        System.out.println("\n\n\033[1;32m=====| BEM VINDO AO SAFE PLANT |=====\033[m");

        boolean foiAutenticado = false;
        Usuario usuarioAutenticado = new Usuario();
        do {
            System.out.println("Faça o \033[;31mLogin\033[m na plataforma ou \033[;31mdigite 1\033[m para cadastrar-se!");
            System.out.print("Usuário: ");
            String usuario = this.scanner.next();
            if (usuario.equals("1")) {
                scanner.nextLine(); // Limpa o console
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

            foiAutenticado = usuarioAutenticado.autenticar(usuario, senha);

            if (!foiAutenticado) {
                System.out.println("Usuário ou senha inválidos.\n");
                System.out.flush();
            }
        } while (!foiAutenticado);

        MenuPrincipal menu = new MenuPrincipal(usuarioAutenticado.getUsuarioId());

        System.out.println("Perfil Autenticado com sucoesso!\nSeja Bem vindo!");
        menu.exibir();
    }


    public void exibirCadastro() {
        System.out.println("\n\n\033[1;32m=====| CADASTRO DE USUARIO (1/2) |=====\033[m\n");

        boolean usuarioValido;
        Usuario novoUsuario;
        do {
            System.out.println("Digite as informações solicitadas abaixo:\n");

            System.out.print("Nome Completo: ");
            String nomeCompleto = scanner.nextLine();

            System.out.print("Nome de Usuário: ");
            String nomeUsuario = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            String senha = lerSenha();

            novoUsuario = new Usuario(nomeCompleto, nomeUsuario, email, senha);
            ArrayList<String> validacaoMensagem = novoUsuario.verificarUsuario();

            if (!validacaoMensagem.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                validacaoMensagem.forEach(msg -> System.out.println(msg));
                System.out.println();
                usuarioValido = false;
                continue;
            }
            usuarioValido = novoUsuario.salvarRegistro();

        } while(!usuarioValido);

        System.out.println("\n\n\033[1;32m=====| CADASTRO DE AGRICULTOR (2/2) |=====\033[m\n");
        boolean agricultorValido;
        do {
            System.out.println("Digite as informações solicitadas abaixo:\n");

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Data de Nascimento: ");
            String dataNascimento = scanner.nextLine();

            System.out.print("Celular: ");
            String celular = scanner.nextLine();

            Agricultor novoAgro = new Agricultor(novoUsuario, cpf, "end-004", dataNascimento, celular);
            ArrayList<String> validacaoMensagem = novoAgro.verificarAgro();

            if (!validacaoMensagem.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                validacaoMensagem.forEach(msg -> System.out.println(msg));
                System.out.println();
                agricultorValido = false;
                continue;
            }
            agricultorValido = novoAgro.salvarRegistro();

        } while (!agricultorValido);
        System.out.println("Autenticação e Registro foram concluidos! Autentica-se e acesse!");

        exibirLogin();
    }

    private String lerSenha() {
        if (console != null)
            return new String(this.console.readPassword("Senha: "));
        else {
            System.out.print("Senha: ");
            return scanner.nextLine();
        }
    }
}
