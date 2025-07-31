package renata.valkanouskaya.weather_project.client.geocoding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import renata.valkanouskaya.weather_project.client.geocoding.dto.LocationData;
import renata.valkanouskaya.weather_project.configuration.FeignConfig;
import renata.valkanouskaya.weather_project.exceptions.CityNotFoundException;

import java.util.List;

@FeignClient(name = "geocodingClient", url = "https://api.openweathermap.org/geo/1.0", configuration = FeignConfig.class)
public interface GeocodingApiClient {
    @GetMapping("/direct")
    List<LocationData> getLocations(
            @RequestParam("q") String location,
            @RequestParam(value = "limit", defaultValue = "1") int limit,
            @RequestParam("appid") String apiKey
    );

    default LocationData getFirstLocation(String city) {
        List<LocationData> locations = getLocations(city, 1, "${app.weather.api-key}");
        if (locations.isEmpty()) {
            throw new CityNotFoundException("Город не найден: " + city);
        }
        return locations.get(0);
    }
}
