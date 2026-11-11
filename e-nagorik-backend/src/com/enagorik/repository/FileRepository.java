package com.enagorik.repository;
import com.enagorik.model.*; import com.enagorik.util.Json; import java.io.*; import java.nio.file.*; import java.util.*;

public final class FileRepository {
  private final Path dir; private final Map<String,User> users=new LinkedHashMap<>(); private final Map<String,CivicService> services=new LinkedHashMap<>(); private final Map<String,ServiceRequest> requests=new LinkedHashMap<>();
  public FileRepository(Path dir){this.dir=dir;try{Files.createDirectories(dir);load();}catch(IOException e){throw new UncheckedIOException(e);}}
  @SuppressWarnings("unchecked") private void load() throws IOException {
    load("users.json", x -> {var m=(Map<String,Object>)x; users.put(String.valueOf(m.get("id")),new User(String.valueOf(m.get("id")),String.valueOf(m.get("name")),String.valueOf(m.get("email")),String.valueOf(m.get("password")),String.valueOf(m.get("role")),String.valueOf(m.get("phone"))));});
    load("services.json", x -> {var m=(Map<String,Object>)x; services.put(String.valueOf(m.get("id")),new CivicService(String.valueOf(m.get("id")),String.valueOf(m.get("name")),String.valueOf(m.get("description")),String.valueOf(m.get("category")),((Number)m.get("fee")).intValue(),Boolean.TRUE.equals(m.get("active"))));});
    load("requests.json", x -> {var m=(Map<String,Object>)x; requests.put(String.valueOf(m.get("id")),new ServiceRequest(String.valueOf(m.get("id")),String.valueOf(m.get("userId")),String.valueOf(m.get("serviceId")),String.valueOf(m.get("status")),String.valueOf(m.get("details")),((Number)m.get("createdAt")).longValue(),String.valueOf(m.get("trackingCode"))));});
  }
  private void load(String file, java.util.function.Consumer<Object> add) throws IOException {Path p=dir.resolve(file);if(!Files.exists(p))return;Object x=Json.parse(Files.readString(p));if(x instanceof Iterable<?> i)i.forEach(add);}
  public synchronized List<User> users(){return List.copyOf(users.values());} public synchronized List<CivicService> services(){return List.copyOf(services.values());} public synchronized List<ServiceRequest> requests(){return List.copyOf(requests.values());}
  public synchronized void user(User x){users.put(x.id(),x);save("users.json",users.values());} public synchronized void service(CivicService x){services.put(x.id(),x);save("services.json",services.values());} public synchronized void request(ServiceRequest x){requests.put(x.id(),x);save("requests.json",requests.values());}
  private void save(String file,Collection<?> xs){
    var values = xs.stream().map(x -> {
      if (x instanceof User u) return Map.of("id",u.id(),"name",u.name(),"email",u.email(),"password",u.password(),"role",u.role(),"phone",u.phone());
      if (x instanceof CivicService s) return Map.of("id",s.id(),"name",s.name(),"description",s.description(),"category",s.category(),"fee",s.fee(),"active",s.active());
      if (x instanceof ServiceRequest r) return Map.of("id",r.id(),"userId",r.userId(),"serviceId",r.serviceId(),"status",r.status(),"details",r.details(),"createdAt",r.createdAt(),"trackingCode",r.trackingCode());
      throw new IllegalArgumentException("Unsupported repository type: " + x.getClass().getName());
    }).toList();
    try{Files.writeString(dir.resolve(file),Json.stringify(values));}catch(IOException e){throw new UncheckedIOException(e);}
  }
}
