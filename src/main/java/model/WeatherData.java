package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем неизвестные поля
public class WeatherData {
    private double latitude;
    private double longitude;
    private double elevation;
    private String timezone;

    // Добавляем новые поля из API
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone_abbreviation;

    @JsonProperty("hourly")
    private HourlyData hourly;

    @JsonProperty("daily")
    private DailyData daily;

    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;

    @JsonProperty("daily_units")
    private DailyUnits dailyUnits;

    @Data
    public static class HourlyData {
        private List<Long> time;
        private List<Double> temperature_2m;
        private List<Double> relative_humidity_2m;
        private List<Double> dew_point_2m;
        private List<Double> apparent_temperature;
        private List<Double> temperature_80m;
        private List<Double> temperature_120m;
        private List<Double> wind_speed_10m;
        private List<Double> wind_speed_80m;
        private List<Integer> wind_direction_10m;
        private List<Integer> wind_direction_80m;
        private List<Double> visibility;
        private List<Double> evapotranspiration;
        private List<Integer> weather_code;
        private List<Double> soil_temperature_0cm;
        private List<Double> soil_temperature_6cm;
        private List<Double> rain;
        private List<Double> showers;
        private List<Double> snowfall;
    }

    @Data
    public static class DailyData {
        private List<Long> time;
        private List<Long> sunrise;
        private List<Long> sunset;
        private List<Double> daylight_duration;
    }

    @Data
    public static class HourlyUnits {
        private String time;
        private String temperature_2m;
        private String relative_humidity_2m;
        private String dew_point_2m;
        private String apparent_temperature;
        private String temperature_80m;
        private String temperature_120m;
        private String wind_speed_10m;
        private String wind_speed_80m;
        private String wind_direction_10m;
        private String wind_direction_80m;
        private String visibility;
        private String evapotranspiration;
        private String weather_code;
        private String soil_temperature_0cm;
        private String soil_temperature_6cm;
        private String rain;
        private String showers;
        private String snowfall;
    }

    @Data
    public static class DailyUnits {
        private String time;
        private String sunrise;
        private String sunset;
        private String daylight_duration;
    }
}