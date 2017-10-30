package ys.prototype.fmtaq.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.assembler.CommandAssembler;
import ys.prototype.fmtaq.application.dto.CommandResponseDTO;
import ys.prototype.fmtaq.domain.task.Command;

import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommandServiceTest {

    @MockBean
    private CommandAssembler commandAssembler;

    @Test
    public void handleResponse() {
        UUID commandId = UUID.randomUUID();
        CommandResponseDTO commandResponseDTO = mock(CommandResponseDTO.class);
        Command command = mock(Command.class);

        when(commandResponseDTO.getCommandId()).thenReturn(commandId);
        when(commandAssembler.getById(commandId)).thenReturn(command);

        CommandService commandService = new CommandService(commandAssembler);
        commandService.handleResponse(commandResponseDTO);

        verify(commandAssembler).getById(commandId);
        verify(command).handleResponse(commandResponseDTO.getResponseStatus());
    }
}
