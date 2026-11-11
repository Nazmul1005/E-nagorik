package com.enagorik.model;
public record ServiceRequest(String id, String userId, String serviceId, String status, String details, long createdAt, String trackingCode) {
  public ServiceRequest { if (id == null || userId == null || serviceId == null || status == null) throw new IllegalArgumentException("Invalid request"); }
}
