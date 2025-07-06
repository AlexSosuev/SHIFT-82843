package service;

import model.TransformedWeatherData;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LoadingService {
    public void saveToCsv(List<TransformedWeatherData> data, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            // Записываем заголовки
            writer.writeNext(new String[]{
                    "date", "latitude", "longitude", "elevation", "timezone", "avg_temperature_2m_24h",
                    "avg_relative_humidity_2m_24h", "avg_dew_point_2m_24h", "avg_apparent_temperature_24h",
                    "avg_temperature_80m_24h", "avg_temperature_120m_24h", "avg_wind_speed_10m_24h",
                    "avg_wind_speed_80m_24h", "avg_visibility_24h", "total_rain_24h", "total_showers_24h",
                    "total_snowfall_24h", "avg_temperature_2m_daylight", "avg_relative_humidity_2m_daylight",
                    "avg_dew_point_2m_daylight", "avg_apparent_temperature_daylight", "avg_temperature_80m_daylight",
                    "avg_temperature_120m_daylight", "avg_wind_speed_10m_daylight", "avg_wind_speed_80m_daylight",
                    "avg_visibility_daylight", "total_rain_daylight", "total_showers_daylight",
                    "total_snowfall_daylight", "wind_speed_10m_m_per_s", "wind_speed_80m_m_per_s",
                    "temperature_2m_celsius", "apparent_temperature_celsius", "temperature_80m_celsius",
                    "temperature_120m_celsius", "soil_temperature_0cm_celsius", "soil_temperature_6cm_celsius",
                    "rain_mm", "showers_mm", "snowfall_mm", "daylight_hours", "sunset_iso", "sunrise_iso"
            });

            // Записываем данные
            for (TransformedWeatherData item : data) {
                writer.writeNext(new String[]{
                        item.getDate(),
                        String.valueOf(item.getLatitude()),
                        String.valueOf(item.getLongitude()),
                        String.valueOf(item.getElevation()),
                        item.getTimezone(),
                        String.valueOf(item.getAvgTemperature2m24h()),
                        String.valueOf(item.getAvgRelativeHumidity2m24h()),
                        String.valueOf(item.getAvgDewPoint2m24h()),
                        String.valueOf(item.getAvgApparentTemperature24h()),
                        String.valueOf(item.getAvgTemperature80m24h()),
                        String.valueOf(item.getAvgTemperature120m24h()),
                        String.valueOf(item.getAvgWindSpeed10m24h()),
                        String.valueOf(item.getAvgWindSpeed80m24h()),
                        String.valueOf(item.getAvgVisibility24h()),
                        String.valueOf(item.getTotalRain24h()),
                        String.valueOf(item.getTotalShowers24h()),
                        String.valueOf(item.getTotalSnowfall24h()),
                        String.valueOf(item.getAvgTemperature2mDaylight()),
                        String.valueOf(item.getAvgRelativeHumidity2mDaylight()),
                        String.valueOf(item.getAvgDewPoint2mDaylight()),
                        String.valueOf(item.getAvgApparentTemperatureDaylight()),
                        String.valueOf(item.getAvgTemperature80mDaylight()),
                        String.valueOf(item.getAvgTemperature120mDaylight()),
                        String.valueOf(item.getAvgWindSpeed10mDaylight()),
                        String.valueOf(item.getAvgWindSpeed80mDaylight()),
                        String.valueOf(item.getAvgVisibilityDaylight()),
                        String.valueOf(item.getTotalRainDaylight()),
                        String.valueOf(item.getTotalShowersDaylight()),
                        String.valueOf(item.getTotalSnowfallDaylight()),
                        String.valueOf(item.getWindSpeed10mMps()),
                        String.valueOf(item.getWindSpeed80mMps()),
                        String.valueOf(item.getTemperature2mCelsius()),
                        String.valueOf(item.getApparentTemperatureCelsius()),
                        String.valueOf(item.getTemperature80mCelsius()),
                        String.valueOf(item.getTemperature120mCelsius()),
                        String.valueOf(item.getSoilTemperature0cmCelsius()),
                        String.valueOf(item.getSoilTemperature6cmCelsius()),
                        String.valueOf(item.getRainMm()),
                        String.valueOf(item.getShowersMm()),
                        String.valueOf(item.getSnowfallMm()),
                        String.valueOf(item.getDaylightHours()),
                        item.getSunsetIso(),
                        item.getSunriseIso()
                });
            }
        }
    }
}