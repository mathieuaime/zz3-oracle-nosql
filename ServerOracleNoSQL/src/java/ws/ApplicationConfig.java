package ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("ws")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> service = new HashSet<>();

    service.add(AutorWS.class);
    service.add(ArticleWS.class);

    return service;
  }     
}
