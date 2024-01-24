package machine;

enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    final public int mlOfWaterPerCup;
    final public int mlOfMilkPerCup;
    final public int gramOfBeansPerCup;
    final public int costPerCup;

    CoffeeType(int mlOfWaterPerCup, int mlOfMilkPerCup, int gramsOfBeansPerCup, int costPerCup) {
        this.mlOfWaterPerCup = mlOfWaterPerCup;
        this.mlOfMilkPerCup = mlOfMilkPerCup;
        this.gramOfBeansPerCup = gramsOfBeansPerCup;
        this.costPerCup = costPerCup;
    }
}

enum MachineState {
    CHOOSING_ACTION,
    CHOOSING_COFFEE,
    ADDING_WATER,
    ADDING_MILK,
    ADDING_BEANS,
    ADDING_CUPS,
}

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int disposableCups;
    private long balance;
    MachineState state;

    CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.disposableCups = 9;
        this.balance = 550;
        this.state = MachineState.CHOOSING_ACTION;
    }

    void processUserInput(String input) {
        switch (this.state) {
            case CHOOSING_ACTION -> processAction(input);
            case CHOOSING_COFFEE -> {
                switch (input) {
                    case "1" -> {
                        if (this.checkAvailability(CoffeeType.ESPRESSO)) {
                            this.sell(CoffeeType.ESPRESSO);
                        }
                    }
                    case "2" -> {
                        if (this.checkAvailability(CoffeeType.LATTE)) {
                            this.sell(CoffeeType.LATTE);
                        }
                    }
                    case "3" -> {
                        if (this.checkAvailability(CoffeeType.CAPPUCCINO)) {
                            this.sell(CoffeeType.CAPPUCCINO);
                        }
                    }
                }

                this.state = MachineState.CHOOSING_ACTION;
            }
            case ADDING_WATER -> {
                this.fill(Integer.parseInt(input),0, 0, 0);
                this.state = MachineState.ADDING_MILK;
            }
            case ADDING_MILK -> {
                this.fill(0, Integer.parseInt(input), 0, 0);
                this.state = MachineState.ADDING_BEANS;
            }
            case ADDING_BEANS -> {
                this.fill(0, 0, Integer.parseInt(input), 0);
                this.state = MachineState.ADDING_CUPS;
            }
            case ADDING_CUPS -> {
                this.fill(0, 0, 0, Integer.parseInt(input));
                this.state = MachineState.CHOOSING_ACTION;
            }
        }
    }

    private void processAction(String action) {
        switch (action) {
            case "buy" -> this.state = MachineState.CHOOSING_COFFEE;
            case "fill" -> this.state = MachineState.ADDING_WATER;
            case "take" -> {
                System.out.printf("I gave you $%d\n\n", this.withdraw());
                this.state = MachineState.CHOOSING_ACTION;
            }
            case "remaining" -> {
                System.out.println(this);
                this.state = MachineState.CHOOSING_ACTION;
            }
        }
    }

    private boolean checkAvailability(CoffeeType coffeeType) {
        if (coffeeType.mlOfWaterPerCup > this.water) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        }
        if (coffeeType.mlOfMilkPerCup > this.milk) {
            System.out.println("Sorry, not enough milk!\n");
            return false;
        }
        if (coffeeType.gramOfBeansPerCup > this.beans) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return false;
        }
        if (this.disposableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!\n");
            return false;
        }
        System.out.println("I have enough resources, making you a coffee!\n");
        return true;
    }

    private void sell(CoffeeType coffeeType) {
        this.water -= coffeeType.mlOfWaterPerCup;
        this.milk -= coffeeType.mlOfMilkPerCup;
        this.beans -= coffeeType.gramOfBeansPerCup;
        this.balance += coffeeType.costPerCup;
        this.disposableCups--;
    }

    private void fill(int water, int milk, int beans, int disposableCups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.disposableCups += disposableCups;
    }

    private long withdraw() {
        long balance = this.balance;
        this.balance = 0;
        return balance;
    }

    @Override
    public String toString() {
        return String.format(
                """
                            The coffee machine has:
                            %d ml of water
                            %d ml of milk
                            %d g of coffee beans
                            %d disposable cups
                            $%d of money
                            """,
                this.water,
                this.milk,
                this.beans,
                this.disposableCups,
                this.balance
        );
    }
}
