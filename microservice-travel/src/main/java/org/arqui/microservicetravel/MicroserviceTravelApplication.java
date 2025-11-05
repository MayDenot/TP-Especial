package org.arqui.microservicetravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.arqui.microservicetravel.feignClient")
public class MicroserviceTravelApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceTravelApplication.class, args);
  }

}
