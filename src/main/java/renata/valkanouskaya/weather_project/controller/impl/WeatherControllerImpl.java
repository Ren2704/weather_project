package renata.valkanouskaya.weather_project.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import renata.valkanouskaya.weather_project.controller.WeatherController;
import renata.valkanouskaya.weather_project.model.WeatherResponse;
import renata.valkanouskaya.weather_project.service.WeatherService;

@Slf4j
@Validated
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WeatherControllerImpl implements WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(String city, String units, String lang) {
        log.info("Получен запрос погоды для города: {}, units: {}, lang: {}", city, units, lang);
        WeatherResponse response = weatherService.getWeather(city, units, lang);
        log.debug("Данные погоды для {}: {}", city, response);
        return ResponseEntity.ok(response);
    }
}