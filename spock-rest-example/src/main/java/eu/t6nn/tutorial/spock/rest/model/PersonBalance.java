package eu.t6nn.tutorial.spock.rest.model;

import eu.t6nn.tutorial.spock.system.wallet.Money;

/**
 * @author tonispi
 */
public class PersonBalance {

    private String currency;
    private String amount;

    public PersonBalance(Money money) {
        this.currency = money.getCurrency().getCurrencyCode();
        this.amount = money.getAmount().toPlainString();
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }
}
