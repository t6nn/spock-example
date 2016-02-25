package eu.t6nn.tutorial.spock.rest.web.def

import geb.Module

/**
 * @author tonispi
 */
class CurrencyInput extends Module {
    static content = {
        enter { text -> $("input", type: "text").value(text) }
        give { currency -> $("button#giveBtn li a", text: contains(currency)).click() }
        ask { currency -> $("button#askBtn li a", text: contains(currency)).click() }
    }
}
