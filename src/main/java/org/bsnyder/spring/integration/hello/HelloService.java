package org.bsnyder.spring.integration.hello;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

public class HelloService {
    
    private static Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    /**
     * Tell Spring Integration to pass along the String payload only.  
     * @param name
     * @return
     */
    public String sayHelloString(String name) {
        return "Hello " + name;
    }
    
    /**
     * Tell Spring Integration to pass along the {@link Message} type. 
     * @param message
     * @return
     */
    public Message<?> sayHelloMessage(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        Set<Entry<String, Object>> entrySet = headers.entrySet();
        Iterator<Entry<String, Object>> iter = null;
        
        for (iter = entrySet.iterator(); iter.hasNext();) {
            Entry<String, Object> entry = iter.next();
            LOGGER.debug("HEADER[key: '{}' value: '{}']", entry.getKey(), entry.getValue());
        }
        return message;
    }

}
