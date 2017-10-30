package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.CommandResponseStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JacksonAutoConfiguration.class)
public class CommandResponseJsonConverterTest {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Test
    public void toDTO() {
        String messageString = "{\"account\":\"bckp-tst4\","
                + "\"address\":\"tst100-uweb00\","
                + "\"id\":\"28ea0c3a-bac2-11e6-a2d2-9105eba229dc\","
                + "\"result\":{\"rc\":0},"
                + "\"task_id\":\"28ea196e-bac2-11e6-a2d2-9105eba229dc\"}";

        CommandResponseJsonConverter commandResponseJsonConverter = new CommandResponseJsonConverter(jackson2ObjectMapperBuilder);
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(messageString);

        assertThat(commandResponseDTO).isNotNull();
        assertThat(commandResponseDTO.getCommandId()).isEqualTo(UUID.fromString("28ea196e-bac2-11e6-a2d2-9105eba229dc"));
        assertThat(commandResponseDTO.getResponseStatus()).isEqualTo(CommandResponseStatus.OK);
    }
}
