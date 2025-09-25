package br.com.gabrielmaran.aprendendoSpring.exception;

import java.util.Date;

public record ExceptionResponse(Date timeStamp, String message, String details ) {}