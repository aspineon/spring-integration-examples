package org.bsnyder.spring.integration.hello;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.message.GenericMessage;

public class HelloWorldApp {
    
    private static Logger LOGGER = LoggerFactory.getLogger(HelloWorldApp.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = 
                new ClassPathXmlApplicationContext("/META-INF/spring/integration/hello/hello-world-context.xml", HelloWorldApp.class);
        MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
        PollableChannel outputChannel = context.getBean("outputChannel", PollableChannel.class);
        
        GenericMessage<String> message = null;
        
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("company", "hybris software");
        headers.put("date", new Date());
                
        for(int i = 0; i < 1000; ++i) {
            headers.put("code", i);
            message = new GenericMessage<String>("World: " + i, headers);
            inputChannel.send(message);
            LOGGER.info("HelloWorldApp: " + outputChannel.receive(i).getPayload());
        }
        
        context.destroy();
        context.close();
    }
}
