package com.enagorik.api;
import com.enagorik.model.*; import com.enagorik.service.CivicService; import com.enagorik.util.Json; import com.sun.net.httpserver.*; import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets; import java.util.*;
public final class ApiServer {
 private final HttpServer server; private final CivicService svc;
 public ApiServer(int port,CivicService s)throws IOException{svc=s;server=HttpServer.create(new InetSocketAddress(port),0);server.createContext("/",this::handle);server.setExecutor(null);}
 public void start(){server.start();}
 private void handle(HttpExchange e){try{String path=e.getRequestURI().getPath(),method=e.getRequestMethod(); if(method.equals("OPTIONS")){send(e,204,Map.of());return;} Object out;int code=200;
   if(path.equals("/health"))out=Map.of("status","ok");
   else if(path.equals("/api/services"))out=svc.services().stream().map(this::service).toList();
   else if(path.equals("/api/users")&&method.equals("GET"))out=svc.users().stream().map(this::publicUser).toList();
   else if(path.equals("/api/auth/login")&&method.equals("POST")){var m=Json.object(body(e));var u=svc.login(Json.str(m,"email"),Json.str(m,"password"));if(u==null){send(e,401,Map.of("error","Invalid credentials"));return;}out=publicUser(u);}
   else if(path.equals("/api/requests")&&method.equals("GET"))out=svc.requests().stream().map(this::request).toList();
   else if(path.equals("/api/requests")&&method.equals("POST")){var m=Json.object(body(e));out=request(svc.create(Json.str(m,"userId"),Json.str(m,"serviceId"),Json.str(m,"details")));code=201;}
   else if(path.startsWith("/api/requests/")&&method.equals("PATCH")){var m=Json.object(body(e));out=request(svc.update(path.substring(14),Json.str(m,"status")));}
   else {send(e,404,Map.of("error","Not found"));return;}send(e,code,out);
 }catch(IllegalArgumentException ex){safeSend(e,400,Map.of("error",ex.getMessage()));}catch(Exception ex){safeSend(e,500,Map.of("error","Internal server error"));}}
 private void safeSend(HttpExchange e,int code,Object x){try{send(e,code,x);}catch(IOException ignored){}}
 private String body(HttpExchange e)throws IOException{return new String(e.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);}
 private void send(HttpExchange e,int code,Object x)throws IOException{byte[] b=Json.stringify(x).getBytes(StandardCharsets.UTF_8);var h=e.getResponseHeaders();h.set("Content-Type","application/json; charset=utf-8");h.set("Access-Control-Allow-Origin","*");h.set("Access-Control-Allow-Methods","GET,POST,PATCH,OPTIONS");h.set("Access-Control-Allow-Headers","Content-Type,Authorization");e.sendResponseHeaders(code,code==204? -1:b.length);if(code!=204)try(var o=e.getResponseBody()){o.write(b);}}
 private Map<String,Object> publicUser(User x){return Map.of("id",x.id(),"name",x.name(),"email",x.email(),"role",x.role(),"phone",x.phone());}
 private Map<String,Object> service(com.enagorik.model.CivicService x){return Map.of("id",x.id(),"name",x.name(),"description",x.description(),"category",x.category(),"fee",x.fee(),"active",x.active());}
 private Map<String,Object> request(ServiceRequest x){return Map.of("id",x.id(),"userId",x.userId(),"serviceId",x.serviceId(),"status",x.status(),"details",x.details(),"createdAt",x.createdAt(),"trackingCode",x.trackingCode());}
}
