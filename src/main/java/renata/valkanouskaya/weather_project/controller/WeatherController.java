package renata.valkanouskaya.weather_project.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import renata.valkanouskaya.weather_project.model.WeatherResponse;

@RequestMapping("/weather")
public interface WeatherController {

  @GetMapping
  ResponseEntity<WeatherResponse> getWeather(
      @RequestParam @NotBlank @Size(min = 2, max = 100)
      @Pattern(
          regexp = "^[a-zA-Zа-яА-Я\\s-]+$",
          message = "Недопустимые символы в названии города"
      )
      String city,

      @RequestParam(required = false, defaultValue = "metric")
      @Pattern(
          regexp = "metric|imperial",
          message = "Допустимые значения: metric, imperial"
      )
      String units,

      @RequestParam(required = false, defaultValue = "ru")
      @Pattern(
          regexp = "^[a-z]{2}$",
          message = "Язык должен быть 2-буквенным кодом"
      )
      String lang);
}