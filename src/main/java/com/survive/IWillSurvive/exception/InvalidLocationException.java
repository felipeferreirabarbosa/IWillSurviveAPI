package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Please, put your location in format (latitude,longitude)!")
public class InvalidLocationException extends RuntimeException{
}
