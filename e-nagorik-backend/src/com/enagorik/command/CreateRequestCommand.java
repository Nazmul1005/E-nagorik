package com.enagorik.command;
import com.enagorik.model.ServiceRequest; import com.enagorik.service.CivicService;
public record CreateRequestCommand(CivicService service,String userId,String serviceId,String details) implements Command<ServiceRequest> {
 public ServiceRequest execute(){return service.create(userId,serviceId,details);}
}
