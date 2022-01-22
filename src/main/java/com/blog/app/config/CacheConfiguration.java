package com.blog.app.config;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CacheConfiguration {
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheManagerFactoryBean(){
        EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
        cacheCacheManager.setCacheManager(ehCacheManagerFactory().getObject());
        cacheCacheManager.setTransactionAware(true);
        return cacheCacheManager;
    }
}
