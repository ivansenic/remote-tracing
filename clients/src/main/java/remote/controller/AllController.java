package remote.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * @author Ivan Senic
 *
 */
@RestController
@RequestMapping("/all")
public class AllController {

	@Autowired
	ApacheController apacheController;

	@Autowired
	JettyController jettyController;

	@Autowired
	SpringRestTemplate springController;

	@Autowired
	UrlConnectionController urlConnectionController;

	@RequestMapping("")
	public String callBaseWithAllClients(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url)
			throws ClientProtocolException, IOException, RestClientException, URISyntaxException, InterruptedException, TimeoutException, ExecutionException {
		apacheController.callBase(url);
		jettyController.callBase(url);
		springController.callBase(url);
		urlConnectionController.callBase(url);

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from All Http Clients!</h1>");
		stringBuilder.append("Used Apache, Jetty, Spring Rest Template and URL Connection to call " + url);
		return stringBuilder.toString();
	}
}
