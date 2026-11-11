package com.enagorik.facade;
import com.enagorik.model.*; import com.enagorik.service.CivicService; import java.util.*;
/** Stable application-facing facade for web and future adapters. */
public final class EnagorikFacade {
 private final CivicService service; public EnagorikFacade(CivicService service){this.service=service;}
 public User login(String email,String password){return service.login(email,password);}
 public List<com.enagorik.model.CivicService> services(){return service.services();}
 public List<ServiceRequest> requests(){return service.requests();}
 public ServiceRequest submit(String user,String civicService,String details){return service.create(user,civicService,details);}
}
