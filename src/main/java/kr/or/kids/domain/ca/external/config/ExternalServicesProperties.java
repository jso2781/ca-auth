package kr.or.kids.domain.ca.external.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external")
public class ExternalServicesProperties {
    private Map<String, Service> services;

    public Map<String, Service> getServices() { return services; }

    public void setServices(Map<String, Service> services) { this.services = services; }

    public static class Service {
        private String baseUrl;
        private int connectTimeoutMs;
        private int readTimeoutMs;

        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

        public int getConnectTimeoutMs() { return connectTimeoutMs; }
        public void setConnectTimeoutMs(int connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }

        public int getReadTimeoutMs() { return readTimeoutMs; }
        public void setReadTimeoutMs(int readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
    }
}
