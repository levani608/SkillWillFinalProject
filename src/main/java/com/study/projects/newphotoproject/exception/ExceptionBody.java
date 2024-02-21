package com.study.projects.newphotoproject.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionBody {

    private String status;

    private String message;

    private LocalDateTime timestamp;

    private String path;

}
