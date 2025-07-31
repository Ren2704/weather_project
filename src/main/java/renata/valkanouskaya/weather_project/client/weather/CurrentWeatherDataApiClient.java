package renata.valkanouskaya.weather_project.client.weather;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.configuration.FeignConfig;
import renata.valkanouskaya.weather_project.model.WeatherRequest;

@FeignClient(name = "weatherClient", url = "https://api.openweathermap.org/data/2.5", configuration = FeignConfig.class)
public interface CurrentWeatherDataApiClient {
    @GetMapping("/weather")
    WeatherData getWeatherByCoordinates(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("appid") String apiKey,
            @RequestParam("units") String units,
            @RequestParam("lang") String lang
    );

    default WeatherData getWeather(WeatherRequest request) {
        return getWeatherByCoordinates(
                request.getLocation().getLat(),
                request.getLocation().getLon(),
                "${app.weather.api-key}",
                request.getUnits(),
                request.getLang()
        );
    }
}
