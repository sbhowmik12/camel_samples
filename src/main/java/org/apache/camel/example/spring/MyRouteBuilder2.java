
package org.apache.camel.example.spring;

import org.apache.camel.Exchange;
import org.apache.camel.MultipleConsumersSupport;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 * A simple example router from a file system to other file systems using multicast EIP pattern
 *
 * @version
 */
public class MyRouteBuilder2 extends RouteBuilder implements
		MultipleConsumersSupport {

	/**
	 * Allow this route to be run as an application(using multicast or broadcast pattern)
	 */
	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	public void configure() {
		// populate the message queue with some messages
		from("file:src/dataroute2/input?noop=true").process(new RequestProcessor()).multicast().to("file:src/dataroute2/output?noop=true")
				.to("file:src/dataroute2/output2?noop=true");

	}

	public class RequestProcessor implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {

			String x = exchange.getIn().getBody(String.class);
			Integer output = (Integer.valueOf(x) * 2);
			exchange.getIn().setBody(output.toString());
			exchange.setOut(exchange.getIn());

		}

	}

	@Override
	public boolean isMultipleConsumersSupported() {
		// TODO Auto-generated method stub
		return true;
	}

}