package ys.prototype.fmtaq.query;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.jdbc.core.RowCallbackHandler;
import ys.prototype.fmtaq.domain.FmtaqException;
import ys.prototype.fmtaq.query.dto.TaskResponseDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowCallbackHandler implements RowCallbackHandler {

    private final PrintWriter printWriter;
    private final ObjectMapper objectMapper;

    TaskRowCallbackHandler(PrintWriter printWriter, ObjectMapper objectMapper) {
        this.printWriter = printWriter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void processRow(ResultSet rs) {
        try {
            tryProcessRow(rs);
        } catch (SQLException e) {
            throw cannotGetColumnValueByColumnLabel(e);
        } catch (JsonGenerationException | JsonMappingException e) {
            throw cannotConvertToJson(e);
        } catch (IOException e) {
            throw cannotWriteValueToStream(e);
        }
    }

    private void tryProcessRow(ResultSet rs) throws SQLException, IOException {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(rs.getString("task_id"),
                rs.getString("task_status"), rs.getString("task_start_time"),
                rs.getString("task_status_time"), rs.getString("command_body"));
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String taskResponseJsonString = objectWriter.writeValueAsString(taskResponseDTO);
        printWriter.println(taskResponseJsonString);
    }

    private FmtaqException cannotGetColumnValueByColumnLabel(Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_GET_COLUMN_VALUE_BY_COLUMN_LABEL, e);
    }

    private FmtaqException cannotConvertToJson(Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_CONVERT_TO_JSON, e);
    }

    private FmtaqException cannotWriteValueToStream(Throwable e) {
        return new FmtaqException(QueryErrorList.CANNOT_WRITE_VALUE_TO_STREAM, e);
    }
}
