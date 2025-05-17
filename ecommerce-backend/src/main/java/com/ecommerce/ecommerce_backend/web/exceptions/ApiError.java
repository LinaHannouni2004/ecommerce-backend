package com.ecommerce.ecommerce_backend.web.exceptions;

public class ApiError extends RuntimeException {
  public ApiError(String message) {
    super(message);
  }
}
