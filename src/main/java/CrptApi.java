import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * Класс CrptApi отправляет JsonObject POST-запросом на указанный в задании URL,
 * и выводит в консоль ответ сервера
 *
 * @author Булат Салихов
 * @since 02.03.2024
 */

public class CrptApi {

    // Переменная rateLimiter класса RateLimiter из библиотеки Guava нужна для ограничения кол-ва вызовов метода sendRequest в секунду
    private final RateLimiter rateLimiter;

    // В конструктор передается TimeUnit и int requestLimit для создания экземпляр rateLimiter и вычисления максимально допустимого кол-ва запросов в секунду
    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.rateLimiter = RateLimiter.create((double) requestLimit / timeUnit.toSeconds(1));
    }

    public void sendRequest(JsonObject document, String signature) {

        // вызывается метод acquire() для соблюдения установленного лимита
        rateLimiter.acquire();

        // Запросы отправляем с использованием HttpURLConnection
        try {
            URL url = new URL("https://ismp.crpt.ru/api/v3/lk/documents/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", signature);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(new Gson().toJson(document).getBytes(StandardCharsets.UTF_8));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(bufferedReader.readLine());

            connection.disconnect();
            outputStream.close();
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
