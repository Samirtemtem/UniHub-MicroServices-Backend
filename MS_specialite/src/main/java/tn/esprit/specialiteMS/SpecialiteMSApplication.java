package tn.esprit.specialiteMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class SpecialiteMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecialiteMSApplication.class, args);
    }

}
