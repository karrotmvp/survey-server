package com.daangn.survey.batchLoader;

import com.daangn.survey.mongo.MongoRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoaderFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@SpringBootTest
public class DataLoaderTest {

    @Autowired
    private MongoRepository mongoRepository;

    @Test
    void 데이터로더_테스트(){

    }
}
