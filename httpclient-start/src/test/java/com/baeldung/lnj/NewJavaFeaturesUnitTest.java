package com.baeldung.lnj;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewJavaFeaturesUnitTest {

    @Test
    void whenRetrievingData_thenParametersAreIncludedInResponse() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/get?param=value")).build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("param=value"));
    }

    @Test
    void whenRetrievingData_thenResponseIsHandledAsynchronously() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/get")).build();

        // use sendAsync instead of send method
        CompletableFuture<HttpResponse<String>> futureResponse =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        CompletableFuture<String> futureBody = futureResponse.thenApply(HttpResponse::body);

        CompletableFuture<Void> assertionFuture = futureBody
                .thenAccept(body -> assertTrue(body.contains("\"host\":\"postman-echo.com\"")));

        // wait for asynchronous pipeline to complete to make sure test doesn't finish before assertion are run
        assertionFuture.join();
    }

    @Test
    void whenCreatingTask_thenTaskIsCreatedSuccessfully() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String jsonBody = "{\"code\":\"task-1\", \"name\":\"Test Task\", \"description\":\"Test POST\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                //.method("POST", HttpRequest.BodyPublishers.ofString(jsonBody)) // works the same
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Test POST"));
    }

    /**
     * <a href="https://docs.oracle.com/en/java/javase/23/docs/api/java.net.http/java/net/http/HttpClient.Builder.html">
     *     Official doc for HttpClient config options</a>
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void configure_httpClient() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(50))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        // Timeout threshold is too small
        assertThrows(HttpConnectTimeoutException.class, () -> client.send(
                HttpRequest.newBuilder().uri(URI.create("https://postman-echo.com/get")).build(),
                HttpResponse.BodyHandlers.discarding())
        );
    }
}