package remote.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url-connection")
public class UrlConnectionController {

	@RequestMapping("")
	public String callBase(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws ClientProtocolException, IOException {
		URLConnection connection = new URL(url).openConnection();

		// connection.connect();

		// try to write smth to the server
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write("Hi there");
		writer.close();

		StringBuilder result = new StringBuilder();
		try (InputStream is = connection.getInputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String read;
			while ((read = br.readLine()) != null) {
				result.append(read);
			}
		}

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Java URL Connection!</h1>");
		stringBuilder.append("Used URL Connection to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Received response: ");
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

	@RequestMapping("connect")
	public String callBaseConnectFirst(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws ClientProtocolException, IOException {
		URLConnection connection = new URL(url).openConnection();

		connection.setDoOutput(true);
		connection.connect();

		// try to write smth to the server
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write("Hi there");
		writer.close();

		StringBuilder result = new StringBuilder();
		try (InputStream is = connection.getInputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String read;
			while ((read = br.readLine()) != null) {
				result.append(read);
			}
		}

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Java URL Connection!</h1>");
		stringBuilder.append("Used URL Connection to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Received response: ");
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

}