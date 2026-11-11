package com.enagorik.singleton;
import com.enagorik.facade.EnagorikFacade;
public final class AppContext { private static volatile AppContext instance; private final EnagorikFacade facade; private AppContext(EnagorikFacade f){facade=f;} public static AppContext initialize(EnagorikFacade f){if(instance==null)synchronized(AppContext.class){if(instance==null)instance=new AppContext(f);}return instance;} public static AppContext get(){if(instance==null)throw new IllegalStateException("Context not initialized");return instance;} public EnagorikFacade facade(){return facade;} }
