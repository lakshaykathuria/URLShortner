package com.shortner.URLShortner.repository;


import com.shortner.URLShortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    boolean existsByShorturl(String shorturl);

    Optional<Url> findByShorturl(String shorturl);
}
