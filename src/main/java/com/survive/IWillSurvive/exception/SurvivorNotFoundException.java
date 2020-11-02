package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "This survivor isn't among us")
public class SurvivorNotFoundException extends RuntimeException {

}
