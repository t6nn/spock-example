package eu.t6nn.tutorial.spock.rest.controller;

import eu.t6nn.tutorial.spock.rest.model.MoneyOnWire;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by t6nn on 2/23/16.
 */
@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason = "Money supplied in invalid format")
public class MoneyInputException extends RuntimeException {

    public MoneyInputException(String message) {
        super(message);
    }
}
