package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

@Component
public class MessageToDTOConverter {

    private final ObjectMapper objectMapper;

    public MessageToDTOConverter(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        objectMapper = jackson2ObjectMapperBuilder.build();
    }

    CommandResponseDTO toDTO(Message message) throws IOException {
        String data = new String(message.getBody(), Charset.forName("UTF-8"));
        JsonNode root = objectMapper.readTree(data);

        return new CommandResponseDTO(getCommandId(root), getCommandResponseStatus(root), data);
    }

    private UUID getCommandId(JsonNode root) {
        JsonNode commandId = root.get("task_id");

        if (commandId.isMissingNode()) {
            throw new RuntimeException("cannot find field: 'task_id'");
        }

        return UUID.fromString(commandId.asText());
    }

    private CommandResponseStatus getCommandResponseStatus(JsonNode root) {
        JsonNode commandResponseCode = root.get("result").get("rc");

        if (commandResponseCode.isMissingNode()) {
            throw new RuntimeException("cannot find field: 'result.rc'");
        }

        return commandResponseCode.asInt() == 0 ? CommandResponseStatus.OK : CommandResponseStatus.ERROR;
    }
}
