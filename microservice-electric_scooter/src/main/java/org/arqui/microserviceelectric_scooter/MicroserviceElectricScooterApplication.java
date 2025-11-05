package org.arqui.microserviceelectric_scooter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.arqui.microserviceelectric_scooter.feignClient") // 👈 importante

public class MicroserviceElectricScooterApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceElectricScooterApplication.class, args);
  }

}




