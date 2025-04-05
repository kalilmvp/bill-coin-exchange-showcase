package com.kmvp.billcoinexhange.app;

import com.kmvp.billcoinexhange.app.services.ExchangeConsole;
import com.kmvp.billcoinexhange.app.services.ExchangePrintable;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author kalil.peixoto
 * @date 4/2/25 17:54
 * @email kalilmvp@gmail.com
 */
public class ExchangeBillCoinScanner {
    private static final Logger LOGGER = Logger.getLogger(ExchangeBillCoinScanner.class.getName());

    public static void main(String[] args) {
        final ExchangePrintable machine = new ExchangeConsole();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter amount in dollars (or type 'q' to quit): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
            try {
                int inputUser = Integer.parseInt(input);
                System.out.println("Do you want the least (l), most(m) or both(b) amount (or type 'q' to quit): ");
                String inputType = scanner.nextLine();

                switch (inputType) {
                    case "l":
                        exchangeLeastCoins(machine, inputUser);
                        break;
                    case "m":
                        exchangeMostCoins(machine, inputUser);
                        break;
                    case "b":
                        exchangeLeastCoins(machine, inputUser);
                        exchangeMostCoins(machine, inputUser);
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                LOGGER.severe("Please user, enter a valid amount.");
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void exchangeLeastCoins(ExchangePrintable machine, int inputUser) {
        Map<Integer, Integer> exchangedLeastCoins = machine.execute(inputUser, true);
        System.out.println("LEAST COINS ---------------");
        System.out.printf("Exchanged $%s successfully! %n%n", inputUser);
        printValues(exchangedLeastCoins);
        machine.printState(inputUser);
    }
    private static void exchangeMostCoins(ExchangePrintable machine, int inputUser) {
        Map<Integer, Integer> exchangedMostCoins = machine.execute(inputUser, false);
        System.out.println("MOST COINS ---------------");
        System.out.printf("Exchanged %s successfully! %n%n", inputUser);
        printValues(exchangedMostCoins);
        machine.printState(inputUser);
    }
    private static void printValues(Map<Integer, Integer> exchangedCoins) {
        System.out.println("Statement");
        for (Map.Entry<Integer, Integer> entry : exchangedCoins.entrySet()) {
            System.out.printf("- %s coins (%s cents) = $%d%n"
                    ,entry.getValue()
                    ,entry.getKey()
                    ,Math.divideExact(Math.multiplyExact(entry.getValue(), entry.getKey()), 100)
            );
        }
    }
}
