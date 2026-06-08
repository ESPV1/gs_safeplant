package br.com.safeplant.monitoramentosafras.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Verificador {
    public static boolean verificarData(String data) {
        if (data == null || data.isEmpty())
            return false;

        return !Interacao.formataData(data).isEmpty();
    }

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

    public static int verificarInteiro(String valor) {
        valor = valor.trim();
        if (!valor.matches(".*\\d.*"))
            return 0;
        return Integer.parseInt(valor);
    }

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

    public static boolean verificarCEP(String cep) {
        cep = cep.replaceAll("[^\\d]", "");
        return cep.length() == 8;
    }

    public static boolean verificarLatitude(double latitude) {
        return !(latitude < -90.0) && !(latitude > 90.0);
    }

    public static boolean verificarLongitude(double longitude) {
        return !(longitude < -180.0) && !(longitude > 180.0);
    }
}
