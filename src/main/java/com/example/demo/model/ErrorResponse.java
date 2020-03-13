package com.example.demo.model;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
  private HttpStatus message;
  private Long id;

  public ErrorResponse(HttpStatus message, Long id) {
    this.message = message;
    this.id = id;
  }

  public HttpStatus getMessage() {
    return this.message;
  }

  public Long getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return this.id + ": " + this.message;
  }
}