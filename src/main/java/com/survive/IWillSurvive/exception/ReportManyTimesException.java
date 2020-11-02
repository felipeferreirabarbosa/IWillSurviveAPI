package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Please, don't report a infected so many times!")
public class ReportManyTimesException extends RuntimeException{
}
