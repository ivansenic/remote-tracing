package remote;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Senic
 *
 */
@Component
public class SimpleJmsListener {

	ExecutorService executor = Executors.newFixedThreadPool(1);

	@JmsListener(destination = "myDestination")
	public void processMessage(String content) {
		System.out.println("Received message: " + content);

		CompletableFuture.runAsync(() -> System.out.println("Hello from completable future"));

		executor.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("Hello from executor");
			}
		});
	}
}
