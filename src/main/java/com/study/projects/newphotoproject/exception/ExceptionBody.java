package com.study.projects.newphotoproject.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionBody {

    private HttpStatusCode status;

    private String message;

    private LocalDateTime timestamp;

    private String path;

}
