package br.com.safeplant.monitoramentosafras.view;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.safeplant.monitoramentosafras.helper.Interacao;
import br.com.safeplant.monitoramentosafras.models.Agricultor;
import br.com.safeplant.monitoramentosafras.models.Endereco;
import br.com.safeplant.monitoramentosafras.models.Usuario;

/**
 * Menu responsável pelo fluxo de autenticação e cadastro de novos usuários no sistema.
 */
public class MenuAutenticacao {
    private final Scanner scanner;
    private final Console console;

    /**
     * Construtor padrão. Inicializa o scanner e o console para leitura de entrada do usuário.
     */
    public MenuAutenticacao() {
        this.scanner = new Scanner(System.in);
        this.console = System.console();
    }

    /**
     * Exibe a tela de login, solicitando usuário e senha até que a autenticação
     * seja realizada com sucesso, redirecionando para o {@link MenuPrincipal}.
     */
    public void exibirLogin() {
        System.out.println("\n\n\033[1;32m=====| BEM VINDO AO SAFE PLANT |=====\033[m");

        boolean foiAutenticado;
        Usuario usuarioAutenticado = new Usuario();
        do {
            System.out.println("Faça o \033[;31mLogin\033[m na plataforma ou \033[;31mdigite 1\033[m para cadastrar-se!");
            System.out.print("Usuário: ");
            String usuario = this.scanner.nextLine().trim();
            if (usuario.equals("1")) {
                exibirCadastro();
                return;
            }
            String senha = lerSenha().trim();

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


    /**
     * Exibe o fluxo completo de cadastro em três etapas:
     * dados do usuário, dados do agricultor e endereço.
     */
    public void exibirCadastro() {
        System.out.println("\n\n\033[1;32m=====| CADASTRO DE USUARIO (1/3) |=====\033[m\n");
        Usuario novoUsuario = exibirCadastroUsuario();
        if (novoUsuario == null) return;

        System.out.println("\n\n\033[1;32m=====| CADASTRO DE AGRICULTOR (2/3) |=====\033[m\n");
        Agricultor novoAgro = exibirCadastroAgricultor(novoUsuario);
        if (novoAgro == null) return;

        System.out.println("\n\n\033[1;32m=====| CADASTRO DE ENDERECO DO AGRICULTOR (3/3) |=====\033[m\n");
        Endereco novoEndereco = exibirCadastroEndereco(novoAgro);

        boolean usuarioValido = novoUsuario.adicionar();
        boolean enderecoValido = novoEndereco.adicionar();
        if (!enderecoValido) {
            System.out.println("Ocorreu um erro ao cadastraro endereço do Agricultor");
            return;
        }
        novoAgro.setEnderecoId(novoEndereco.getEnderecoId());
        boolean agricultorValido = novoAgro.adicionar();

        if (usuarioValido && agricultorValido) {
            System.out.println("Autenticação e Registro foram concluidos! Autentica-se e acesse!");
            exibirLogin();
        }
        else {
            System.out.printf("Ocorreu um erro durante o registro do %s.", !usuarioValido ? "usuario" : "agricultor");
            exibirCadastro();
        }
    }


    /**
     * Exibe o fluxo completo de cadastro em três etapas:
     * dados do usuário, dados do agricultor e endereço.
     */
    private String lerSenha() {
        if (console != null)
            return new String(this.console.readPassword("Senha: "));
        else {
            System.out.print("Senha: ");
            return scanner.nextLine();
        }
    }

    /**
     * Exibe o formulário de cadastro de {@link Usuario}, coletando e validando
     * os dados antes de retornar a instância criada.
     *
     * @return instância de {@link Usuario} válida, ou {@code null} se o cadastro for cancelado
     */
    private Usuario exibirCadastroUsuario() {
        Usuario novoUsuario;
        boolean usuarioValido;
        do {
            System.out.println("Digite \033[1;31mSair\033[m para encerrar o cadastro.");
            System.out.println("Digite as informações solicitadas abaixo:\n");

            String nomeCompleto = Interacao.inputString("Nome Completo: ");
            if (Interacao.verificarSaida(nomeCompleto)) return null;

            String nomeUsuario = Interacao.inputString("Nome de Usuário: ");
            if (Interacao.verificarSaida(nomeUsuario)) return null;

            String email = Interacao.inputString("Email: ");
            if (Interacao.verificarSaida(email)) return null;

            String senha = lerSenha().trim();
            if (Interacao.verificarSaida(senha)) return null;

            novoUsuario = new Usuario(nomeCompleto, nomeUsuario, email, senha);
            ArrayList<String> validacaoMensagem = novoUsuario.verificarRegistro();

            if (!validacaoMensagem.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                validacaoMensagem.forEach(msg -> System.out.println(msg));
                System.out.println();
                usuarioValido = false;
                continue;
            }
            usuarioValido = true;

        } while(!usuarioValido);

        return novoUsuario;
    }

    /**
     * Exibe o formulário de cadastro de {@link Agricultor} a partir de um {@link Usuario}
     * existente, coletando e validando CPF, data de nascimento e celular.
     *
     * @param novoUsuario instância de {@link Usuario} base para criação do agricultor
     * @return instância de {@link Agricultor} válida, ou {@code null} se o cadastro for cancelado
     */
    private Agricultor exibirCadastroAgricultor(Usuario novoUsuario) {
        Agricultor novoAgro;
        boolean agricultorValido;
        do {
            System.out.println("Digite \033[1;31mSair\033[m para encerrar o cadastro.");
            System.out.println("Digite as informações solicitadas abaixo:\n");

            String cpf = Interacao.inputString("CPF: ");
            if (Interacao.verificarSaida(cpf)) return null;

            String dataNascimento = Interacao.inputData("Data de Nascimento: ");
            if (dataNascimento != null && Interacao.verificarSaida(dataNascimento)) return null;

            String celular = Interacao.inputString("Celular: ");
            if (Interacao.verificarSaida(celular)) return null;

            novoAgro = new Agricultor(novoUsuario, cpf, "end-004", dataNascimento, celular);
            ArrayList<String> validacaoMensagem = novoAgro.verificarRegistro();

            if (!validacaoMensagem.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                validacaoMensagem.forEach(msg -> System.out.println(msg));
                System.out.println();
                agricultorValido = false;
                continue;
            }
            agricultorValido = true;

        } while (!agricultorValido);

        return novoAgro;
    }

    /**
     * Exibe o formulário de cadastro de {@link Endereco}, permitindo busca por CEP
     * via ViaCEP ou preenchimento manual, validando os dados antes de retornar.
     *
     * @param novoAgro instância de {@link Agricultor} ao qual o endereço será associado
     * @return instância de {@link Endereco} válida e confirmada pelo usuário
     */
    private Endereco exibirCadastroEndereco(Agricultor novoAgro) {
        System.out.println("Digite \033[1;31mSair\033[m para encerrar o cadastro.");
        System.out.println("Digite as informações solicitadas abaixo:\n");

        boolean buscaPorCep;
        Endereco novoEndereco = new Endereco();
        do {
            String cep = Interacao.inputString("CEP: ");
            Endereco enderecoPorCep = Endereco.buscarEnderecoPorCep(cep);
            if (enderecoPorCep == null) {
                System.out.println("\033[1;93m⚠ CEP não encontrado ou inválido.\033[m");
                buscaPorCep = Interacao.inputBooleano("Deseja continuar com o cadastro manual? [s/n]: ");
                if (buscaPorCep) {
                    novoEndereco.setCep(cep);
                    novoEndereco.setLogradouro(Interacao.inputString("Logradouro: "));
                    novoEndereco.setBairro(Interacao.inputString("Bairro: "));
                    novoEndereco.setLocalidade(Interacao.inputString("Cidade: "));
                    novoEndereco.setUf(Interacao.inputString("UF: "));
                    novoEndereco.setRegiao(Interacao.inputString("Região: "));
                    novoEndereco.setNumero(Interacao.inputString("Número: "));
                    novoEndereco.setComplemento(Interacao.inputString("Complemento: "));
                }
            } else {
                enderecoPorCep.exibirInfosCep(false);
                buscaPorCep = Interacao.inputBooleano("\nConfirma o endereço acima? [s/n]: ");
                if (buscaPorCep) {
                    System.out.println("Endereço Salvo!\n");
                    novoEndereco = enderecoPorCep;
                    novoEndereco.setNumero(Interacao.inputString("Número: "));
                    if (enderecoPorCep.getComplemento().isEmpty())
                        novoEndereco.setComplemento(Interacao.inputString("Complemento: "));
                }
            }
            if (!buscaPorCep) continue;

            ArrayList<String> erros = novoEndereco.verificarRegistro();
            if (!erros.isEmpty()) {
                System.out.println("\n\033[1;31mErros encontrados: \033[m");
                erros.forEach(msg -> System.out.println(msg));
                System.out.println();
                buscaPorCep = false;
            }

        } while (!buscaPorCep);

        return novoEndereco;
    }
}
