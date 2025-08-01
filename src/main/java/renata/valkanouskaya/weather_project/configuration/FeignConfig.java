package renata.valkanouskaya.weather_project.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "renata.valkanouskaya.weather_project.client")
public class FeignConfig {
}
