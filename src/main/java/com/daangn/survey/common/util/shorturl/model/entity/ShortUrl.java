package com.daangn.survey.common.util.shorturl.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "origin_url")
    private String originUrl;

    @Builder.Default
    @Column(name = "req_count")
    private long reqCount = 1;

    public void addReqCount(){
        reqCount += 1;
    }

    public void setShortUrl(String url){
        this.shortUrl = url;
    }
}
