package br.com.safeplant.monitoramentosafras.helper;

import java.util.Arrays;

/**
 * Classe utilitária responsável por validar dados de entrada do sistema,
 * como CPF, CEP, datas e coordenadas geográficas.
 */
public class Verificador {

    /**
     * Verifica se a data informada é válida e possui formato reconhecido.
     *
     * @param data {@link String} contendo a data a ser verificada
     * @return {@code true} se a data for válida, {@code false} caso contrário
     */
    public static boolean verificarData(String data) {
        if (data == null || data.isEmpty())
            return false;

        return !Interacao.formataData(data).isEmpty();
    }

    /**
     * Verifica se o CPF informado é válido, removendo caracteres não numéricos
     * e rejeitando sequências conhecidas como inválidas.
     *
     * @param cpf {@link String} contendo o CPF a ser verificado
     * @return {@code true} se o CPF for válido, {@code false} caso contrário
     */
    public static boolean verificarCPF(String cpf) {
        if (cpf.isEmpty())
            return false;

        String[] invalidCPFs = {
                "11111111111", "22222222222", "33333333333",
                "44444444444", "55555555555", "66666666666",
                "77777777777", "88888888888", "99999999999",
                "00000000000", "12345678909"
        };

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11)
            return false;

        return !Arrays.asList(invalidCPFs).contains(cpf);
    }

    /**
     * Converte a {@link String} informada para inteiro, retornando {@code 0}
     * caso o valor não contenha dígitos.
     *
     * @param valor {@link String} contendo o valor a ser verificado e convertido
     * @return int representando o valor convertido, ou {@code 0} se inválido
     */
    public static int verificarInteiro(String valor) {
        valor = valor.trim();
        if (!valor.matches(".*\\d.*"))
            return 0;
        return Integer.parseInt(valor);
    }

    /**
     * Verifica se a {@link String} informada representa um número decimal válido.
     *
     * @param valor {@link String} contendo o valor a ser verificado
     * @return {@code true} se o valor for um double válido, {@code false} caso contrário
     */
    public static boolean verificarDouble(String valor) {
        valor = valor.trim();
        if (valor == null || valor.isEmpty())
            return false;
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se o CEP informado é válido, removendo caracteres não numéricos
     * e confirmando que possui exatamente 8 dígitos.
     *
     * @param cep {@link String} contendo o CEP a ser verificado
     * @return {@code true} se o CEP for válido, {@code false} caso contrário
     */
    public static boolean verificarCEP(String cep) {
        cep = cep.replaceAll("[^\\d]", "");
        return cep.length() == 8;
    }

    /**
     * Verifica se o valor de latitude está dentro do intervalo válido entre -90° e 90°.
     *
     * @param latitude double representando a latitude a ser verificada
     * @return {@code true} se a latitude for válida, {@code false} caso contrário
     */
    public static boolean verificarLatitude(double latitude) {
        return !(latitude < -90.0) && !(latitude > 90.0);
    }

    /**
     * Verifica se o valor de longitude está dentro do intervalo válido entre -180° e 180°.
     *
     * @param longitude double representando a longitude a ser verificada
     * @return {@code true} se a longitude for válida, {@code false} caso contrário
     */
    public static boolean verificarLongitude(double longitude) {
        return !(longitude < -180.0) && !(longitude > 180.0);
    }
}
