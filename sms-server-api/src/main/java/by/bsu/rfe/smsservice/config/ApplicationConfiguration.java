package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public SpringContextHolder contextHolder() {
    return new SpringContextHolder();
  }

  @Bean
  public Mapper dozerMapper() {
    return new DozerBeanMapper();
  }
}
