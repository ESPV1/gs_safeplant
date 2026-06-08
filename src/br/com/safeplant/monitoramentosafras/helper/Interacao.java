package br.com.safeplant.monitoramentosafras.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static String inputData(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();
        if (valor.equalsIgnoreCase("hoje")) return formataData(LocalDate.now().toString());
        if (!Verificador.verificarData(valor)) return null;
        return formataData(valor);
    }

    public static double inputDouble(String label) {
        System.out.print(label);
        String valor = scanner.nextLine().trim();

        if (verificarSaida(valor)) return Double.NEGATIVE_INFINITY;
        if (!Verificador.verificarDouble(valor)) return Double.NaN;

        return Double.parseDouble(valor);
    }


    public static boolean verificarSaida(String input) {
        return input.equalsIgnoreCase("Sair");
    }

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
