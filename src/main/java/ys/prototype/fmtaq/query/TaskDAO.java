package ys.prototype.fmtaq.query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.query.dto.TaskIdRequestDTO;
import ys.prototype.fmtaq.query.dto.UserInfoDTO;

import java.io.PrintWriter;

@Component
@Transactional(readOnly = true)
public class TaskDAO {

    private final static int FETCH_SIZE = 100;
    private final static String MAIN_SQL = "select t.id as task_id, t.task_status as task_status, "
            + "t.start_timestamp as task_start_time, "
            + "t.status_timestamp as task_status_time, c.body as command_body "
            + "from task t "
            + "left join command c "
            + "on t.id = c.task_id ";
    private final static String TASK_LIST_BY_USER_INFO_SQL = MAIN_SQL + "where t.account = ? AND t.service_type = ?";
    private final static String TASK_LIST_BY_TASK_ID = MAIN_SQL + "where t.id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowCallbackHandlerInjector rowCallbackHandlerInjector;

    public TaskDAO(JdbcTemplate jdbcTemplate, RowCallbackHandlerInjector rowCallbackHandlerInjector) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowCallbackHandlerInjector = rowCallbackHandlerInjector;
    }

    public void getTaskListByUserInfo(PrintWriter printWriter, UserInfoDTO userInfoDTO) {
        Object[] queryArguments = new Object[]{userInfoDTO.getAccount(), userInfoDTO.getServiceType()};
        RowCallbackHandler rowCallbackHandler = rowCallbackHandlerInjector.injectTaskRowCallbackHandler(printWriter);

        jdbcTemplate.setFetchSize(FETCH_SIZE);
        jdbcTemplate.query(TASK_LIST_BY_USER_INFO_SQL, queryArguments, rowCallbackHandler);
    }

    public void getTaskListByTaskId(PrintWriter printWriter, TaskIdRequestDTO taskIdRequestDTO) {
        Object[] queryArguments = new Object[]{taskIdRequestDTO.getId()};
        RowCallbackHandler rowCallbackHandler = rowCallbackHandlerInjector.injectTaskRowCallbackHandler(printWriter);

        jdbcTemplate.setFetchSize(FETCH_SIZE);
        jdbcTemplate.query(TASK_LIST_BY_TASK_ID, queryArguments, rowCallbackHandler);
    }
}
