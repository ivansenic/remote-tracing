package remote.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/springresttemplate")
public class SpringRestTemplate {

	private RestTemplate restTemplate = new RestTemplate();

	private RestTemplate restTemplateWithApache = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

	@RequestMapping("")
	public String callBase(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws RestClientException, URISyntaxException {
		String result = restTemplate.getForObject(new URI(url), String.class);

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Spring Rest Template!</h1>");
		stringBuilder.append("Used Spring Rest Template to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Received response: ");
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

	@RequestMapping("/apache")
	public String callBaseWithApache(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws RestClientException, URISyntaxException {
		String result = restTemplateWithApache.getForObject(new URI(url), String.class);

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Spring Rest Template!</h1>");
		stringBuilder.append("Used Spring Rest Template to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Received response: ");
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

	@RequestMapping("/async")
	public String callBaseAsync(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws RestClientException, URISyntaxException {
		asyncRestTemplate.getForEntity(new URI(url), String.class);

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Spring Rest Template!</h1>");
		stringBuilder.append("Used Async Spring Rest Template to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Response unknown.");
		return stringBuilder.toString();
	}

}