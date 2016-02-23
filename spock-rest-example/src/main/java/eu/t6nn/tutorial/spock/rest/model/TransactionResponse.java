package eu.t6nn.tutorial.spock.rest.model;

/**
 * Created by t6nn on 2/23/16.
 */
public class TransactionResponse {

    private final String message;

    private final MoneyOnWire money;

    public TransactionResponse(String message, MoneyOnWire money) {
        this.message = message;
        this.money = money;
    }

    public String getMessage() {
        return message;
    }

    public MoneyOnWire getMoney() {
        return money;
    }
}
