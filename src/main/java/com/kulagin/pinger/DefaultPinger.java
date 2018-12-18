package com.kulagin.pinger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
@Slf4j
public class DefaultPinger {
    @Value("${app.ping.url}")
    private String[] urls;
    @Value("${app.ping.times}")
    private int times;
    @Value("${app.ping.wait-interval-ms}")
    private long waitIntervalMs;
    private Map<String, String> headers;

    private final RestTemplate restTemplate;

    public void ping() throws InterruptedException{
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        for (String url : urls) {
            log.info("Ping {}, times {}", url, times);
            for (int i = 0; i < times; i++) {
                // don't care about result
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                Thread.sleep(waitIntervalMs);
            }
        }
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
