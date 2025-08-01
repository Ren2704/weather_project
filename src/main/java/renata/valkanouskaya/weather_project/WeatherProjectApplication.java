package renata.valkanouskaya.weather_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WeatherProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherProjectApplication.class, args);
	}

}
