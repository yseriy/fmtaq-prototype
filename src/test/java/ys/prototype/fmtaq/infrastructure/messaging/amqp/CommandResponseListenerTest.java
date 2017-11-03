package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.FmtaqException;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CommandResponseListenerTest {

    private final AmqpTransportLogger logger = new AmqpTransportLogger();

    @Test
    public void addDefaultVersionHandler() throws UnsupportedEncodingException {
        String version = "v1";
        CommandResponseHandler commandResponseHandler = mock(CommandResponseHandler.class);
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("test_body".getBytes("UTF-8"), messageProperties);

        CommandResponseListener commandResponseListener = new CommandResponseListener(logger);
        assertThat(commandResponseListener).isNotNull();

        commandResponseListener.addVersionHandler(version, commandResponseHandler);
        commandResponseListener.processResponse(message);

        verify(commandResponseHandler).handle(message);
    }

    @Test
    public void addVersionHandler() throws UnsupportedEncodingException {
        String versionHeaderName = "version";
        String version = "v12";
        CommandResponseHandler commandResponseHandler = mock(CommandResponseHandler.class);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(versionHeaderName, version);
        Message message = new Message("test_body".getBytes("UTF-8"), messageProperties);

        CommandResponseListener commandResponseListener = new CommandResponseListener(logger);
        assertThat(commandResponseListener).isNotNull();

        commandResponseListener.addVersionHandler(version, commandResponseHandler);
        commandResponseListener.processResponse(message);

        verify(commandResponseHandler).handle(message);
    }
}
