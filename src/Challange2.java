import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Challange2 {
    public static void main(String[] args) throws Exception{
        var apiKey = "<your key>";
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
                .GET()
                .build();

        var client = HttpClient.newHttpClient();

        var response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        var json = response.body();
        var moviesArray = parseJsonMovies(json);

        var titles = parseTitles(moviesArray);
        titles.forEach(System.out::println);

        var urlImages = parseUrlImages(moviesArray);
        urlImages.forEach(System.out::println);

        var ratings = parseRating(moviesArray);
        ratings.forEach(System.out::println);

        var years = parseYears(moviesArray);
        years.forEach(System.out::println);
    }

    private static String[] parseJsonMovies(String json) {
        json = json.replace("{\"items\":[", "").replace("],\"errorMessage\":\"\"}", "");
        return json.replace("{", "").split("},");
    }

    private static List<String> parseTitles(String[] moviesArray) {
        var titles = new ArrayList<String>();
        for (var movie: moviesArray) {
            var attributes = movie.split("\",");
            var titleField = attributes[2];
            titles.add(titleField.substring(9));
        }
        return titles;
    }

    private static List<String> parseUrlImages(String[] moviesArray) {
        var urlImages = new ArrayList<String>();
        for (var movie: moviesArray) {
            var attributes = movie.split("\",");
            var urlImageField = attributes[5];
            urlImages.add(urlImageField.substring(9));
        }
        return urlImages;
    }

    private static List<String> parseRating(String[] moviesArray) {
        var ratings = new ArrayList<String>();
        for (var movie: moviesArray) {
            var attributes = movie.split("\",");
            var ratingField = attributes[7];
            ratings.add(ratingField.substring(14));
        }
        return ratings;
    }

    private static List<String> parseYears(String[] moviesArray) {
        var years = new ArrayList<String>();
        for (var movie: moviesArray) {
            var attributes = movie.split("\",");
            var yearField = attributes[4];
            years.add(yearField.substring(8));
        }
        return years;
    }
}
