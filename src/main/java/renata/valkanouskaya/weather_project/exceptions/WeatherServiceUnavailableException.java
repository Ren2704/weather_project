package renata.valkanouskaya.weather_project.exceptions;

public class WeatherServiceUnavailableException extends RuntimeException {
    public WeatherServiceUnavailableException(String message) {
        super(message);
    }
}
