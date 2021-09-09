package com.lz.provider;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpUtil {

    private final static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static HttpResponse<?> doPost(String httpUrl, Object obj) {
        String json = new Gson().toJson(obj);

        HttpRequest.BodyPublisher requestBody = HttpRequest.BodyPublishers
                .ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpUrl))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .POST(requestBody)
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}