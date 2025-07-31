package renata.valkanouskaya.weather_project.exceptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String message) {
        super(message);
    }
}
