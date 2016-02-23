package eu.t6nn.tutorial.spock.rest.controller;

import eu.t6nn.tutorial.spock.rest.model.PersonBalance;
import eu.t6nn.tutorial.spock.system.Person;
import eu.t6nn.tutorial.spock.system.wallet.NormalWallet;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tonispi
 */
@Controller
@RequestMapping( "/person" )
public class PersonController {

	private final Person person = new Person( new NormalWallet() );

	@RequestMapping( method = RequestMethod.GET, path = "/balances", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    @ResponseBody
	public List<PersonBalance> getBalancesAsJson() {
		return person.reportBalances().stream().map( PersonBalance::new ).collect( Collectors.toList() );
	}

	@RequestMapping( method = RequestMethod.GET, path = "/balances", produces = MediaType.TEXT_HTML_VALUE )
    @ResponseBody
	public String getBalancesAsText() {
		StringBuilder builder = new StringBuilder("My Balances:\n");
		person.reportBalances().stream().forEach( ( m ) -> builder.append( m ).append( "\n" ) );
		return builder.toString();
	}

}
