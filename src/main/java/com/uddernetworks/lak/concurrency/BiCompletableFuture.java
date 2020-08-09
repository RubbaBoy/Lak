package com.uddernetworks.lak.concurrency;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BiCompletableFuture<K, V> extends CompletableFuture<Map.Entry<K, V>> {

    public void complete(K key, V value) {
        super.complete(new AbstractMap.SimpleEntry<>(key, value));
    }

    public CompletableFuture<Void> thenAccept(BiConsumer<K, V> consumer) {
        return super.thenAccept(entry -> consumer.accept(entry.getKey(), entry.getValue()));
    }
}
