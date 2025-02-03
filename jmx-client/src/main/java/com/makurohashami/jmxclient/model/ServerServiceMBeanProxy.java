package com.makurohashami.jmxclient.model;

import java.util.Map;

public interface ServerServiceMBeanProxy {

    String getUrl();

    void setUrl(String serverName);

    long getConnectionTimeout();

    void setConnectionTimeout(long connectionTimeout);

    boolean isUseSsl();

    void setUseSsl(boolean useSsl);

    Map<String, String> getConfig();

    String getConfigValue(String key);

    String putConfigValue(String key, String value);

    String removeConfigValue(String key);

    void resetConnectionTimeout();

}
