package eu.t6nn.tutorial.spock.rest.web.def

import eu.t6nn.tutorial.spock.rest.helpers.AngularJsAware
import geb.Page

/**
 * @author tonispi
 */
class PersonMainPage extends Page implements AngularJsAware {
    static url = "/"

    static at = {
        angularReady
        title == "Wallet App"
    }

    static content = {
        balances { $("#balanceList").module(BalanceList) }
        txForm { $("form").module(TransactionForm) }
    }
}
