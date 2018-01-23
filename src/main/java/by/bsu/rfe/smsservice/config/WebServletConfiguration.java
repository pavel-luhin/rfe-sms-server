package by.bsu.rfe.smsservice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebServletConfiguration implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setServletContext(servletContext);
    context.register(
        ApplicationConfiguration.class,
        CacheConfiguration.class,
        DatabaseConfiguration.class,
        SchedulerConfiguration.class,
        SecurityConfiguration.class,
        WebConfiguration.class
    );

    ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher",
        new DispatcherServlet(context));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/rest/*");
  }
}
