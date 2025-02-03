package com.makurohashami.jmxserver.service;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@ToString
public class ServerService {

    private static final long DEFAULT_CONNECTION_TIMEOUT = 5000;

    private String url;
    private long connectionTimeout;
    private boolean useSSL;
    private final Map<String, String> config;

    public ServerService() {
        this.url = "http://localhost:8080";
        this.connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        this.useSSL = false;
        this.config = new HashMap<>();
        this.config.put("username", "admin");
        this.config.put("password", "admin");
    }

    public void setUrl(String url) {
        log.info("Call setUrl() with value: '{}'", url);
        this.url = url;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        log.info("Call setConnectionTimeout() with value: '{}'", connectionTimeout);
        this.connectionTimeout = connectionTimeout;
    }

    public void setUseSSL(boolean useSSL) {
        log.info("Call setUseSSL() with value: '{}'", useSSL);
        this.useSSL = useSSL;
    }

    public String getConfigValue(String key) {
        return this.config.get(key);
    }

    public String putConfigValue(String key, String value) {
        log.info("Call putConfigValue() with key: '{}', value: '{}'", key, value);
        return this.config.put(key, value);
    }

    public String removeConfigValue(String key) {
        log.info("Call removeConfigValue() by key: '{}'", key);
        return this.config.remove(key);
    }

    public void resetConnectionTimeout() {
        log.info("Call resetConnectionTimeout()");
        this.connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    }

}
