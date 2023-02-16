package dev.hmmr.taxi.management.backend.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resource with given id does not exist")
public class ResourceNotFoundException extends RuntimeException {}
