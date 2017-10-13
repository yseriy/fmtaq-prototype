package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.task.Command;

import java.nio.charset.Charset;

@Component
@Aspect
public class AmqpTransportLogger {

    private final static int FIRST_ELEMENT = 0;
    private final static Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Around("execution(public void tryProcessResponse(org.springframework.amqp.core.Message))")
    public Object logProcessResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Message message = (Message) joinPoint.getArgs()[FIRST_ELEMENT];
        logger.info("start processing command response message: {}\ndecoded message body: {}", message.toString(),
                new String(message.getBody(), CHARSET_UTF_8));
        Object returnObject = joinPoint.proceed();
        logger.info("processing command response message - ok");

        return returnObject;
    }

    @Around("execution(public void ys.prototype.fmtaq.infrastructure.messaging.amqp"
            + ".AmqpTransportCommandSender.send(ys.prototype.fmtaq.domain.task.Command))")
    public Object logSendCommand(ProceedingJoinPoint joinPoint) throws Throwable {
        Command command = (Command) joinPoint.getArgs()[FIRST_ELEMENT];
        logger.debug("start sending command. command address: '{}', command body: '{}'",
                command.getAddress(), command.getBody());
        Object returnObject = joinPoint.proceed();
        logger.debug("sending command - ok");

        return returnObject;
    }
}
