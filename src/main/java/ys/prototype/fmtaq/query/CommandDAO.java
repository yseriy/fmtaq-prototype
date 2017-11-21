package ys.prototype.fmtaq.query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.query.dto.CommandIdQueryDTO;
import ys.prototype.fmtaq.query.dto.CommandQueryDTO;
import ys.prototype.fmtaq.query.dto.TaskIdQueryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class CommandDAO {

    private static final int FETCH_SIZE = 100;
    private static final String MAIN_SQL = "select id, task_id, start_timestamp, status_timestamp,"
            + " command_status, body from command ";
    private static final String COMMAND_LIST_BY_TASK_ID = MAIN_SQL + "where task_id = ?";
    private static final String COMMAND_LIST_BY_ID = MAIN_SQL + "where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public CommandDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setFetchSize(FETCH_SIZE);
    }

    public List<CommandQueryDTO> getCommandListByTaskId(final TaskIdQueryDTO taskIdQueryDTO) {
        checkParamsGetCommandListByTaskId(taskIdQueryDTO);
        Object[] queryArguments = new Object[]{taskIdQueryDTO.getId()};
        return jdbcTemplate.query(COMMAND_LIST_BY_TASK_ID, queryArguments, this::rowMapper);
    }

    public List<CommandQueryDTO> getCommandListById(final CommandIdQueryDTO commandIdQueryDTO) {
        checkParamsGetCommandListById(commandIdQueryDTO);
        Object[] queryArguments = new Object[]{commandIdQueryDTO.getId()};
        return jdbcTemplate.query(COMMAND_LIST_BY_ID, queryArguments, this::rowMapper);
    }

    private void checkParamsGetCommandListByTaskId(final TaskIdQueryDTO taskIdQueryDTO) {
        assert taskIdQueryDTO != null : "TaskIdQueryDTO cannot be null";
        assert taskIdQueryDTO.getId() != null : "task id cannot be null";
    }

    private void checkParamsGetCommandListById(final CommandIdQueryDTO commandIdQueryDTO) {
        assert commandIdQueryDTO != null : "CommandIdQueryDTO cannot be null";
        assert commandIdQueryDTO.getId() != null : "command id cannot be null";
    }

    private CommandQueryDTO rowMapper(final ResultSet rs, final int rowNum) throws SQLException {
        return new CommandQueryDTO(rs.getString("id"), rs.getString("task_id"),
                rs.getString("command_status"), rs.getString("start_timestamp"),
                rs.getString("status_timestamp"), rs.getString("body"));
    }
}
