package br.com.safeplant.monitoramentosafras.helper;

import java.util.Scanner;

public class Interacao {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputString(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    public static int inputInteiro(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();
        if (verificarSaida(valor)) return -1;
        return Verificador.verificarInteiro(valor);
    }

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

    public static boolean verificarSaida(String input) {
        return input.equalsIgnoreCase("Sair");
    }
}
