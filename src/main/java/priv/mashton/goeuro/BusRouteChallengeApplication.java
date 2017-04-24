package priv.mashton.goeuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class BusRouteChallengeApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BusRouteChallengeApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
