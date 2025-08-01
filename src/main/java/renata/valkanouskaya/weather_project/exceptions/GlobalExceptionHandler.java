package renata.valkanouskaya.weather_project.exceptions;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
    String errorMessage = ex.getConstraintViolations().stream()
        .map(violation ->
            String.format("%s: %s",
                violation.getPropertyPath().toString(),
                violation.getMessage()))
        .collect(Collectors.joining("; "));

    log.warn("Ошибка валидации: {}", errorMessage);
    return ResponseEntity.status(400).body(errorMessage);
  }
}