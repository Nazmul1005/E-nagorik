package com.enagorik.util;
import java.util.*;

/** Small JSON parser/writer sufficient for the API, with no external dependencies. */
public final class Json {
  private Json() {}
  public static Object parse(String s) { return new Parser(s).value(); }
  public static String stringify(Object o) {
    if (o == null) return "null";
    if (o instanceof String x) return "\"" + esc(x) + "\"";
    if (o instanceof Number || o instanceof Boolean) return o.toString();
    if (o instanceof Map<?,?> m) { var b=new StringBuilder("{"); boolean first=true; for(var e:m.entrySet()){if(!first)b.append(',');first=false;b.append(stringify(String.valueOf(e.getKey()))).append(':').append(stringify(e.getValue()));} return b.append('}').toString(); }
    if (o instanceof Iterable<?> i) { var b=new StringBuilder("["); boolean first=true; for(var x:i){if(!first)b.append(',');first=false;b.append(stringify(x));} return b.append(']').toString(); }
    return stringify(o.toString());
  }
  private static String esc(String s){return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r");}
  @SuppressWarnings("unchecked") public static Map<String,Object> object(String s){Object x=parse(s); if(!(x instanceof Map)) throw new IllegalArgumentException("JSON object required"); return (Map<String,Object>)x;}
  public static String str(Map<String,Object> m,String k){Object x=m.get(k);return x==null?null:String.valueOf(x);}
  private static final class Parser {
    final String s; int p; Parser(String s){this.s=s.trim();}
    Object value(){skip(); if(p>=s.length())throw err(); char c=s.charAt(p); if(c=='{')return obj(); if(c=='[')return arr(); if(c=='"')return string(); if(s.startsWith("true",p)){p+=4;return true;}if(s.startsWith("false",p)){p+=5;return false;}if(s.startsWith("null",p)){p+=4;return null;} return number();}
    Map<String,Object> obj(){p++;Map<String,Object> m=new LinkedHashMap<>();skip();if(peek('}')){p++;return m;}while(true){skip();String k=string();skip();expect(':');m.put(k,value());skip();if(peek('}')){p++;return m;}expect(',');}}
    List<Object> arr(){p++;List<Object> a=new ArrayList<>();skip();if(peek(']')){p++;return a;}while(true){a.add(value());skip();if(peek(']')){p++;return a;}expect(',');}}
    String string(){expect('"');StringBuilder b=new StringBuilder();while(p<s.length()){char c=s.charAt(p++);if(c=='"')return b.toString();if(c=='\\'&&p<s.length()){char e=s.charAt(p++);b.append(e=='n'?'\n':e=='r'?'\r':e=='t'?'\t':e);}else b.append(c);}throw err();}
    Number number(){int st=p;while(p<s.length()&&"-+.0123456789eE".indexOf(s.charAt(p))>=0)p++;String n=s.substring(st,p);try{return n.contains(".")||n.contains("e")||n.contains("E")?Double.parseDouble(n):Long.parseLong(n);}catch(Exception e){throw err();}}
    void skip(){while(p<s.length()&&Character.isWhitespace(s.charAt(p)))p++;}boolean peek(char c){return p<s.length()&&s.charAt(p)==c;}void expect(char c){skip();if(!peek(c))throw err();p++;}IllegalArgumentException err(){return new IllegalArgumentException("Malformed JSON at "+p);}
  }
}
