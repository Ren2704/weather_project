package renata.valkanouskaya.weather_project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.mapper.WeatherMapper;
import renata.valkanouskaya.weather_project.model.WeatherResponse;

class WeatherMapperImplTest {

  private final WeatherMapper weatherMapper = new WeatherMapperImpl();

  @Test
  void convertToWeatherResponse_shouldMapCorrectly() {
    LocationData locationData = LocationData.builder()
        .name("Grodno")
        .build();

    WeatherData.Weather weather = WeatherData.Weather.builder()
        .description("ясно")
        .icon("01d")
        .build();

    WeatherData.Main main = WeatherData.Main.builder()
        .temp(15.5)
        .humidity(75)
        .build();

    WeatherData weatherData = WeatherData.builder()
        .weather(List.of(weather))
        .main(main)
        .build();

    WeatherResponse result = weatherMapper.convertToWeatherResponse(weatherData, locationData);

    assertNotNull(result);
    assertEquals("Grodno", result.city());
    assertEquals(15.5, result.temperature());
    assertEquals(75, result.humidity());
    assertEquals("ясно", result.weatherDescription());
    assertEquals("01d", result.icon());
  }
}