package eu.t6nn.tutorial.spock.rest.web.def

import geb.Page

/**
 * @author tonispi
 */
class PersonMainPage extends Page {
    static url = "/"

    static at = { title == "Wallet App" }

    static content = {
        balances { $("#balanceList").module(BalanceList) }
        txForm { $("form").module(TransactionForm) }
    }
}
