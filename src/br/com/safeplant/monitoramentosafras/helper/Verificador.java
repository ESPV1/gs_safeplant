package br.com.safeplant.monitoramentosafras.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Verificador {
    public static boolean verificarDataNascimento(String dataNascimento) {
        try {
            if (dataNascimento.split("/").length != 3)
                return false;
            String validDate = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

            return !validDate.isEmpty();
        }
        catch (DateTimeParseException ex) {
            return false;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean verificarCPF(String cpf) {
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

    public static boolean verificarCEP(String cep) {
        cep = cep.replaceAll("[^\\d]", "");
        return cep.length() == 8;
    }
}
