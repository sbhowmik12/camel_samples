
package org.apache.camel.example.spring;


import org.apache.camel.MultipleConsumersSupport;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 * A choice based message router from a file system to another
 * file system
 *
 * @version
 */
public class MyRouteBuilder3 extends RouteBuilder implements  MultipleConsumersSupport{

	/**
	 * Allow this route to be run as an application (using condition based routing or switch pattern)
	 */
	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	public void configure() {
		// populate the message queue with some messages
		from("file:src/dataroute3/input?noop=true").convertBodyTo(String.class).choice().
		when(body().contains("NonCamel")).to("file:src/dataroute3/output?noop=true").otherwise().to("file:src/dataroute3/outputdata1?noop=true").endChoice();
	}
	
	

	@Override
	public boolean isMultipleConsumersSupported() {
		return true;
	}


}