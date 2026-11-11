package com.enagorik.model;
public record User(String id, String name, String email, String password, String role, String phone) {
  public User { if (id == null || name == null || email == null || role == null) throw new IllegalArgumentException("Invalid user"); }
}
