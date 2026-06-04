package br.com.safeplant;

import br.com.safeplant.view.MenuAutenticacao;
import br.com.safeplant.view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        MenuAutenticacao menuAutenticacao = new MenuAutenticacao();
        menuAutenticacao.exibirLogin();
    }
}