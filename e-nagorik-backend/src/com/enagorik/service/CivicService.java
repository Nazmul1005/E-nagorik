package com.enagorik.service;
import com.enagorik.model.*; import com.enagorik.repository.FileRepository; import java.util.*; import java.util.concurrent.atomic.AtomicLong;
public final class CivicService {
 private final FileRepository repo; private final AtomicLong ids=new AtomicLong();
 public CivicService(FileRepository r){repo=r; repo.requests().stream().mapToLong(x -> {
   try { return Long.parseLong(x.id().substring(1)); } catch (RuntimeException ignored) { return 0; }
 }).max().ifPresent(ids::set);}
 public void seed(){if(repo.users().isEmpty()){repo.user(new User("u1","Demo Citizen","citizen@example.com","password","CITIZEN","01700000000"));repo.user(new User("a1","Administrator","admin@example.com","admin","ADMIN","01700000001"));}if(repo.services().isEmpty()){repo.service(new com.enagorik.model.CivicService("birth-certificate","Birth Certificate","Apply for a birth certificate","DOCUMENT",200,true));repo.service(new com.enagorik.model.CivicService("trade-license","Trade License","Apply for a trade license","BUSINESS",500,true));repo.service(new com.enagorik.model.CivicService("water-connection","Water Connection","Request a new water connection","UTILITY",1000,true));}}
 public List<User> users(){return repo.users();} public List<com.enagorik.model.CivicService> services(){return repo.services();} public List<ServiceRequest> requests(){return repo.requests();}
 public User login(String email,String password){return repo.users().stream().filter(x->x.email().equalsIgnoreCase(email)&&x.password().equals(password)).findFirst().orElse(null);}
 public ServiceRequest create(String user,String service,String details){if(repo.users().stream().noneMatch(x->x.id().equals(user)))throw new IllegalArgumentException("User not found");if(repo.services().stream().noneMatch(x->x.id().equals(service)&&x.active()))throw new IllegalArgumentException("Service not found");String id="r"+ids.incrementAndGet();var x=new ServiceRequest(id,user,service,"SUBMITTED",details==null?"":details,System.currentTimeMillis(),"EN-"+System.currentTimeMillis());repo.request(x);return x;}
 public ServiceRequest update(String id,String status){var old=repo.requests().stream().filter(x->x.id().equals(id)).findFirst().orElseThrow(()->new IllegalArgumentException("Request not found"));var x=new ServiceRequest(old.id(),old.userId(),old.serviceId(),status,old.details(),old.createdAt(),old.trackingCode());repo.request(x);return x;}
}
