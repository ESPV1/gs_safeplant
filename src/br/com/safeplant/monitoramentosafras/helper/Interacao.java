package br.com.safeplant.monitoramentosafras.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe utilitária responsável por gerenciar as interações de entrada
 * e saída com o usuário via console.
 */
public class Interacao {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Exibe um rótulo e lê uma linha de texto digitada pelo usuário.
     *
     * @param label {@link String} rótulo exibido antes da entrada
     * @return {@link String} valor digitado pelo usuário
     */
    public static String inputString(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    /**
     * Exibe um rótulo e lê um valor inteiro digitado pelo usuário.
     * Retorna {@code -1} se o usuário digitar "Sair".
     *
     * @param label {@link String} rótulo exibido antes da entrada
     * @return int valor inteiro lido, {@code 0} se inválido ou {@code -1} se cancelado
     */
    public static int inputInteiro(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();
        if (verificarSaida(valor)) return -1;
        return Verificador.verificarInteiro(valor);
    }

    /**
     * Exibe um rótulo e solicita ao usuário uma resposta de confirmação (S/N),
     * repetindo até que uma opção válida seja informada.
     *
     * @param label {@link String} rótulo exibido antes da entrada
     * @return {@code true} se o usuário confirmar com "S", {@code false} para "N"
     */
    public static boolean inputBooleano(String label) {
        String valor;
        do {
            System.out.print(label);
            valor = scanner.nextLine().trim();
            if (!valor.equalsIgnoreCase("s") && !valor.equalsIgnoreCase("n"))
                System.out.println("Opção inválida. Digite S ou N");
        } while (!valor.equalsIgnoreCase("s") && !valor.equalsIgnoreCase("n"));

        return valor.equalsIgnoreCase("s");
    }

    /**
     * Exibe um rótulo e lê uma data digitada pelo usuário, aceitando "hoje"
     * como atalho para a data atual. Retorna {@code null} se a data for inválida.
     *
     * @param label {@link String} rótulo exibido antes da entrada
     * @return {@link String} data formatada no padrão DD/MM/YYYY, ou {@code null} se inválida
     */
    public static String inputData(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();
        if (valor.equalsIgnoreCase("hoje")) return formataData(LocalDate.now().toString());
        if (!Verificador.verificarData(valor)) return null;
        return formataData(valor);
    }

    /**
     * Exibe um rótulo e lê um valor decimal digitado pelo usuário.
     * Retorna {@link Double#NEGATIVE_INFINITY} se cancelado e {@link Double#NaN} se inválido.
     *
     * @param label {@link String} rótulo exibido antes da entrada
     * @return double valor lido, {@link Double#NEGATIVE_INFINITY} se cancelado ou {@link Double#NaN} se inválido
     */
    public static double inputDouble(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();

        if (verificarSaida(valor)) return Double.NEGATIVE_INFINITY;
        if (!Verificador.verificarDouble(valor)) return Double.NaN;

        return Double.parseDouble(valor);
    }


    /**
     * Verifica se o valor informado corresponde ao comando de saída "Sair",
     * ignorando diferenças entre maiúsculas e minúsculas.
     *
     * @param input {@link String} valor a ser verificado
     * @return {@code true} se o valor for "Sair", {@code false} caso contrário
     */
    public static boolean verificarSaida(String input) {
        return input.equalsIgnoreCase("Sair");
    }

    /**
     * Converte uma data em {@link String} para o formato DD/MM/YYYY,
     * aceitando tanto o padrão ISO (yyyy-MM-dd) quanto o padrão brasileiro (DD/MM/YYYY).
     * Retorna uma {@link String} vazia caso a data seja inválida.
     *
     * @param data {@link String} contendo a data a ser formatada
     * @return {@link String} data formatada no padrão DD/MM/YYYY, ou vazia se inválida
     */
    public static String formataData(String data) {
        try {
            if (data.contains("-")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(data).format(formatter);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter).format(formatter);
        }
        catch (DateTimeParseException ex) {
            return "";
        }
    }

    /**
     * Aguarda o usuário pressionar qualquer tecla antes de prosseguir,
     * exibindo uma mensagem de instrução no console.
     *
     * @param scanner instância de {@link Scanner} utilizada para leitura da entrada
     */
    public static void aguardarTecla(Scanner scanner) {
        System.out.println("Pressione qualquer tecla para fechar o menu...");
        try {
            System.in.read();
            System.in.skip(System.in.available());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
