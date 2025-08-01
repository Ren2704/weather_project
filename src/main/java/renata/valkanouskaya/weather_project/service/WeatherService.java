package renata.valkanouskaya.weather_project.service;

import org.springframework.lang.Nullable;
import renata.valkanouskaya.weather_project.model.WeatherResponse;


public interface WeatherService {

    WeatherResponse getWeather(String city, @Nullable String units, @Nullable String lang);
}
