package ys.prototype.fmtaq.infrastructure.messaging.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CommandResponseJsonConverter.class, Jackson2ObjectMapperBuilderConfig.class})
public class CommandResponseJsonConverterTest {

    @Autowired
    private CommandResponseJsonConverter commandResponseJsonConverter;

    @Test
    public void toDTO() throws Exception {
        String messageString = "{\"account\":\"bckp-tst4\"," +
                "\"address\":\"tst100-uweb00\"," +
                "\"id\":\"28ea0c3a-bac2-11e6-a2d2-9105eba229dc\"," +
                "\"result\":{\"rc\":0}," +
                "\"task_id\":\"28ea196e-bac2-11e6-a2d2-9105eba229dc\"}";
        String messageString1 = "";
        CommandResponseDTO commandResponseDTO = commandResponseJsonConverter.toDTO(messageString1);

        assertThat(commandResponseDTO).isNotNull();
        assertThat(commandResponseDTO.getCommandId()).isEqualTo(UUID.fromString("28ea196e-bac2-11e6-a2d2-9105eba229dc"));

        System.out.println(commandResponseDTO.getCommandId());
        System.out.println(commandResponseDTO.getResponseStatus());
        System.out.println(commandResponseDTO.getBody());
    }
}
