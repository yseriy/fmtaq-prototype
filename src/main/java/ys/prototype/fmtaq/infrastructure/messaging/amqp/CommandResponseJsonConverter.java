package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.CommandResponseStatus;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandResponseJsonConverter {

    private final ObjectMapper objectMapper;

    public CommandResponseJsonConverter(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        objectMapper = jackson2ObjectMapperBuilder.build();
    }

    CommandResponseDTO toDTO(String response) throws IOException {
        if (response.isEmpty()) {
            throw emptyMessage();
        }

        Optional<JsonNode> rootOptional = Optional.ofNullable(objectMapper.readTree(response));
        JsonNode root = rootOptional.orElseThrow(this::noContentToBind);

        return new CommandResponseDTO(getCommandId(root), getCommandResponseStatus(root), response);
    }

    private UUID getCommandId(JsonNode root) {
        Optional<JsonNode> commandIdOptional = Optional.ofNullable(root.get("task_id"));
        JsonNode commandId = commandIdOptional.orElseThrow(() -> missingNode("task_id"));

        return UUID.fromString(commandId.asText());
    }

    private CommandResponseStatus getCommandResponseStatus(JsonNode root) {
        Optional<JsonNode> commandResponseOptional = Optional.ofNullable(root.get("result"));
        JsonNode commandResponse = commandResponseOptional.orElseThrow(() -> missingNode("result"));

        Optional<JsonNode> commandResponseCodeOptional = Optional.ofNullable(commandResponse.get("rc"));
        JsonNode commandResponseCode = commandResponseCodeOptional.orElseThrow(() -> missingNode("result.rc"));

        if (!commandResponseCode.isInt()) {
            throw canNotBeConvertedToAnInt();
        }

        return commandResponseCode.asInt() == 0 ? CommandResponseStatus.OK : CommandResponseStatus.ERROR;
    }

    private RuntimeException emptyMessage() {
        return new RuntimeException("receive empty message");
    }

    private RuntimeException noContentToBind() {
        return new RuntimeException("message has no content to bind");
    }

    private RuntimeException missingNode(String field) {
        String exceptionString = String.format("cannot find field: '%s'", field);
        return new RuntimeException(exceptionString);
    }

    private RuntimeException canNotBeConvertedToAnInt() {
        return new RuntimeException("response code can not be converted to an int");
    }
}
