package gr.aueb.cf.schoolappssr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolappssrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolappssrApplication.class, args);
	}

}
