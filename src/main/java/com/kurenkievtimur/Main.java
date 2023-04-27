package com.kurenkievtimur;

import java.util.Random;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        int passwordLength = checkPasswordLength(scanner);

        System.out.println("Input the number of possible symbols in the code:");
        int symbols = checkPossibleSymbols(scanner, passwordLength);

        String password = generatePassword(passwordLength, symbols);
        printInfoPreparedPassword(passwordLength, symbols);

        System.out.println("Okay, let's start a game!");

        int bulls = 0;
        int cows = 0;

        int counter = 0;
        while (bulls != passwordLength) {
            System.out.printf("Turn %d:%n", ++counter);
            String input = scanner.next();

            bulls = countBulls(input, password);
            cows = countCows(input, password);

            printInfo(bulls, cows);
        }
    }

    public static String generatePassword(int n, int symbols) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        while (password.length() != n) {
            char number = Character.forDigit(random.nextInt(symbols), Character.MAX_RADIX);

            if (password.indexOf(valueOf(number)) == -1) {
                password.append(number);
            }
        }

        return password.toString();
    }

    public static int checkPasswordLength(Scanner scanner) {
        String input = scanner.next();

        int passwordLength = 0;

        try {
            passwordLength = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", input);
            System.exit(0);
        }

        if (passwordLength > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique possible symbols.%n", passwordLength);
            System.exit(0);
        } else if (passwordLength <= 0) {
            System.out.printf("Error: \"%d\" isn't a valid number.%n", passwordLength);
            System.exit(0);
        }

        return passwordLength;
    }

    public static int checkPossibleSymbols(Scanner scanner, int passwordLength) {
        String input = scanner.next();

        int symbols = 0;

        try {
            symbols = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", input);
            System.exit(0);
        }

        if (symbols < passwordLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", passwordLength, symbols);
            System.exit(0);
        } else if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        } else if (symbols <= 0) {
            System.out.printf("Error: \"%d\" isn't a valid number.%n", symbols);
            System.exit(0);
        }

        return symbols;
    }

    public static void printInfoPreparedPassword(int passwordLength, int symbols) {
        if (symbols <= 10) {
            System.out.printf("The secret is prepared: %s (0-%d).%n", "*".repeat(Math.max(0, passwordLength)), symbols - 1);
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).%n", "*".repeat(Math.max(0, passwordLength)),
                    Character.forDigit(symbols - 1, Character.MAX_RADIX));
        }
    }

    public static int countBulls(String input, String password) {
        int bulls = 0;
        for (int i = 0; i < password.length(); i++) {
            try {
                if (input.charAt(i) == password.charAt(i)) {
                    bulls++;
                }
            } catch (StringIndexOutOfBoundsException e) {
                bulls = 0;
                break;
            }
        }

        return bulls;
    }

    public static int countCows(String input, String password) {
        int cows = 0;
        for (int i = 0; i < password.length(); i++) {
            try {
                if (password.indexOf(input.charAt(i)) != -1 && input.charAt(i) != password.charAt(i)) {
                    cows++;
                }
            } catch (StringIndexOutOfBoundsException e) {
                cows = 0;
                break;
            }
        }

        return cows;
    }

    public static void printInfo(int bulls, int cows) {
        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: none");
        } else if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d %s and %d %s%n", bulls, (bulls == 1 ? "bull" : "bulls"), cows, (cows == 1 ? "cow" : "cows"));
        } else if (bulls > 0) {
            System.out.printf("Grade: %d %s%n", bulls, (bulls == 1 ? "bull" : "bulls"));
        } else if (cows > 0) {
            System.out.printf("Grade: %d %s%n", cows, (cows == 1 ? "cow" : "cows"));
        } else {
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }
}