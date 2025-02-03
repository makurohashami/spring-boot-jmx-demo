package com.makurohashami.jmxclient.service;

import com.makurohashami.jmxclient.model.Server;
import java.util.Map;
import javax.management.MBeanInfo;

public interface ServerServiceJmxClient {

    MBeanInfo getMBeanInfo();

    Server getServer();

    String getUrl();

    void setUrl(String url);

    long getConnectionTimeout();

    void setConnectionTimeout(long connectionTimeout);

    boolean isUseSSL();

    void setUseSSL(boolean useSSL);

    Map<String, String> getConfig();

    String getConfigValue(String key);

    String putConfigValue(String key, String value);

    String removeConfigValue(String key);

    void resetConnectionTimeout();

}
