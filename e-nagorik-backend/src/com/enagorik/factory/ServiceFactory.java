package com.enagorik.factory;
import com.enagorik.model.CivicService;
public final class ServiceFactory { private ServiceFactory(){} public static CivicService create(String id,String name,String description,String category,int fee){return new CivicService(id,name,description,category,fee,true);} }
