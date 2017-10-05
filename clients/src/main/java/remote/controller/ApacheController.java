package remote.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apache")
public class ApacheController {

	private CloseableHttpAsyncClient httpAsyncClient;

	@RequestMapping("")
	public String callBase(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity());

		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Apache Http Client!</h1>");
		stringBuilder.append("Used Apache Http Client to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Received response: ");
		stringBuilder.append(result);
		return stringBuilder.toString();
	}

	@RequestMapping("/async")
	public String callBaseAsync(@RequestParam(value = "url", defaultValue = "http://localhost:8001") String url) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {

			@Override
			public void failed(Exception ex) {
				CompletableFuture.runAsync(() -> System.out.println("ApacheFutureCallback -> failed"));
			}

			@Override
			public void completed(HttpResponse result) {
				CompletableFuture.runAsync(() -> System.out.println("ApacheFutureCallback -> completed"));
			}

			@Override
			public void cancelled() {
				CompletableFuture.runAsync(() -> System.out.println("ApacheFutureCallback -> canceled"));
			}
		});


		StringBuilder stringBuilder = new StringBuilder("<h1>Greetings from Apache Http Async Client!</h1>");
		stringBuilder.append("Used Apache Http Client to call " + url);
		stringBuilder.append("<br>");
		stringBuilder.append("Response unknown");
		return stringBuilder.toString();
	}

	@PostConstruct
	public void buildAsyncClient() {
		httpAsyncClient = HttpAsyncClientBuilder.create().build();
		httpAsyncClient.start();
	}

	@PreDestroy
	public void closeAsyncClient() throws IOException {
		if (null != httpAsyncClient) {
			httpAsyncClient.close();
		}
	}

}