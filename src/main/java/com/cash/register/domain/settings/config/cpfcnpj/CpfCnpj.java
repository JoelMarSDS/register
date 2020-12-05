package com.cash.register.domain.settings.config.cpfcnpj;

public class CpfCnpj {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValid(String cpfCnpj) {
        return (isValidCPF(cpfCnpj) || isValidCNPJ(cpfCnpj));
    }

    private static int calculateDigit(String str, int[] peso) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * peso[peso.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    private static String padLeft(String text, char character) {
        return String.format("%11s", text).replace(' ', character);
    }

    private static boolean isValidCPF(String cpf) {
        cpf = cpf.trim().replace(".", "").replace("-", "").replace(" ", "");

        if ((cpf == null) || (cpf.length() != 11))
            return false;

        for (int j = 0; j < 10; j++)
            if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(cpf))
                return false;

        Integer digitOne = calculateDigit(cpf.substring(0, 9), pesoCPF);
        Integer digitTwo = calculateDigit(cpf.substring(0, 9) + digitOne, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digitOne.toString() + digitTwo.toString());
    }

    private static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.trim().replace(".", "").replace("-", "").replace("/", "");
        if ((cnpj == null) || (cnpj.length() != 14)) return false;

        Integer digitOne = calculateDigit(cnpj.substring(0, 12), pesoCNPJ);
        Integer digitTwo = calculateDigit(cnpj.substring(0, 12) + digitOne, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digitOne.toString() + digitTwo.toString());
    }
}
