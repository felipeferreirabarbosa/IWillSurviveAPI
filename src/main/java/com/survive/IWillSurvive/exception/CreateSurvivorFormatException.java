package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Please, put your information in correct format!")
public class CreateSurvivorFormatException extends IllegalArgumentException {
    public CreateSurvivorFormatException(String errorMessage) {super(errorMessage); }
}
