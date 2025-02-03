package com.makurohashami.jmxclient.service;

import com.makurohashami.jmxclient.client.JmxClient;
import com.makurohashami.jmxclient.model.Server;
import java.util.Map;
import javax.management.MBeanInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "serverServiceMBean.client", havingValue = "manual")
public class ServerServiceManualJmxClient implements ServerServiceJmxClient {

    @Value("${serverServiceMBean.name}")
    private String mBeanName;

    private final JmxClient jmxClient;

    @Override
    public MBeanInfo getMBeanInfo() {
        return jmxClient.getMBeanInfo(mBeanName);
    }

    @Override
    public Server getServer() {
        return Server.builder()
                .url((String) jmxClient.getAttribute(mBeanName, "Url"))
                .connectionTimeout((long) jmxClient.getAttribute(mBeanName, "ConnectionTimeout"))
                .useSSL((boolean) jmxClient.getAttribute(mBeanName, "UseSsl"))
                .config((Map<String, String>) jmxClient.getAttribute(mBeanName, "Config"))
                .build();
    }

    @Override
    public String getUrl() {
        return (String) jmxClient.getAttribute(mBeanName, "Url");
    }

    @Override
    public void setUrl(String url) {
        jmxClient.setAttribute(mBeanName, "Url", url);
    }

    @Override
    public long getConnectionTimeout() {
        return (long) jmxClient.getAttribute(mBeanName, "ConnectionTimeout");
    }

    @Override
    public void setConnectionTimeout(long connectionTimeout) {
        jmxClient.setAttribute(mBeanName, "ConnectionTimeout", connectionTimeout);
    }

    @Override
    public boolean isUseSSL() {
        return (boolean) jmxClient.getAttribute(mBeanName, "UseSsl");
    }

    @Override
    public void setUseSSL(boolean useSSL) {
        jmxClient.setAttribute(mBeanName, "UseSsl", useSSL);
    }

    @Override
    public Map<String, String> getConfig() {
        return (Map<String, String>) jmxClient.getAttribute(mBeanName, "Config");
    }

    @Override
    public String getConfigValue(String key) {
        return (String) jmxClient.invokeOperation(mBeanName, "getConfigValue", new Object[]{key}, new String[]{"java.lang.String"});
    }

    @Override
    public String putConfigValue(String key, String value) {
        return (String) jmxClient.invokeOperation(mBeanName, "putConfigValue", new Object[]{key, value}, new String[]{"java.lang.String", "java.lang.String"});
    }

    @Override
    public String removeConfigValue(String key) {
        return (String) jmxClient.invokeOperation(mBeanName, "removeConfigValue", new Object[]{key}, new String[]{"java.lang.String"});
    }

    @Override
    public void resetConnectionTimeout() {
        jmxClient.invokeOperation(mBeanName, "resetConnectionTimeout", new Object[]{}, new String[]{});
    }

}
