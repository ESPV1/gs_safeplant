package br.com.safeplant.monitoramentosafras;

import br.com.safeplant.monitoramentosafras.models.Endereco;
import br.com.safeplant.monitoramentosafras.view.MenuAutenticacao;

public class Main {
    public static void main(String[] args) {
        MenuAutenticacao menuAutenticacao = new MenuAutenticacao();
        menuAutenticacao.exibirLogin();
    }
}