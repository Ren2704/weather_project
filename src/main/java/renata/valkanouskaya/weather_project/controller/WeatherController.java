package renata.valkanouskaya.weather_project.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import renata.valkanouskaya.weather_project.exceptions.CityNotFoundException;
import renata.valkanouskaya.weather_project.exceptions.WeatherServiceUnavailableException;
import renata.valkanouskaya.weather_project.model.WeatherResponse;
import renata.valkanouskaya.weather_project.service.WeatherService;

@Slf4j
@Validated
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(
            @RequestParam @NotBlank @Size(min = 2, max = 100)
            @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s-]+$", message = "Недопустимые символы в названии города")
            String city,

            @RequestParam(required = false, defaultValue = "metric")
            @Pattern(regexp = "metric|imperial", message = "Допустимые значения: metric, imperial")
            String units,

            @RequestParam(required = false, defaultValue = "ru")
            @Pattern(regexp = "^[a-z]{2}$", message = "Язык должен быть 2-буквенным кодом")
            String lang) {

        log.info("Получен запрос погоды для города: {}, units: {}, lang: {}", city, units, lang);

        WeatherResponse response = weatherService.getWeather(city, units, lang);
        log.debug("Данные погоды для {}: {}", city, response);

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFound(CityNotFoundException ex) {
        log.warn("Город не найден: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(WeatherServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailable(WeatherServiceUnavailableException ex) {
        log.error("Ошибка сервиса погоды: {}", ex.getMessage());
        return ResponseEntity.status(503).body(ex.getMessage());
    }
}