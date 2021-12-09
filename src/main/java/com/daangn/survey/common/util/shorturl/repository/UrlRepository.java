package com.daangn.survey.common.util.shorturl.repository;

import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findFirstByShortUrlOrOriginUrlOrderByCreatedAt(String shortUrl, String originUrl);
    boolean existsByShortUrlOrOriginUrl(String shortUrl, String originUrl);
    ShortUrl findShortUrlByShortUrl(String shortUrl);
}
