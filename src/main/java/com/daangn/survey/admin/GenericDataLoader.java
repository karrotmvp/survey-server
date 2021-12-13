package com.daangn.survey.admin;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class GenericDataLoader<T> {
    public GenericDataLoader(T type, Function<T, List<T>> function){
        BatchLoader<T, T> memberBatchLoader =
                surveys1 -> CompletableFuture.supplyAsync(() -> function.apply(type));

        DataLoader<T, T> memberDataLoader = DataLoaderFactory.newDataLoader(memberBatchLoader);
    }
}
