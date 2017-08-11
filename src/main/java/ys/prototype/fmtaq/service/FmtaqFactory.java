package ys.prototype.fmtaq.service;

import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class FmtaqFactory {

    Queue getQueue(String name) {
        return new Queue(name);
    }
}
