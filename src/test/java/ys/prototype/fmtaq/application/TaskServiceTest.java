package ys.prototype.fmtaq.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.assembler.TaskIdAssembler;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @MockBean
    private TaskAssembler taskAssembler;

    @MockBean
    private TaskIdAssembler taskIdAssembler;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void startAsyncTask() {
        UUID taskId = UUID.randomUUID();
        TaskIdDTO taskIdDTO = mock(TaskIdDTO.class);
        TaskDTO taskDTO = mock(TaskDTO.class);
        Task task = mock(Task.class);

        when(task.getId()).thenReturn(taskId);
        when(taskAssembler.fromDTO(taskDTO)).thenReturn(task);
        when(taskIdAssembler.toDTO(taskId)).thenReturn(taskIdDTO);

        TaskService taskService = new TaskService(taskAssembler, taskIdAssembler, taskRepository);
        TaskIdDTO testedTaskIdDTO = taskService.startAsyncTask(taskDTO);

        assertThat(testedTaskIdDTO).isNotNull();
        assertThat(testedTaskIdDTO).isEqualTo(taskIdDTO);
        verify(taskAssembler).fromDTO(taskDTO);
        verify(taskRepository).save(task);
        verify(taskIdAssembler).toDTO(taskId);
    }
}
