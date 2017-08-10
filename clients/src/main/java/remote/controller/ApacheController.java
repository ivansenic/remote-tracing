package remote.controller;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.ContentDecoder;
import org.apache.http.nio.ContentEncoder;
import org.apache.http.nio.NHttpClientConnection;
import org.apache.http.nio.NHttpClientEventHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apache")
public class ApacheController {

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
		CloseableHttpAsyncClient client = HttpAsyncClientBuilder.create().setEventHandler(new NHttpClientEventHandler() {

			@Override
			public void timeout(NHttpClientConnection conn) throws IOException, HttpException {
				// TODO Auto-generated method stub

			}

			@Override
			public void responseReceived(NHttpClientConnection conn) throws IOException, HttpException {
			}

			@Override
			public void requestReady(NHttpClientConnection conn) throws IOException, HttpException {
				// TODO Auto-generated method stub

			}

			@Override
			public void outputReady(NHttpClientConnection conn, ContentEncoder encoder) throws IOException, HttpException {
				// TODO Auto-generated method stub

			}

			@Override
			public void inputReady(NHttpClientConnection conn, ContentDecoder decoder) throws IOException, HttpException {
				// TODO Auto-generated method stub

			}

			@Override
			public void exception(NHttpClientConnection conn, Exception ex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endOfInput(NHttpClientConnection conn) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void connected(NHttpClientConnection conn, Object attachment) throws IOException, HttpException {
				// TODO Auto-generated method stub

			}

			@Override
			public void closed(NHttpClientConnection conn) {
				// TODO Auto-generated method stub

			}
		}).build();
		HttpGet httpGet = new HttpGet(url);
		client.execute(httpGet, new FutureCallback<HttpResponse>() {

			@Override
			public void failed(Exception ex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void completed(HttpResponse result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub

			}
		});
		return "";
	}

}