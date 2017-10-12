package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.exception.FmtaqError;
import ys.prototype.fmtaq.exception.FmtaqErrorList;
import ys.prototype.fmtaq.exception.FmtaqException;

@Component
public class CommandAmqpSender implements CommandSender {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final AmqpTransportQueueProperties amqpTransportQueueProperties;
    private final AmqpTransportLogger logger;

    public CommandAmqpSender(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate,
                             AmqpTransportQueueProperties amqpTransportQueueProperties, AmqpTransportLogger logger) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.amqpTransportQueueProperties = amqpTransportQueueProperties;
        this.logger = logger;
    }

    public void send(Command command) {
        assert command != null : "command cannot be null";
        assert command.getAddress() != null : "command address cannot be null";
        assert command.getBody() != null : "command body cannot be null";

        logger.logSendCommand(command);
        Queue queue = createQueue(command.getAddress());
        tryDeclareQueue(queue);
        tryConvertAndSend(command.getAddress(), command.getBody());
        logger.logSendCommandOk();
    }

    private Queue createQueue(String name) {
        Boolean durable = amqpTransportQueueProperties.getHost().getDurable();
        Boolean exclusive = amqpTransportQueueProperties.getHost().getExclusive();
        Boolean autoDelete = amqpTransportQueueProperties.getHost().getAutoDelete();

        return new Queue(name, durable, exclusive, autoDelete);
    }

    private void tryDeclareQueue(Queue queue) {
        try {
            amqpAdmin.declareQueue(queue);
        } catch (AmqpException e) {
            throw exception(FmtaqErrorList.CANNOT_DECLARE_QUEUE, e).set("queue", queue.toString());
        }
    }

    private void tryConvertAndSend(String address, String body) {
        try {
            amqpTemplate.convertAndSend(address, body);
        } catch (AmqpException e) {
            throw exception(FmtaqErrorList.CANNOT_SEND_COMMAND, e).set("address", address).set("body", body);
        }
    }

    private FmtaqException exception(FmtaqError fmtaqError, Throwable cause) {
        return new FmtaqException(fmtaqError, cause);
    }
}
