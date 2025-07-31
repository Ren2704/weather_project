package renata.valkanouskaya.weather_project.mapper;

import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.model.WeatherResponse;

public interface WeatherMapper {
    WeatherResponse convertToWeatherResponse(WeatherData weatherData, LocationData location);
}
