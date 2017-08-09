package ys.prototype.fmtaq.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.domain.dto.CommandResultDTO;

import java.nio.charset.Charset;
import java.util.UUID;

@Component
public class MessageToDTOConverter {

    public CommandResultDTO toDTO(Message message) {
        CommandResultDTO commandResultDTO = new CommandResultDTO();
        commandResultDTO.setBody(new String(message.getBody(), Charset.forName("UTF-8")));

        return commandResultDTO;
    }

    private UUID getCommandId() {
        return null;
    }

    private Boolean isCommandStatusOk(String body) {
        return null;
    }
}
