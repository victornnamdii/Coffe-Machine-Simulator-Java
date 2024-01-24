package machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CoffeeMachine coffeeMachine = new CoffeeMachine();
            String input = null;

            while (!"exit".equals(input)) {
                switch (coffeeMachine.state) {
                    case CHOOSING_ACTION -> System.out.println("Write action (buy, fill, take):");
                    case CHOOSING_COFFEE -> System.out.println(
                            "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"
                    );
                    case ADDING_WATER -> System.out.println("Write how many ml of water you want to add:");
                    case ADDING_MILK -> System.out.println("Write how many ml of milk you want to add:");
                    case ADDING_BEANS -> System.out.println("Write how many gram of coffee beans you want to add:");
                    case ADDING_CUPS -> System.out.println("Write how many disposable you want to add:");
                }
                input = scanner.nextLine();
                coffeeMachine.processUserInput(input);
            }
        }
    }
}