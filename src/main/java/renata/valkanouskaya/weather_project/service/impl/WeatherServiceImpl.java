package renata.valkanouskaya.weather_project.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import renata.valkanouskaya.weather_project.client.geocoding.GeocodingApiClient;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.client.weather.CurrentWeatherDataApiClient;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.exceptions.WeatherServiceUnavailableException;
import renata.valkanouskaya.weather_project.mapper.impl.WeatherMapperImpl;
import renata.valkanouskaya.weather_project.model.WeatherRequest;
import renata.valkanouskaya.weather_project.model.WeatherResponse;
import renata.valkanouskaya.weather_project.service.WeatherService;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final GeocodingApiClient geocodingClient;
    private final CurrentWeatherDataApiClient currentWeatherDataApiClient;
    private final WeatherMapperImpl weatherMapper;

    @Override
    public WeatherResponse getWeather(String city) {
        return getWeather(city, null, null);
    }

//    @Cacheable("weatherCache")
    @Override
    public WeatherResponse getWeather(String city, @Nullable String units, @Nullable String lang) {
        try {
            log.info("Не кэш. Выполняется запрос к API для города: {}", city);
            LocationData location = geocodingClient.getFirstLocation(city);
            WeatherRequest request = WeatherRequest.builder()
                    .location(location)
                    .units(units != null ? units : "metric")
                    .lang(lang != null ? lang : "ru")
                    .build();

            WeatherData weatherData = currentWeatherDataApiClient.getWeather(request);
            return weatherMapper.convertToWeatherResponse(weatherData, location);
        } catch (FeignException e) {
            throw new WeatherServiceUnavailableException("Сервис погоды временно недоступен");
        }
    }
}
