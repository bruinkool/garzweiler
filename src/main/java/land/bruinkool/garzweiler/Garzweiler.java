package land.bruinkool.garzweiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"land.bruinkool.garzweiler.entity"})
public class Garzweiler {
    public static void main(String[] args) {
        SpringApplication.run(Garzweiler.class, args);
    }
}
