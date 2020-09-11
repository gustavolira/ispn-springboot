package com.example.demo;

import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.persistence.jdbc.configuration.JdbcStringBasedStoreConfigurationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public String hello() {

        EmbeddedCacheManager cacheManager = new DefaultCacheManager();
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.memory().maxCount(1);
        configurationBuilder.persistence().addStore(JdbcStringBasedStoreConfigurationBuilder.class)
                .table()
                .tableNamePrefix("cache")
                .idColumnType("VARCHAR(255)").idColumnName("id")
                .dataColumnType("BYTEA").dataColumnName("data")
                .timestampColumnName("ts").timestampColumnType("BIGINT")
                .connectionPool()
                .connectionUrl("jdbc:postgresql://localhost:5432/example")
                .driverClass("org.postgresql.Driver")
                .username("gustavo")
                .password("123456")
                .segmented(false);

        Cache<String, String> cache = cacheManager
                .administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("example", configurationBuilder.build());

        cache.put("key1", "value1");
        cache.put("key2", "value1");
        cache.put("key3", "value1");
        cache.put("key4", "value1");
        cache.put("key5", "value1");
        cache.put("key6", "value1");

        System.out.println(cache.get("key1"));

        System.out.println(cache.size());

        return "Hello World!";
    }
}
