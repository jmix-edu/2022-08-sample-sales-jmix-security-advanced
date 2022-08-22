package com.company.samplesales;

import com.google.common.base.Strings;
import io.jmix.core.security.AuthorizedUrlsProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class SampleSalesJmixApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SampleSalesJmixApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @EventListener
    public void printApplicationUrl(ApplicationStartedEvent event) {
        LoggerFactory.getLogger(SampleSalesJmixApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    @Bean
    public AuthorizedUrlsProvider myAuthorizedUrlsProvider() {
        return new AuthorizedUrlsProvider() {
            @Override
            public Collection<String> getAuthenticatedUrlPatterns() {
                return Arrays.asList("/authenticated/**");
            }

            @Override
            public Collection<String> getAnonymousUrlPatterns() {
                return Arrays.asList("/public/**");
            }
        };
    }
}
