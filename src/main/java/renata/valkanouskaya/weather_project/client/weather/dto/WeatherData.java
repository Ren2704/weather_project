package renata.valkanouskaya.weather_project.client.weather.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherData {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private String name;

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    public static class Weather {
        private String description;
        private String icon;
    }

    @Data
    public static class Main {
        private double temp;
        private int humidity;
    }
}
