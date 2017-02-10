package oracle.nosql.ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("ws")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> service = new HashSet<>();

    service.add(AuteurWS.class);
    service.add(LivreWS.class);

    return service;
  }     
}
