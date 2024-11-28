package com.example.twillioautentication.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private String errorMessage;
    private Integer httpStatusCode;
}
