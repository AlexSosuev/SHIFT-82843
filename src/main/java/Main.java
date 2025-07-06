import model.WeatherData;
import model.TransformedWeatherData;
import service.ExtractionService;
import service.TransformationService;
import service.LoadingService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Извлечение данных
            ExtractionService extractor = new ExtractionService();
            WeatherData response = extractor.fetchWeatherData();

            // 2. Преобразование данных
            TransformationService transformer = new TransformationService();
            List<TransformedWeatherData> transformedData = transformer.transform(response);

            // 3. Загрузка данных
            LoadingService loader = new LoadingService();
            loader.saveToCsv(transformedData, "src/main/resources/weather_data.csv");

            System.out.println("Данные сохранены в weather_data.csv");
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении ETL-процесса: " + e.getMessage());
        }
    }
}