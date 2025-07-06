package service;

import model.WeatherData;
import model.TransformedWeatherData;
import util.DateUtil;
import util.UnitConverter;
import java.util.ArrayList;
import java.util.List;

public class TransformationService {
    public List<TransformedWeatherData> transform(WeatherData response) {
        List<TransformedWeatherData> result = new ArrayList<>();

        WeatherData.HourlyData hourly = response.getHourly();
        WeatherData.DailyData daily = response.getDaily();

        int daysCount = daily.getTime().size();

        for (int dayIdx = 0; dayIdx < daysCount; dayIdx++) {
            TransformedWeatherData data = new TransformedWeatherData();

            // Установка базовых полей
            setBasicFields(data, response);

            // Обработка временных меток
            processTimestamps(data, daily, dayIdx, response.getTimezone());

            // Расчет 24-часовых средних
            calculate24hAverages(data, hourly, dayIdx);

            // Расчет дневных показателей
            calculateDaylightAverages(data, hourly, daily, dayIdx);

            // Конвертация единиц измерения
            convertUnits(data, hourly, dayIdx);

            result.add(data);
        }

        return result;
    }

    private void setBasicFields(TransformedWeatherData data, WeatherData response) {
        data.setLatitude(response.getLatitude());
        data.setLongitude(response.getLongitude());
        data.setElevation(response.getElevation());
        data.setTimezone(response.getTimezone());
    }

    private void processTimestamps(TransformedWeatherData data,
                                   WeatherData.DailyData daily,
                                   int dayIdx,
                                   String timezone) {
        long unixTime = daily.getTime().get(dayIdx);
        data.setDate(DateUtil.unixToIso(unixTime, timezone));

        long sunrise = daily.getSunrise().get(dayIdx);
        long sunset = daily.getSunset().get(dayIdx);
        data.setSunriseIso(DateUtil.unixToIso(sunrise, timezone));
        data.setSunsetIso(DateUtil.unixToIso(sunset, timezone));
        data.setDaylightHours((sunset - sunrise) / 3600.0);
    }

    private void calculate24hAverages(TransformedWeatherData data,
                                      WeatherData.HourlyData hourly,
                                      int dayIdx) {
        int startIdx = dayIdx * 24;
        int endIdx = startIdx + 24;

        // Температура на 2м
        data.setAvgTemperature2m24h(calculateAverage(
                hourly.getTemperature_2m().subList(startIdx, endIdx),
                UnitConverter::fahrenheitToCelsius));

        // Относительная влажность
        data.setAvgRelativeHumidity2m24h(calculateAverage(
                hourly.getRelative_humidity_2m().subList(startIdx, endIdx)));

        // Точка росы
        data.setAvgDewPoint2m24h(calculateAverage(
                hourly.getDew_point_2m().subList(startIdx, endIdx),
                UnitConverter::fahrenheitToCelsius));

        // Ощущаемая температура
        data.setAvgApparentTemperature24h(calculateAverage(
                hourly.getApparent_temperature().subList(startIdx, endIdx),
                UnitConverter::fahrenheitToCelsius));

        // Температура на 80м
        data.setAvgTemperature80m24h(calculateAverage(
                hourly.getTemperature_80m().subList(startIdx, endIdx),
                UnitConverter::fahrenheitToCelsius));

        // Температура на 120м
        data.setAvgTemperature120m24h(calculateAverage(
                hourly.getTemperature_120m().subList(startIdx, endIdx),
                UnitConverter::fahrenheitToCelsius));

        // Скорость ветра на 10м
        data.setAvgWindSpeed10m24h(calculateAverage(
                hourly.getWind_speed_10m().subList(startIdx, endIdx),
                UnitConverter::knotsToMps));

        // Скорость ветра на 80м
        data.setAvgWindSpeed80m24h(calculateAverage(
                hourly.getWind_speed_80m().subList(startIdx, endIdx),
                UnitConverter::knotsToMps));

        // Видимость
        data.setAvgVisibility24h(calculateAverage(
                hourly.getVisibility().subList(startIdx, endIdx),
                UnitConverter::feetToMeters));

        // Сумма осадков
        data.setTotalRain24h(calculateSum(
                hourly.getRain().subList(startIdx, endIdx),
                UnitConverter::inchesToMm));

        data.setTotalShowers24h(calculateSum(
                hourly.getShowers().subList(startIdx, endIdx),
                UnitConverter::inchesToMm));

        data.setTotalSnowfall24h(calculateSum(
                hourly.getSnowfall().subList(startIdx, endIdx),
                UnitConverter::inchesToMm));
    }

