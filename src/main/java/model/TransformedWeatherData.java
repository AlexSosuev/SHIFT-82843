package model;

import lombok.Data;

@Data
public class TransformedWeatherData {
    // Основная информация
    private String date;
    private double latitude;
    private double longitude;
    private double elevation;
    private String timezone;

    // 24-часовые средние
    private double avgTemperature2m24h;
    private double avgRelativeHumidity2m24h;
    private double avgDewPoint2m24h;
    private double avgApparentTemperature24h;
    private double avgTemperature80m24h;
    private double avgTemperature120m24h;
    private double avgWindSpeed10m24h;
    private double avgWindSpeed80m24h;
    private double avgVisibility24h;
    private double totalRain24h;
    private double totalShowers24h;
    private double totalSnowfall24h;

    // Дневные показатели
    private double avgTemperature2mDaylight;
    private double avgRelativeHumidity2mDaylight;
    private double avgDewPoint2mDaylight;
    private double avgApparentTemperatureDaylight;
    private double avgTemperature80mDaylight;
    private double avgTemperature120mDaylight;
    private double avgWindSpeed10mDaylight;
    private double avgWindSpeed80mDaylight;
    private double avgVisibilityDaylight;
    private double totalRainDaylight;
    private double totalShowersDaylight;
    private double totalSnowfallDaylight;

    // Конвертированные единицы
    private double windSpeed10mMps;
    private double windSpeed80mMps;
    private double temperature2mCelsius;
    private double apparentTemperatureCelsius;
    private double temperature80mCelsius;
    private double temperature120mCelsius;
    private double soilTemperature0cmCelsius;
    private double soilTemperature6cmCelsius;
    private double rainMm;
    private double showersMm;
    private double snowfallMm;

    // Информация о световом дне
    private double daylightHours;
    private String sunsetIso;
    private String sunriseIso;
}
