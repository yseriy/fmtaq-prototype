package ys.prototype.fmtaq.query;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ys.prototype.fmtaq.domain.FmtaqException;
import ys.prototype.fmtaq.query.dto.TaskIdRequestDTO;
import ys.prototype.fmtaq.query.dto.TaskResponseDTO;
import ys.prototype.fmtaq.query.dto.UserInfoDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private final ObjectWriter objectWriter;

    public TaskDAO(final JdbcTemplate jdbcTemplate, final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    public void getTaskListByUserInfo(final PrintWriter printWriter, final UserInfoDTO userInfoDTO) {
        checkParamsUserInfo(printWriter, userInfoDTO);
        Object[] queryArguments = new Object[]{userInfoDTO.getAccount(), userInfoDTO.getServiceType()};
        RowCallbackHandler rowCallbackHandler = (rs) -> taskRowCallbackHandler(printWriter, rs);

        jdbcTemplate.setFetchSize(FETCH_SIZE);
        jdbcTemplate.query(TASK_LIST_BY_USER_INFO_SQL, queryArguments, rowCallbackHandler);
    }

    private void checkParamsUserInfo(final PrintWriter printWriter, final UserInfoDTO userInfoDTO) {
        assert printWriter != null : "PrintWriter cannot be null";
        assert userInfoDTO != null : "UserInfoDTO cannot be null";
        assert userInfoDTO.getAccount() != null : "task account cannot be null";
        assert userInfoDTO.getServiceType() != null : "task service type cannot be null";
    }

    public void getTaskListByTaskId(final PrintWriter printWriter, final TaskIdRequestDTO taskIdRequestDTO) {
        checkParamsTaskId(printWriter, taskIdRequestDTO);
        Object[] queryArguments = new Object[]{taskIdRequestDTO.getId()};
        RowCallbackHandler rowCallbackHandler = (rs) -> taskRowCallbackHandler(printWriter, rs);

        jdbcTemplate.setFetchSize(FETCH_SIZE);
        jdbcTemplate.query(TASK_LIST_BY_TASK_ID, queryArguments, rowCallbackHandler);
    }

    private void checkParamsTaskId(final PrintWriter printWriter, final TaskIdRequestDTO taskIdRequestDTO) {
        assert printWriter != null : "PrintWriter cannot be null";
        assert taskIdRequestDTO != null : "TaskIdRequestDTO cannot be null";
        assert taskIdRequestDTO.getId() != null : "task id cannot be null";
    }

    private void taskRowCallbackHandler(final PrintWriter printWriter, final ResultSet rs) {
        try {
            tryProcessRow(printWriter, rs);
        } catch (SQLException e) {
            throw cannotGetColumnValueByColumnLabel(e);
        } catch (JsonGenerationException | JsonMappingException e) {
            throw cannotConvertToJson(e);
        } catch (IOException e) {
            throw cannotWriteValueToStream(e);
        }
    }

    private void tryProcessRow(final PrintWriter printWriter, final ResultSet rs) throws SQLException, IOException {
        String taskResponseString = convertToJsonString(rs);
        printWriter.println(taskResponseString);
    }

    private String convertToJsonString(final ResultSet rs) throws SQLException, JsonProcessingException {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(rs.getString("task_id"),
                rs.getString("task_status"), rs.getString("task_start_time"),
                rs.getString("task_status_time"), rs.getString("command_body"));
        return objectWriter.writeValueAsString(taskResponseDTO);
    }

    private FmtaqException cannotGetColumnValueByColumnLabel(final Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_GET_COLUMN_VALUE_BY_COLUMN_LABEL, e);
    }

    private FmtaqException cannotConvertToJson(final Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_CONVERT_TO_JSON, e);
    }

    private FmtaqException cannotWriteValueToStream(final Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_WRITE_VALUE_TO_STREAM, e);
    }
}