    private void calculateDaylightAverages(TransformedWeatherData data,
                                           WeatherData.HourlyData hourly,
                                           WeatherData.DailyData daily,
                                           int dayIdx) {
        long sunrise = daily.getSunrise().get(dayIdx);
        long sunset = daily.getSunset().get(dayIdx);

        List<Integer> daylightIndices = new ArrayList<>();
        for (int i = 0; i < hourly.getTime().size(); i++) {
            long time = hourly.getTime().get(i);
            if (time >= sunrise && time < sunset) {
                daylightIndices.add(i);
            }
        }

        if (daylightIndices.isEmpty()) {
            return;
        }

        // Температура на 2м (дневное время)
        data.setAvgTemperature2mDaylight(calculateAverageForIndices(
                hourly.getTemperature_2m(), daylightIndices,
                UnitConverter::fahrenheitToCelsius));

        // Относительная влажность
        data.setAvgRelativeHumidity2mDaylight(calculateAverageForIndices(
                hourly.getRelative_humidity_2m(), daylightIndices));

        // Точка росы
        data.setAvgDewPoint2mDaylight(calculateAverageForIndices(
                hourly.getDew_point_2m(), daylightIndices,
                UnitConverter::fahrenheitToCelsius));

        // Ощущаемая температура
        data.setAvgApparentTemperatureDaylight(calculateAverageForIndices(
                hourly.getApparent_temperature(), daylightIndices,
                UnitConverter::fahrenheitToCelsius));

        // Температура на 80м
        data.setAvgTemperature80mDaylight(calculateAverageForIndices(
                hourly.getTemperature_80m(), daylightIndices,
                UnitConverter::fahrenheitToCelsius));

        // Температура на 120м
        data.setAvgTemperature120mDaylight(calculateAverageForIndices(
                hourly.getTemperature_120m(), daylightIndices,
                UnitConverter::fahrenheitToCelsius));

        // Скорость ветра на 10м
        data.setAvgWindSpeed10mDaylight(calculateAverageForIndices(
                hourly.getWind_speed_10m(), daylightIndices,
                UnitConverter::knotsToMps));

        // Скорость ветра на 80м
        data.setAvgWindSpeed80mDaylight(calculateAverageForIndices(
                hourly.getWind_speed_80m(), daylightIndices,
                UnitConverter::knotsToMps));

        // Видимость
        data.setAvgVisibilityDaylight(calculateAverageForIndices(
                hourly.getVisibility(), daylightIndices,
                UnitConverter::feetToMeters));

        // Сумма осадков
        data.setTotalRainDaylight(calculateSumForIndices(
                hourly.getRain(), daylightIndices,
                UnitConverter::inchesToMm));

        data.setTotalShowersDaylight(calculateSumForIndices(
                hourly.getShowers(), daylightIndices,
                UnitConverter::inchesToMm));

        data.setTotalSnowfallDaylight(calculateSumForIndices(
                hourly.getSnowfall(), daylightIndices,
                UnitConverter::inchesToMm));
    }

    private void convertUnits(TransformedWeatherData data,
                              WeatherData.HourlyData hourly,
                              int dayIdx) {
        int hourIdx = dayIdx * 24;

        // Конвертация скорости ветра
        data.setWindSpeed10mMps(UnitConverter.knotsToMps(
                hourly.getWind_speed_10m().get(hourIdx)));
        data.setWindSpeed80mMps(UnitConverter.knotsToMps(
                hourly.getWind_speed_80m().get(hourIdx)));

        // Конвертация температуры
        data.setTemperature2mCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getTemperature_2m().get(hourIdx)));
        data.setApparentTemperatureCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getApparent_temperature().get(hourIdx)));
        data.setTemperature80mCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getTemperature_80m().get(hourIdx)));
        data.setTemperature120mCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getTemperature_120m().get(hourIdx)));

        // Температура почвы
        data.setSoilTemperature0cmCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getSoil_temperature_0cm().get(hourIdx)));
        data.setSoilTemperature6cmCelsius(UnitConverter.fahrenheitToCelsius(
                hourly.getSoil_temperature_6cm().get(hourIdx)));

        // Осадки
        data.setRainMm(UnitConverter.inchesToMm(
                hourly.getRain().get(hourIdx)));
        data.setShowersMm(UnitConverter.inchesToMm(
                hourly.getShowers().get(hourIdx)));
        data.setSnowfallMm(UnitConverter.inchesToMm(
                hourly.getSnowfall().get(hourIdx)));
    }

    private double calculateAverage(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    private double calculateAverage(List<Double> values,
                                    java.util.function.Function<Double, Double> converter) {
        return values.stream()
                .mapToDouble(converter::apply)
                .average().orElse(0);
    }

    private double calculateAverageForIndices(List<Double> values,
                                              List<Integer> indices) {
        return indices.stream()
                .mapToDouble(values::get)
                .average().orElse(0);
    }

    private double calculateAverageForIndices(List<Double> values,
                                              List<Integer> indices,
                                              java.util.function.Function<Double, Double> converter) {
        return indices.stream()
                .mapToDouble(i -> converter.apply(values.get(i)))
                .average().orElse(0);
    }

    private double calculateSum(List<Double> values,
                                java.util.function.Function<Double, Double> converter) {
        return values.stream()
                .mapToDouble(converter::apply)
                .sum();
    }

    private double calculateSumForIndices(List<Double> values,
                                          List<Integer> indices,
                                          java.util.function.Function<Double, Double> converter) {
        return indices.stream()
                .mapToDouble(i -> converter.apply(values.get(i)))
                .sum();
    }
}
