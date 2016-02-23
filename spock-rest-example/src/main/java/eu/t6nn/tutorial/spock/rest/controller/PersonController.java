package eu.t6nn.tutorial.spock.rest.controller;

import eu.t6nn.tutorial.spock.rest.model.MoneyOnWire;
import eu.t6nn.tutorial.spock.rest.model.TransactionResponse;
import eu.t6nn.tutorial.spock.system.Person;
import eu.t6nn.tutorial.spock.system.wallet.Money;
import eu.t6nn.tutorial.spock.system.wallet.NormalWallet;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tonispi
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    private final Person person = new Person(new NormalWallet());

    @RequestMapping(method = RequestMethod.GET, path = "/balances", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<MoneyOnWire> getBalancesAsJson() {
        return person.reportBalances().stream().map(MoneyOnWire::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/give", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionResponse giveMoney(@RequestBody MoneyOnWire request) {
        try {
            Money money = request.parseMoney();
            person.give(money);
            return new TransactionResponse("Thanks for the money!", request);
        } catch (RuntimeException e) {
            throw new MoneyInputException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/ask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionResponse askMoney(@RequestBody MoneyOnWire request) {
        try {
            Money money = request.parseMoney();
            Money actualAmount = person.ask(money);
            return new TransactionResponse("Will give you this much:", new MoneyOnWire(actualAmount));
        } catch (RuntimeException e) {
            throw new MoneyInputException(e.getMessage());
        }
    }

}
