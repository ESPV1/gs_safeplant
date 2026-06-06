package br.com.safeplant.monitoramentosafras.interfaces;

import br.com.safeplant.monitoramentosafras.models.Produto;

import java.util.ArrayList;

public interface IProduto extends IOperacoesPadrao {
    ArrayList<Produto> pegarMeusProdutos();
    ArrayList<Produto> exibirProdutos();
}
