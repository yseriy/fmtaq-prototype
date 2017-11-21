package ys.prototype.fmtaq.query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.query.dto.TaskIdQueryDTO;
import ys.prototype.fmtaq.query.dto.TaskQueryDTO;
import ys.prototype.fmtaq.query.dto.UserInfoQueryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class TaskDAO {

    private static final int FETCH_SIZE = 100;
    private static final String MAIN_SQL = "select t.id as task_id, t.task_status as task_status, "
            + "t.start_timestamp as task_start_time, "
            + "t.status_timestamp as task_status_time, c.body as command_body "
            + "from task t "
            + "left join command c "
            + "on t.id = c.task_id ";
    private static final String TASK_LIST_BY_USER_INFO_SQL = MAIN_SQL + "where t.account = ? AND t.service_type = ?";
    private static final String TASK_LIST_BY_TASK_ID = MAIN_SQL + "where t.id = ?";

    private final JdbcTemplate jdbcTemplate;

    public TaskDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setFetchSize(FETCH_SIZE);
    }

    public List<TaskQueryDTO> getTaskListByUserInfo(final UserInfoQueryDTO userInfoQueryDTO) {
        checkParamsUserInfo(userInfoQueryDTO);
        Object[] queryArguments = new Object[]{userInfoQueryDTO.getAccount(), userInfoQueryDTO.getServiceType()};
        return jdbcTemplate.query(TASK_LIST_BY_USER_INFO_SQL, queryArguments, this::rowMapper);
    }

    public List<TaskQueryDTO> getTaskListById(final TaskIdQueryDTO taskIdQueryDTO) {
        checkParamsTaskId(taskIdQueryDTO);
        Object[] queryArguments = new Object[]{taskIdQueryDTO.getId()};
        return jdbcTemplate.query(TASK_LIST_BY_TASK_ID, queryArguments, this::rowMapper);
    }

    private void checkParamsUserInfo(final UserInfoQueryDTO userInfoQueryDTO) {
        assert userInfoQueryDTO != null : "UserInfoQueryDTO cannot be null";
        assert userInfoQueryDTO.getAccount() != null : "task account cannot be null";
        assert userInfoQueryDTO.getServiceType() != null : "task service type cannot be null";
    }

    private void checkParamsTaskId(final TaskIdQueryDTO taskIdQueryDTO) {
        assert taskIdQueryDTO != null : "TaskIdQueryDTO cannot be null";
        assert taskIdQueryDTO.getId() != null : "task id cannot be null";
    }

    private TaskQueryDTO rowMapper(final ResultSet rs, final int rowNum) throws SQLException {
        return new TaskQueryDTO(rs.getString("task_id"), rs.getString("task_status"),
                rs.getString("task_start_time"), rs.getString("task_status_time"),
                rs.getString("command_body"));
    }
}
