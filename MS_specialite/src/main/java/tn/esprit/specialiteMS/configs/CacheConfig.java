package tn.esprit.specialiteMS.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager() {
        // Caffeine cache manager will use the properties defined in application.properties
        return new CaffeineCacheManager();
    }
} 