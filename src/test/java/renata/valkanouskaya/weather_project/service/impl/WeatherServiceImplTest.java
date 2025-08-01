package renata.valkanouskaya.weather_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import feign.FeignException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import renata.valkanouskaya.weather_project.client.geocoding.GeocodingApiClient;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.client.weather.CurrentWeatherDataApiClient;
import renata.valkanouskaya.weather_project.client.weather.dto.WeatherData;
import renata.valkanouskaya.weather_project.exceptions.WeatherServiceUnavailableException;
import renata.valkanouskaya.weather_project.mapper.impl.WeatherMapperImpl;
import renata.valkanouskaya.weather_project.model.WeatherRequest;
import renata.valkanouskaya.weather_project.model.WeatherResponse;
import renata.valkanouskaya.weather_project.service.WeatherService;

class WeatherServiceImplTest {

  private final GeocodingApiClient geocodingClient = mock();
  private final CurrentWeatherDataApiClient currentWeatherDataApiClient = mock();
  private final WeatherMapperImpl weatherMapper = mock();
  private final WeatherService weatherService = new WeatherServiceImpl(
      geocodingClient, currentWeatherDataApiClient, weatherMapper
  );

  private final String city = "Grodno";
  private final String apiKey = "test-api-key";

  @Test
  void getWeather_shouldReturnWeatherResponse_success() {
    ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);

    LocationData locationData = LocationData.builder()
        .name(city)
        .lat(53.9)
        .lon(27.56)
        .country("BY")
        .build();

    WeatherRequest expectedRequest = WeatherRequest.builder()
        .location(locationData)
        .units("metric")
        .lang("ru")
        .apiKey(apiKey)
        .build();

    WeatherData.Coord coord = WeatherData.Coord.builder()
        .lon(27.56)
        .lat(53.9)
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
        .coord(coord)
        .weather(List.of(weather))
        .main(main)
        .name(city)
        .build();

    WeatherResponse expectedResponse = new WeatherResponse(city, 15.5, 75, "ясно", "01d");

    when(geocodingClient.getFirstLocation(city, apiKey)).thenReturn(locationData);
    when(currentWeatherDataApiClient.getWeather(expectedRequest)).thenReturn(weatherData);
    when(weatherMapper.convertToWeatherResponse(weatherData, locationData)).thenReturn(expectedResponse);

    WeatherResponse actualResponse = weatherService.getWeather(city, "metric", "ru");

    assertNotNull(actualResponse);
    assertEquals(expectedResponse, actualResponse);
    verify(geocodingClient).getFirstLocation(city, apiKey);
    verify(currentWeatherDataApiClient).getWeather(expectedRequest);
    verify(weatherMapper).convertToWeatherResponse(weatherData, locationData);
  }

  @Test
  void getWeather_shouldThrowWeatherServiceUnavailableException_whenFeignException() {
    ReflectionTestUtils.setField(weatherService, "apiKey", apiKey);
    FeignException feignException = mock(FeignException.class);

    when(geocodingClient.getFirstLocation(city, apiKey)).thenThrow(feignException);

    assertThrows(WeatherServiceUnavailableException.class,
        () -> weatherService.getWeather(city, "metric", "ru"));

    verify(geocodingClient).getFirstLocation(city, apiKey);
    verify(currentWeatherDataApiClient, never()).getWeather(any());
  }
}