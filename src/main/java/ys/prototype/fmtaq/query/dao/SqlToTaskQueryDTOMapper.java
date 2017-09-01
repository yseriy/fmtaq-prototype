package ys.prototype.fmtaq.query.dao;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.query.dto.CommandQueryDTO;
import ys.prototype.fmtaq.query.dto.TaskQueryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class SqlToTaskQueryDTOMapper {

    List<TaskQueryDTO> map(ResultSet rs) throws SQLException {
        Map<Integer, TaskQueryDTO> taskMap = new HashMap<>();
        Map<Integer, CommandQueryDTO> commandMap = new HashMap<>();

        while (rs.next()) {
            mapCommand(commandMap, rs, mapTask(taskMap, rs));
        }

        return new ArrayList<>(taskMap.values());
    }

    private TaskQueryDTO mapTask(final Map<Integer, TaskQueryDTO> taskMap, final ResultSet rs) throws SQLException {
        Integer taskId = rs.getInt("t_id");
        TaskQueryDTO taskQueryDTO = taskMap.get(taskId);

        if (taskQueryDTO == null) {
            taskQueryDTO = new TaskQueryDTO(taskId.toString(), rs.getString("t_status"));
            taskMap.put(taskId, taskQueryDTO);
        }

        return taskQueryDTO;
    }

    private void mapCommand(final Map<Integer, CommandQueryDTO> commandMap, final ResultSet rs,
                            final TaskQueryDTO taskQueryDTO) throws SQLException {

        Integer commandId = rs.getInt("c_id");
        CommandQueryDTO commandQueryDTO = commandMap.get(commandId);

        if (commandQueryDTO == null) {
            commandQueryDTO = new CommandQueryDTO(commandId.toString(), rs.getString("c_status"),
                    rs.getString("c_address"), rs.getString("c_body"));
            commandMap.put(commandId, commandQueryDTO);
        }

        if (!taskQueryDTO.getCommands().contains(commandQueryDTO)) {
            taskQueryDTO.getCommands().add(commandQueryDTO);
        }
    }
}
