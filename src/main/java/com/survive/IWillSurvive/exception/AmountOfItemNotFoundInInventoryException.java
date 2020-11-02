package com.survive.IWillSurvive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Oh my... You don't have this amount of item.")
public class AmountOfItemNotFoundInInventoryException extends RuntimeException{
}
