package renata.valkanouskaya.weather_project.client.weather.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherData {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private String name;

    @Data
    @Builder
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    @Builder
    public static class Weather {
        private String description;
        private String icon;
    }

    @Data
    @Builder
    public static class Main {
        private double temp;
        private int humidity;
    }
}
