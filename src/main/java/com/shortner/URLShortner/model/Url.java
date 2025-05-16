package com.shortner.URLShortner.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls", indexes = {
        @Index(name = "idx_shortcode", columnList = "shorturl")
})
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(length = 10, unique = true)
    private String shorturl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Long accessCount = 0L;

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shorturl) {
        this.shorturl = shorturl;
    }

    public void incrementAccessCount() {
        this.accessCount++;
    }
}
