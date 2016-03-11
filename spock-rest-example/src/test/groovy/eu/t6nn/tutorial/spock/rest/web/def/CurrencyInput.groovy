package eu.t6nn.tutorial.spock.rest.web.def

import geb.Module

/**
 * @author tonispi
 */
class CurrencyInput extends Module {
    static content = {
        enter { text -> $("input", type: "text").value(text) }
        give { currency ->
            $("div#giveBtn button").click()
            $("div#giveBtn li a", text: contains(currency)).click()
        }
        ask { currency ->
            $("div#askBtn button").click()
            $("div#askBtn li a", text: contains(currency)).click()
        }
    }
}
