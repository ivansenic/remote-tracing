package remote.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/jetty")
public class JettyController {

	private HttpClient httpClient;

	@RequestMapping("")
	public String callBase(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url)
			throws ClientProtocolException, IOException, InterruptedException, TimeoutException, ExecutionException {
		// ContentResponse response = httpClient.newRequest(url).send();

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Jetty Http Client!</h1>");
		stringBuilder.append("Used Jetty Http Client to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Sync not implemented.");
		return stringBuilder.toString();
	}

	@RequestMapping("async")
	public String callBaseAsync(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url)
			throws ClientProtocolException, IOException, InterruptedException, TimeoutException, ExecutionException {
		HttpExchange exchange = new HttpExchange();
		exchange.setURL(url);
		httpClient.send(exchange);

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Jetty Http Client!</h1>");
		stringBuilder.append("Used Jetty Http Client to asynchronously call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Response unknown.");
		return stringBuilder.toString();
	}

	@PostConstruct
	protected void init() throws Exception {
		httpClient = new HttpClient();
		httpClient.start();
	}

	@PreDestroy
	protected void close() throws Exception {
		httpClient.stop();
	}

}