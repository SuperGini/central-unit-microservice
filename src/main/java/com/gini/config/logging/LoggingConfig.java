package com.gini.config.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;

import static java.util.Collections.singleton;
import static org.zalando.logbook.BodyFilter.merge;
import static org.zalando.logbook.BodyFilters.defaultValue;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonStringProperty;


@Configuration
public class LoggingConfig {

    @Bean
    public BodyFilter bodyFilter() {
        return merge(
                defaultValue(),
                replaceJsonStringProperty(singleton("partNumber"), "XXX"));
    }

}
