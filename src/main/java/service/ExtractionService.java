package service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.WeatherData;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExtractionService {
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=55.0344&longitude=82.9434&" +
            "daily=sunrise,sunset,daylight_duration&hourly=temperature_2m,relative_humidity_2m,dew_point_2m," +
            "apparent_temperature,temperature_80m,temperature_120m,wind_speed_10m,wind_speed_80m,wind_direction_10m," +
            "wind_direction_80m,visibility,evapotranspiration,weather_code,soil_temperature_0cm,soil_temperature_6cm," +
            "rain,showers,snowfall&timezone=auto&timeformat=unixtime&wind_speed_unit=kn&temperature_unit=fahrenheit&"+
            "precipitation_unit=inch&start_date=2025-05-16&end_date=2025-05-30";

    public WeatherData fetchWeatherData() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        // Настраиваем ObjectMapper для игнорирования неизвестных свойств
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(response.body(), WeatherData.class);
    }
}