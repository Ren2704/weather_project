package renata.valkanouskaya.weather_project.model;

import lombok.Builder;

@Builder
public record WeatherResponse (
    String city,
    double temperature,
    int humidity,
    String weatherDescription,
    String icon
) { }