package remote.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.tag.Tags;
import rocks.inspectit.agent.java.sdk.opentracing.ExtendedTracer;
import rocks.inspectit.agent.java.sdk.opentracing.TracerProvider;

@RestController
public class HelloController {

	@Autowired
	JmsTemplate jmsTemplate;

	@RequestMapping("/")
	public String index() {
		ExtendedTracer tracer = TracerProvider.get();
		Span span = tracer.buildSpan("my operation").withTag("myTag", "myValue").start();
		span.setBaggageItem("baggage-key", "baggage-value");
		try {
			jmsTemplate.convertAndSend("myDestination", "Index page called!");

			if (new Random().nextBoolean()) {
				Tags.ERROR.set(span, Boolean.TRUE);
				throw new NullPointerException("hahahaha");
			}
		} finally {
			span.finish();
		}

		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/plain")
	public String plain() {
		jmsTemplate.convertAndSend("myDestination", "Index page called!");

		return "Greetings from Spring Boot!";
	}

}