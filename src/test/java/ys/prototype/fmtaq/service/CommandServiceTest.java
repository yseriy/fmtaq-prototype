package ys.prototype.fmtaq.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandResponseStatus;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.dto.CommandResponseDTO;
import ys.prototype.fmtaq.repository.CommandRepository;

import java.util.UUID;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommandService.class)
public class CommandServiceTest {

    @MockBean
    private AmqpAdmin mockAmqpAdmin;
    @MockBean
    private AmqpTemplate mockAmqpTemplate;
    @MockBean
    private CommandRepository mockCommandRepository;
    @MockBean
    private FmtaqFactory mockFmtaqFactory;
    @Autowired
    private CommandService commandService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandResponseDTO mockResponseDTO;
    private Task mockTask;
    private Command mockCommand;
    private Queue mockQueue;

    @Before
    public void setup() {
        Integer step = 0;
        String address = "test_address";
        String body = "test_body";
        UUID uuid = UUID.fromString("2783eb47-7e4c-4871-b7b2-ba04a7b0ef75");

        mockResponseDTO = mock(CommandResponseDTO.class);
        mockTask = mock(Task.class);
        mockCommand = mock(Command.class);
        mockQueue = mock(Queue.class);

        when(mockResponseDTO.getCommandId()).thenReturn(uuid);
        when(mockResponseDTO.getResponseStatus()).thenReturn(CommandResponseStatus.OK);

        when(mockCommand.getAddress()).thenReturn(address);
        when(mockCommand.getBody()).thenReturn(body);
        when(mockCommand.getTask()).thenReturn(mockTask);
        when(mockCommand.getStep()).thenReturn(step);
        when(mockCommand.hasNextCommand()).thenReturn(true);
        when(mockCommand.nextStep()).thenReturn(step + 1);

        when(mockCommandRepository.findOne(anyObject())).thenReturn(mockCommand);
        when(mockCommandRepository.getNextCommand(anyObject(), anyObject(), anyInt())).thenReturn(mockCommand);
        when(mockFmtaqFactory.getQueue(anyString())).thenReturn(mockQueue);
    }

    @Test
    public void setStatusAndSendNextCommand() {
        InOrder inOrder = inOrder(mockCommandRepository, mockCommand, mockAmqpAdmin, mockFmtaqFactory, mockAmqpAdmin,
                mockAmqpTemplate);

        commandService.setStatusAndSendNextCommand(mockResponseDTO);

        inOrder.verify(mockCommandRepository).findOne(mockResponseDTO.getCommandId());
        inOrder.verify(mockCommand).setStatusFromResponse(mockResponseDTO.getResponseStatus());
        inOrder.verify(mockCommandRepository).getNextCommand(mockTask, CommandStatus.REGISTERED, mockCommand.nextStep());
        inOrder.verify(mockFmtaqFactory).getQueue(mockCommand.getAddress());
        inOrder.verify(mockAmqpAdmin).declareQueue(mockQueue);
        inOrder.verify(mockAmqpTemplate).convertAndSend(mockCommand.getAddress(), mockCommand.getBody());
    }

    @Test
    public void setStatusAndSendNextCommandHasNextCommandFalse() {
        InOrder inOrder = inOrder(mockCommandRepository, mockCommand, mockAmqpAdmin, mockFmtaqFactory, mockAmqpAdmin,
                mockAmqpTemplate);

        when(mockCommand.hasNextCommand()).thenReturn(false);

        commandService.setStatusAndSendNextCommand(mockResponseDTO);

        inOrder.verify(mockCommandRepository).findOne(mockResponseDTO.getCommandId());
        inOrder.verify(mockCommand).setStatusFromResponse(mockResponseDTO.getResponseStatus());
        verify(mockCommandRepository, never()).getNextCommand(anyObject(), anyObject(), anyInt());
        verify(mockFmtaqFactory, never()).getQueue(anyString());
        verify(mockAmqpAdmin, never()).declareQueue(anyObject());
        verify(mockAmqpTemplate, never()).convertAndSend(anyString(), anyString());
    }

    @Test
    public void setStatusAndSendNextCommandExceptionInFindOne() {
        String exceptionString = "command not found";

        when(mockCommandRepository.findOne(anyObject())).thenReturn(null);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(exceptionString);

        commandService.setStatusAndSendNextCommand(mockResponseDTO);
    }

    @Test
    public void setStatusAndSendNextCommandExceptionInGetNextCommand() {
        String exceptionString = "cannot found next command";

        when(mockCommandRepository.getNextCommand(anyObject(), anyObject(), anyInt())).thenReturn(null);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage(exceptionString);

        commandService.setStatusAndSendNextCommand(mockResponseDTO);
    }

    @Test
    public void sendCommand() {
        InOrder inOrder = inOrder(mockCommandRepository, mockCommand, mockAmqpAdmin, mockFmtaqFactory, mockAmqpAdmin,
                mockAmqpTemplate);

        commandService.sendCommand(mockCommand);

        inOrder.verify(mockFmtaqFactory).getQueue(mockCommand.getAddress());
        inOrder.verify(mockAmqpAdmin).declareQueue(mockQueue);
        inOrder.verify(mockAmqpTemplate).convertAndSend(mockCommand.getAddress(), mockCommand.getBody());
    }
}
