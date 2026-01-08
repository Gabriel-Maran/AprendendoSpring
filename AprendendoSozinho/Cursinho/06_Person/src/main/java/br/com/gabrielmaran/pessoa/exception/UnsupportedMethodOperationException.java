package br.com.gabrielmaran.pessoa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
    public class UnsupportedMethodOperationException extends RuntimeException {
    public UnsupportedMethodOperationException(String message) {
        super(message);
    }
}
