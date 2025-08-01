package renata.valkanouskaya.weather_project.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import renata.valkanouskaya.weather_project.controller.impl.WeatherControllerImpl;
import renata.valkanouskaya.weather_project.exceptions.CityNotFoundException;
import renata.valkanouskaya.weather_project.exceptions.GlobalExceptionHandler;
import renata.valkanouskaya.weather_project.model.WeatherResponse;
import renata.valkanouskaya.weather_project.service.WeatherService;

class WeatherControllerTest {

  private final WeatherService weatherService = mock();
  private final WeatherController weatherController = new WeatherControllerImpl(weatherService);
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(weatherController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  void getWeather_shouldReturnOk() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    WeatherResponse response = WeatherResponse.builder()
        .city("Grodno")
        .temperature(13.87)
        .humidity(89)
        .weatherDescription("переменная облачность")
        .icon("03n")
        .build();
    when(weatherService.getWeather("Grodno", "metric", "ru")).thenReturn(response);

    mockMvc.perform(get("/weather")
            .param("city", "Grodno")
            .param("units", "metric")
            .param("lang", "ru")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
  }

  @Test
  void getWeather_cityNotFound_shouldReturn404() throws Exception {
    when(weatherService.getWeather("UnknownCity", "metric", "ru"))
        .thenThrow(new CityNotFoundException());

    mockMvc.perform(get("/weather")
            .param("city", "UnknownCity")
            .param("units", "metric")
            .param("lang", "ru")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}