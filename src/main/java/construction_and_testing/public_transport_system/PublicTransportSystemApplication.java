package construction_and_testing.public_transport_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PublicTransportSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicTransportSystemApplication.class, args);
    }
}
