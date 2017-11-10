package ys.prototype.fmtaq.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class RowCallbackHandlerInjector {

    private final ObjectMapper objectMapper;

    public RowCallbackHandlerInjector(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.objectMapper = jackson2ObjectMapperBuilder.build();
    }

    RowCallbackHandler injectTaskRowCallbackHandler(PrintWriter printWriter) {
        return new TaskRowCallbackHandler(printWriter, objectMapper);
    }
}
