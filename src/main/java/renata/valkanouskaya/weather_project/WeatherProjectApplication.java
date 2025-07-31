package renata.valkanouskaya.weather_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherProjectApplication.class, args);
	}

}
