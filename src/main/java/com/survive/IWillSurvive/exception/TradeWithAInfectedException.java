package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Hey! Don't touch this inventory, it's infected!")
public class TradeWithAInfectedException extends RuntimeException {

}
