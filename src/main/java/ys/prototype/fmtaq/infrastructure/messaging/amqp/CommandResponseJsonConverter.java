package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.exception.ErrorCode;
import ys.prototype.fmtaq.exception.FmtaqException;
import ys.prototype.fmtaq.exception.JsonConvertingCode;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandResponseJsonConverter {

    private final static String COMMAND_ID_NODE_NAME = "task_id";
    private final static String RESPONSE_NODE_NAME = "result";
    private final static String RESPONSE_CODE_NODE_NAME = "rc";
    private final ObjectMapper objectMapper;

    public CommandResponseJsonConverter(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        objectMapper = jackson2ObjectMapperBuilder.build();
    }

    CommandResponseDTO toDTO(String messageBody) {
        assert messageBody != null : "message body cannot be null";

        JsonNode root = getMessageBodyJsonRoot(messageBody);
        UUID commandId = getCommandId(root);
        CommandResponseStatus commandResponseStatus = getCommandResponseStatus(root);

        return new CommandResponseDTO(commandId, commandResponseStatus, messageBody);
    }

    private JsonNode getMessageBodyJsonRoot(String messageBody) {
        try {
            Optional<JsonNode> rootOptional = Optional.ofNullable(objectMapper.readTree(messageBody));
            return rootOptional.orElseThrow(() -> exception(JsonConvertingCode.NO_CONTENT_TO_BIND));
        } catch (IOException e) {
            throw exception(JsonConvertingCode.BAD_JSON_FORMAT, e).set("json string", messageBody);
        }
    }

    private UUID getCommandId(JsonNode root) {
        JsonNode commandIdJsonNode = root.path(COMMAND_ID_NODE_NAME);

        if (commandIdJsonNode.isMissingNode()) {
            throw exception(JsonConvertingCode.MISSING_NODE).set("node path", COMMAND_ID_NODE_NAME);
        }

        if (!commandIdJsonNode.isTextual()) {
            throw exception(JsonConvertingCode.CANNOT_BE_CONVERTED_TO_A_TEXT).set("node path", COMMAND_ID_NODE_NAME);
        }

        try {
            return UUID.fromString(commandIdJsonNode.asText());
        } catch (IllegalArgumentException e) {
            throw exception(JsonConvertingCode.BAD_UUID_FORMAT, e).set("node path", COMMAND_ID_NODE_NAME)
                    .set("node value", commandIdJsonNode.asText());
        }
    }

    private CommandResponseStatus getCommandResponseStatus(JsonNode root) {
        JsonNode commandResponseCodeJsonNode = root.path(RESPONSE_NODE_NAME).path(RESPONSE_CODE_NODE_NAME);

        if (commandResponseCodeJsonNode.isMissingNode()) {
            throw exception(JsonConvertingCode.MISSING_NODE)
                    .set("node path", RESPONSE_NODE_NAME + "." + RESPONSE_CODE_NODE_NAME);
        }

        if (!commandResponseCodeJsonNode.isInt()) {
            throw exception(JsonConvertingCode.CANNOT_BE_CONVERTED_TO_AN_INT)
                    .set("node path", RESPONSE_NODE_NAME + "." + RESPONSE_CODE_NODE_NAME);
        }

        return commandResponseCodeJsonNode.asInt() == 0 ? CommandResponseStatus.OK : CommandResponseStatus.ERROR;
    }

    private FmtaqException exception(ErrorCode errorCode) {
        return new FmtaqException(errorCode);
    }

    private FmtaqException exception(ErrorCode errorCode, Throwable cause) {
        return new FmtaqException(errorCode, cause);
    }
}
