package com.example.application.services;

/**
 * Classe di utilità per la manipolazione di stringhe.
 * <p>
 * Fornisce metodi per la formattazione del testo, come la capitalizzazione delle parole.
 * </p>
 * 
 * @author [Il tuo nome]
 * @version 1.0
 */
public class StringUtils {

    /**
     * Capitalizza la prima lettera di una stringa, rendendo il resto in minuscolo.
     * 
     * @param input la stringa da modificare
     * @return la stringa con la prima lettera maiuscola, oppure {@code null} se l'input è nullo
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Capitalizza ogni parola in una stringa, mantenendo la corretta spaziatura.
     * 
     * @param input la stringa da formattare
     * @return la stringa con ogni parola capitalizzata, oppure {@code null} se l'input è nullo
     */
    public static String capitalizeAddress(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
