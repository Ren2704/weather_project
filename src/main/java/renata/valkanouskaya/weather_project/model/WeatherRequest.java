package renata.valkanouskaya.weather_project.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;

@Data
@Builder
@With
public class WeatherRequest {
    private final LocationData location;

    @Builder.Default
    private final String units = "metric";

    @Builder.Default
    private final String lang = "ru";
}
