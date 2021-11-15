package com.daangn.survey.common.util.shorturl.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "short_url")
public class ShortUrl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "short_url_id")
    private Long id;

    @Column(name = "short_url", length = 1000)
    private String shortUrl;

    @Column(name = "origin_url", length = 1000)
    private String originUrl;

    @Column(name = "scheme_url", length = 1000)
    private String schemeUrl;

    @Builder.Default
    @Column(name = "req_count")
    private long reqCount = 1;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public void addReqCount(){
        reqCount += 1;
    }

    public void setShortUrl(String url){
        this.shortUrl = url;
    }
}
