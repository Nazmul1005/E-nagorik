package com.enagorik.util;
public final class AppConfig { private AppConfig(){} public static int port(){return Integer.getInteger("enagorik.port",8080);} }
