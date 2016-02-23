package eu.t6nn.tutorial.spock.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpockRestExampleApplication.class)
@WebAppConfiguration
public class SpockRestExampleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
