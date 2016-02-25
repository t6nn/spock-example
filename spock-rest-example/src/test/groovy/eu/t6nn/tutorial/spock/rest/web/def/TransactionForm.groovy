package eu.t6nn.tutorial.spock.rest.web.def

import geb.Module

/**
 * @author tonispi
 */
class TransactionForm extends Module{
    static content = {
        amount {$("#txInput").module(CurrencyInput)}
    }
}
