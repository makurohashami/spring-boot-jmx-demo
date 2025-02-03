package com.makurohashami.jmxclient.controller;

import com.makurohashami.jmxclient.model.Server;
import com.makurohashami.jmxclient.service.ServerServiceJmxClient;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@RequestMapping(value = "/server", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServerController {

    private final ServerServiceJmxClient serverServiceJmxClient;

    @GetMapping("/mBeanInfo")
    public MBeanInfo getMBeanInfo() {
        return serverServiceJmxClient.getMBeanInfo();
    }

    @GetMapping
    public Server getServer() {
        return serverServiceJmxClient.getServer();
    }

    @GetMapping("/url")
    public Map<String, Object> getUrl() {
        return wrapToMap("url", serverServiceJmxClient.getUrl());
    }

    @PostMapping("/url")
    public void setUrl(@RequestParam String url) {
        serverServiceJmxClient.setUrl(url);
    }

    @GetMapping("/connectionTimeout")
    public Map<String, Object> getConnectionTimeout() {
        return wrapToMap("timeout", serverServiceJmxClient.getConnectionTimeout());
    }

    @PostMapping("/connectionTimeout")
    public void setConnectionTimeout(@RequestParam long timeout) {
        serverServiceJmxClient.setConnectionTimeout(timeout);
    }

    @GetMapping("/useSSL")
    public Map<String, Object> isUseSsl() {
        return wrapToMap("useSSL", serverServiceJmxClient.isUseSSL());
    }

    @PostMapping("/useSSL")
    public void setUseSSL(boolean useSSL) {
        serverServiceJmxClient.setUseSSL(useSSL);
    }

    @GetMapping("/config")
    public Map<String, String> getConfig() {
        return serverServiceJmxClient.getConfig();
    }

    @GetMapping("/config/{key}")
    public Map<String, Object> getConfigValue(@PathVariable String key) {
        return wrapToMap(key, serverServiceJmxClient.getConfigValue(key));
    }

    @PostMapping("/config/{key}/{value}")
    public Map<String, Object> putConfigValue(@PathVariable String key, @PathVariable String value) {
        return wrapToMap("oldValue", serverServiceJmxClient.putConfigValue(key, value));
    }

    @DeleteMapping("/config/{key}")
    public Map<String, Object> removeConfigValue(@PathVariable String key) {
        return wrapToMap("oldValue", serverServiceJmxClient.removeConfigValue(key));
    }

    @PostMapping("/connectionTimeout/reset")
    public void resetConnectionTimeout() {
        serverServiceJmxClient.resetConnectionTimeout();
    }

    private Map<String, Object> wrapToMap(String key, Object value) {
        Map<String, Object> result = new HashMap<>();
        result.put(key, value);
        return result;
    }

}
