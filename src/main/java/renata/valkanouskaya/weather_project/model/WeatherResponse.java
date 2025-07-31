package renata.valkanouskaya.weather_project.model;

public record WeatherResponse (
        String city,
        double temperature,
        int humidity,
        String weatherDescription,
        String icon
) { }