package org.arqui.microserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.arqui.microserviceuser.feignClients")
public class MicroserviceUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceUserApplication.class, args);
  }

}
