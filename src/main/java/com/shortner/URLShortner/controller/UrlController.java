package com.shortner.URLShortner.controller;

import com.shortner.URLShortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping()
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseDomain;

    /**
     * POST /api/shorten
     * Accepts a long URL and returns a full short URL.
     */
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("longUrl");
        if (longUrl == null || longUrl.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "longUrl is required"));
        }
        String shorturl = urlService.shortenUrl(longUrl);
        String fullShortUrl = baseDomain + "/" + shorturl;
        return ResponseEntity.ok(Map.of("shortUrl", fullShortUrl));
    }

    /**
     * GET /{shortUrl}
     * Redirects to the original long URL if found, otherwise returns 404.
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToLongUrl(@PathVariable String shortUrl) {
        Optional<String> longUrlOpt = urlService.getLongUrl(shortUrl);
        if (longUrlOpt.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", longUrlOpt.get());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Short URL not found"));
        }
    }
}
