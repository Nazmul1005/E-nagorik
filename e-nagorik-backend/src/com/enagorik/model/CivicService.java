package com.enagorik.model;
public record CivicService(String id, String name, String description, String category, int fee, boolean active) {
  public CivicService { if (id == null || name == null || category == null || fee < 0) throw new IllegalArgumentException("Invalid service"); }
}
