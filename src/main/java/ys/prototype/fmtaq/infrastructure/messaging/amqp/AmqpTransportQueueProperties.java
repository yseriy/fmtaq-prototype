package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Configuration
@ConfigurationProperties("app.transport.amqp.queue")
public class AmqpTransportQueueProperties {

    @Setter
    @Getter
    public static class Response {
        @NotNull
        @Size(min = 1, max = 255)
        private String name;
        private Boolean durable = true;
        private Boolean exclusive = false;
        private Boolean autoDelete = false;
    }

    @Setter
    @Getter
    public static class Host {
        private Boolean durable = true;
        private Boolean exclusive = false;
        private Boolean autoDelete = false;
    }

    private Response response;
    private Host host;
}
