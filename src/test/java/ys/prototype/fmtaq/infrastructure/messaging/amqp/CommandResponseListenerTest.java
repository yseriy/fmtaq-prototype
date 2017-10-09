package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.CommandService;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.CommandResponseStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CommandResponseListenerTest {

    private final static String CHARSET = "UTF-8";
    private String commandResponseJsonString;
    private Message message;
    private CommandResponseListener commandResponseListener;

    @MockBean
    private CommandResponseJsonConverter commandResponseJsonConverter;

    @MockBean
    private CommandService commandService;

    @Before
    public void setup() throws IOException {
        Path commandResponseJsonFile = Paths.get("src/test/resources/command_response.json");
        byte[] commandResponseJsonByteArray = Files.readAllBytes(commandResponseJsonFile);
        commandResponseJsonString = new String(commandResponseJsonByteArray, Charset.forName(CHARSET));
        MessageProperties messageProperties = new MessageProperties();
        message = new Message(commandResponseJsonString.getBytes(CHARSET), messageProperties);
        commandResponseListener = new CommandResponseListener(commandResponseJsonConverter, commandService);
    }

    @Test
    public void processResponse() throws Exception {
        UUID commandResponseId = UUID.randomUUID();
        String commandResponseBody = "test_body";
        CommandResponseDTO commandResponseDTO = new CommandResponseDTO(commandResponseId, CommandResponseStatus.OK,
                commandResponseBody);
        when(commandResponseJsonConverter.toDTO(commandResponseJsonString)).thenReturn(commandResponseDTO);

        commandResponseListener.processResponse(message);

        verify(commandResponseJsonConverter).toDTO(commandResponseJsonString);
        verify(commandService).handleResponse(commandResponseDTO);
    }

    @Test
    public void processResponseException() throws IOException {
        Throwable testException = new RuntimeException("test_exception");
        when(commandResponseJsonConverter.toDTO(commandResponseJsonString)).thenThrow(testException);

        commandResponseListener.processResponse(message);
    }
}
