package renata.valkanouskaya.weather_project.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.mapper.WeatherMapper;
import renata.valkanouskaya.weather_project.model.WeatherResponse;

@Component
@RequiredArgsConstructor
public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public WeatherResponse convertToWeatherResponse(WeatherData weatherData, LocationData location) {
        return new WeatherResponse(
                location.getName(),
                weatherData.getMain().getTemp(),
                weatherData.getMain().getHumidity(),
                weatherData.getWeather().get(0).getDescription(),
                weatherData.getWeather().get(0).getIcon()
        );
    }
}