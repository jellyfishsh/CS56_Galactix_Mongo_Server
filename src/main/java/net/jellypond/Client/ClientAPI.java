package net.jellypond.Client;


import net.jellypond.Server.ScoreObj;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.conversions.Bson;

import javax.swing.text.Document;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientAPI {
    private static final String apiaddress = "https://galactixapi.jellypond.net";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();;

    public static ScoreObj getScore(String username){
        String result = "None";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiaddress + "/score?username=" + username))
                .setHeader("User-Agent", "Galactix Client")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        ScoreObj score = ScoreObj.fromJson(result);

        return score;
    }

    public static List<ScoreObj> getScoreboard(){
        String result = "not";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiaddress + "/scoreboard"))
                .setHeader("User-Agent", "Galactix Client")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (Exception e){
            e.printStackTrace();
        }


        return ScoreObj.fromJsonArray(result);
    }
    public static List<ScoreObj> getScoreboard(int entries){
        String result = "not";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiaddress + "/scoreboard?entries" + entries))
                .setHeader("User-Agent", "Galactix Client")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (Exception e){
            e.printStackTrace();
        }


        return ScoreObj.fromJsonArray(result);
    }

    public static String postScore(ScoreObj score){
        String jsonscore = score.toDocument().toJson();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonscore))
                .uri(URI.create(apiaddress + "/upload"))
                .setHeader("User-Agent", "Galactix Client")
                .header("Content-Type", "application/json")
                .build();


        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e){
            e.printStackTrace();
        }

        return "Failure.";

    }





}
