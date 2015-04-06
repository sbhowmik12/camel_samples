
package org.apache.camel.example.spring;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.MultipleConsumersSupport;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.spring.Main;

import com.thoughtworks.xstream.XStream;

/**
 * A simple xml based message router from a file system to  another
 * file system(xml transformation)
 *
 * @version
 */
public class MyRouteBuilder4 extends RouteBuilder implements
		MultipleConsumersSupport {

	/**
	 * Allow this route to be run as an application
	 */
	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	public void configure() {
		
		/*1. from("file:src/dataroute4/input?noop=true").filter().xpath("//animal").
		to("file:src/dataroute4/output?noop=true");
		 
		
		 2.  from("file:src/dataroute4/input?noop=true").
		  choice().when().xpath("//type"="animal")
		  .to("file:src/dataroute4/output?noop=true").
		  otherwise().to("file:src/dataroute4/output1?noop=true");*/
		 	
		XStream xStream = new XStream();
		xStream.alias("animal", XmlMapper.class);
		from("file:src/dataroute4/input?noop=true")
				.unmarshal(new XStreamDataFormat(xStream))
				.process(new RequestProcessor())
				.marshal(new XStreamDataFormat(xStream))
				.to("file:src/dataroute4/output?noop=true");

	}

	public class RequestProcessor implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {

			XmlMapper x = (XmlMapper) exchange.getIn().getBody();
			System.out.println(x.getAnimalType() + "lives in the desert");
			x.setAnimalType("Camel lives in the desert");
			exchange.getIn().setBody(x, XmlMapper.class);
		}
	}

	@Override
	public boolean isMultipleConsumersSupported() {
		return true;
	}

}