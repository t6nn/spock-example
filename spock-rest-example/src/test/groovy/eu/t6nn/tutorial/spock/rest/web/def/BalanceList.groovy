package eu.t6nn.tutorial.spock.rest.web.def

import geb.Module

/**
 * @author tonispi
 */
class BalanceList extends Module {
    static content = {
        balanceItems(required: false) { $("li") }
    }
}
