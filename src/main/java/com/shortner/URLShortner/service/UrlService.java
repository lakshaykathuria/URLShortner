package com.shortner.URLShortner.service;

import com.shortner.URLShortner.model.Url;
import com.shortner.URLShortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String longUrl) {
        Url url = new Url();
        url.setLongUrl(longUrl);

        url = urlRepository.save(url);

        String shortUrl = encodeBase62(url.getId());
        url.setShortUrl(shortUrl);

        urlRepository.save(url);

        return shortUrl;
    }


    public Optional<String> getLongUrl(String shortUrl) {
        return urlRepository.findByShorturl(shortUrl)
                .map(url -> {
                    urlRepository.save(url);
                    url.incrementAccessCount();
                    return url.getLongUrl();
                });
    }


    private String encodeBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
