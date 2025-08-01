package renata.valkanouskaya.weather_project.exceptions;

public class CityNotFoundException extends RuntimeException{

  public CityNotFoundException() {
    super("Город не найден");
  }

    public CityNotFoundException(String message) {
        super(message);
    }
}
