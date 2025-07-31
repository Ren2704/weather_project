package renata.valkanouskaya.weather_project.client.geocoding.dto;

import lombok.Data;

@Data
public class LocationData {
    private String name;
    private double lat;
    private double lon;
    private String country;
}
