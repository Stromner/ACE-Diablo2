package ace.diablo.two;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AceDiabloTwo {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AceDiabloTwo.class);
        builder.headless(false);
        builder.run(args);
    }
}
