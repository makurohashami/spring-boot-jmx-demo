package com.makurohashami.jmxclient.service;

import com.makurohashami.jmxclient.client.JmxClient;
import com.makurohashami.jmxclient.model.Server;
import com.makurohashami.jmxclient.model.ServerServiceMBeanProxy;
import java.util.Map;
import javax.management.MBeanInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "serverServiceMBean.client", havingValue = "proxy")
public class ServerServiceProxyJmxClient implements ServerServiceJmxClient {

    @Value("${serverServiceMBean.name}")
    private String mBeanName;

    private final static Class<ServerServiceMBeanProxy> PROXY_CLASS = ServerServiceMBeanProxy.class;

    private final JmxClient jmxClient;

    @Override
    public MBeanInfo getMBeanInfo() {
        return jmxClient.getMBeanInfo(mBeanName);
    }

    @Override
    public Server getServer() {
        ServerServiceMBeanProxy proxy = getProxy();
        return Server.builder()
                .url(proxy.getUrl())
                .connectionTimeout(proxy.getConnectionTimeout())
                .useSSL(proxy.isUseSsl())
                .config(proxy.getConfig())
                .build();
    }

    @Override
    public String getUrl() {
        return getProxy().getUrl();
    }

    @Override
    public void setUrl(String url) {
        getProxy().setUrl(url);
    }

    @Override
    public long getConnectionTimeout() {
        return getProxy().getConnectionTimeout();
    }

    @Override
    public void setConnectionTimeout(long connectionTimeout) {
        getProxy().setConnectionTimeout(connectionTimeout);
    }

    @Override
    public boolean isUseSSL() {
        return getProxy().isUseSsl();
    }

    @Override
    public void setUseSSL(boolean useSSL) {
        getProxy().setUseSsl(useSSL);
    }

    @Override
    public Map<String, String> getConfig() {
        return getProxy().getConfig();
    }

    @Override
    public String getConfigValue(String key) {
        return getProxy().getConfigValue(key);
    }

    @Override
    public String putConfigValue(String key, String value) {
        return getProxy().putConfigValue(key, value);
    }

    @Override
    public String removeConfigValue(String key) {
        return getProxy().removeConfigValue(key);
    }

    @Override
    public void resetConnectionTimeout() {
        getProxy().resetConnectionTimeout();
    }

    private ServerServiceMBeanProxy getProxy() {
        return jmxClient.getMBeanProxy(mBeanName, PROXY_CLASS);
    }

}
