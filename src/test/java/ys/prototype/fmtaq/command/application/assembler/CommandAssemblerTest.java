package ys.prototype.fmtaq.command.application.assembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.application.ApplicationErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandRepository;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommandAssemblerTest {

    @MockBean
    private CommandSender commandSender;

    @MockBean
    private CommandRepository commandRepository;

    @Test
    public void getById() {
        UUID commandId = UUID.randomUUID();
        Task task = mock(Task.class);
        Command command = mock(Command.class);
        when(command.getTask()).thenReturn(task);
        when(commandRepository.findOne(commandId)).thenReturn(command);

        CommandAssembler commandAssembler = new CommandAssembler(commandSender, commandRepository);
        Command testedCommand = commandAssembler.getById(commandId);

        assertThat(testedCommand).isEqualTo(command);
        verify(commandRepository).findOne(commandId);
        verify(command).setCommandSender(commandSender);
        verify(command.getTask()).setCommandSender(commandSender);
    }

    @Test
    public void commandNotFound() {
        UUID commandId = UUID.randomUUID();
        when(commandRepository.findOne(commandId)).thenReturn(null);
        CommandAssembler commandAssembler = new CommandAssembler(commandSender, commandRepository);

        Throwable thrown = catchThrowable(() -> commandAssembler.getById(commandId));

        assertThat(thrown).isInstanceOf(FmtaqException.class);
        assertThat(thrown).hasFieldOrPropertyWithValue("fmtaqError", ApplicationErrorList.COMMAND_NOT_FOUND);
    }
}
