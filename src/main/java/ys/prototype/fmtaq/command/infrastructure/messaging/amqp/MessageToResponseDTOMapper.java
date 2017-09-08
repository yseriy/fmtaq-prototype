package ys.prototype.fmtaq.command.infrastructure.messaging.amqp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.CommandReturnStatus;
import ys.prototype.fmtaq.command.dto.DTOFactory;
import ys.prototype.fmtaq.command.dto.ResponseDTO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

@Component
public class MessageToResponseDTOMapper {

    private static final String CHARSET = "UTF-8";
    private final ObjectMapper objectMapper;
    private final DTOFactory DTOFactory;

    public MessageToResponseDTOMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder, DTOFactory DTOFactory) {
        this.objectMapper = jackson2ObjectMapperBuilder.build();
        this.DTOFactory = DTOFactory;
    }

    ResponseDTO toDTO(Message message) throws IOException {
        String data = new String(message.getBody(), Charset.forName(CHARSET));
        JsonNode root = objectMapper.readTree(data);

        return DTOFactory.createResponseDTO(getCommandId(root), getCommandResponseStatus(root), data);
    }

    private UUID getCommandId(JsonNode root) {
        JsonNode commandId = root.get("task_id");

        if (commandId.isMissingNode()) {
            throw new RuntimeException("cannot find field: 'task_id'");
        }

        return UUID.fromString(commandId.asText());
    }

    private CommandReturnStatus getCommandResponseStatus(JsonNode root) {
        JsonNode responseCode = root.get("result").get("rc");

        if (responseCode.isMissingNode()) {
            throw new RuntimeException("cannot find field: 'result.rc'");
        }

        return responseCode.asInt() == 0 ? CommandReturnStatus.OK : CommandReturnStatus.ERROR;
    }
}
