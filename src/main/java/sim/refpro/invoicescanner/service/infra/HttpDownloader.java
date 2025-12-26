package sim.refpro.invoicescanner.service.infra;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.springframework.stereotype.Component;

/**
 * <h2>HTTP Downloader</h2>
 * 
 * <p>
 * HTTP client component responsible for downloading resources from a given URL. This component is 
 * used by the invoice scanning workflow to download PDF documents from public URLs.
 * </p>
 *
 * @author Simone Njike
 * @version 1.1
 * @since 01.12.2025
 */
@Component
public class HttpDownloader {
    
    private final HttpClient httpClient;

    /**
     * Creates a new HttpDownloader using Java's built-in HttpClient.
     * A simple connect and request timeout is configured for robustness.
     */
    public HttpDownloader() {
        
        this.httpClient =  HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }
    
    /**
     * Downloads the content from the given URL as a byte array.
     *
     * @param url The URL to download from. Must be a valid absolute URL.
     * @return The response body as a byte array.
     * @throws IllegalArgumentException if the URL is null, blank, or invalid.
     * @throws IllegalStateException    if the HTTP request fails or returns a non-success status code.
     */
    public byte[] download(String url) {
        
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL must not be null or blank");
        }

        HttpRequest request;
        
        try {
            request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10))
                    .GET().build();
        } 
        catch (IllegalArgumentException ex) {
            // URI.create may throw IllegalArgumentException for invalid URLs
            throw new IllegalArgumentException("Invalid URL: " + url, ex);
        }

        try {
            HttpResponse<byte[]> response = httpClient.send(request, 
                                                                            HttpResponse.BodyHandlers.ofByteArray());
            int statusCode = response.statusCode();
            if (statusCode < 200 || statusCode >= 300) {
                
                throw new IllegalStateException("Failed to download resource from URL: " + url + 
                                                                            " (HTTP status: " + statusCode + ")");
            }
            return response.body();
        } 
        catch (IOException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Error while downloading resource from URL: " + url, ex);
        }
    }
    
}