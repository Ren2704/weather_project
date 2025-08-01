package renata.valkanouskaya.weather_project.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.weather.api-key}")
    private String apiKey;

    @Cacheable(value = "weatherCache", key = "{#city, #units, #lang}", unless = "#result == null")
    @Override
    public WeatherResponse getWeather(String city, @Nullable String units, @Nullable String lang) {

        log.info("Получение данных о погоде для города: {}, единицы измерения: {}, язык: {}",
            city, units, lang
        );
        try {
            LocationData location = geocodingClient.getFirstLocation(city, apiKey);
            WeatherRequest request = WeatherRequest.builder()
                .location(location)
                .units(units)
                .lang(lang)
                .apiKey(apiKey)
                .build();

            WeatherData weatherData = currentWeatherDataApiClient.getWeather(request);
            return weatherMapper.convertToWeatherResponse(weatherData, location);
        } catch (FeignException e) {
            log.error("Ошибка при получении данных о погоде для города {}: {}", city, e.getMessage());
            throw new WeatherServiceUnavailableException("Сервис погоды временно недоступен");
        } catch (Exception e) {
            log.error("Неожиданная ошибка при обработке запроса погоды для города {}: {}", city, e.getMessage());
            throw e;
        }
    }
}
