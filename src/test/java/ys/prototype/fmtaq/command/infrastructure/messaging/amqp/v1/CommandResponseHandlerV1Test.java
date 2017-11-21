package ys.prototype.fmtaq.command.infrastructure.messaging.amqp.v1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.application.CommandService;
import ys.prototype.fmtaq.command.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.command.domain.CommandResponseStatus;
import ys.prototype.fmtaq.command.infrastructure.messaging.amqp.CommandResponseHandler;
import ys.prototype.fmtaq.command.infrastructure.messaging.amqp.CommandResponseListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CommandResponseHandlerV1Test {

    private final static String CHARSET = "UTF-8";
    private String commandResponseJsonString;
    private Message message;

    @MockBean
    private CommandResponseJsonConverter commandResponseJsonConverter;

    @MockBean
    private CommandService commandService;

    @MockBean
    private CommandResponseListener commandResponseListener;

    @Before
    public void setup() throws IOException {
        Path commandResponseJsonFile = Paths.get("src/test/resources/command_response.json");
        byte[] commandResponseJsonByteArray = Files.readAllBytes(commandResponseJsonFile);
        commandResponseJsonString = new String(commandResponseJsonByteArray, Charset.forName(CHARSET));
        MessageProperties messageProperties = new MessageProperties();
        message = new Message(commandResponseJsonString.getBytes(CHARSET), messageProperties);
    }

    @Test
    public void handle() throws UnsupportedEncodingException {
        UUID commandResponseId = UUID.randomUUID();
        String commandResponseBody = "test_body";

        CommandResponseDTO commandResponseDTO = new CommandResponseDTO(commandResponseId, CommandResponseStatus.OK,
                commandResponseBody);
        when(commandResponseJsonConverter.toDTO(commandResponseJsonString)).thenReturn(commandResponseDTO);
        InOrder inOrder = inOrder(commandResponseJsonConverter, commandService, commandResponseListener);

        CommandResponseHandler commandResponseHandler = new CommandResponseHandlerV1(commandResponseJsonConverter,
                commandService, commandResponseListener);
        commandResponseHandler.handle(message);

        inOrder.verify(commandResponseListener).addVersionHandler("v1", commandResponseHandler);
        inOrder.verify(commandResponseJsonConverter).toDTO(commandResponseJsonString);
        inOrder.verify(commandService).handleResponse(commandResponseDTO);
    }
}
