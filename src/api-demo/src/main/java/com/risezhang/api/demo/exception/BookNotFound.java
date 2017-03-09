package com.risezhang.api.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by liang on 09/03/2017.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such book!")
public class BookNotFound extends RuntimeException {
}
