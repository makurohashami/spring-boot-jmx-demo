package com.makurohashami.jmxclient.client;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmxClient {

    @Value("${jmx.remote.host}")
    private String host;

    @Value("${jmx.remote.port}")
    private int port;

    private JMXConnector jmxConnector;
    private MBeanServerConnection connection;

    @PostConstruct
    public void init() {
        connect();
    }

    private synchronized void connect() {
        try {
            if (jmxConnector == null) {
                JMXServiceURL serviceUrl = new JMXServiceURL(String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi", host, port));

                jmxConnector = JMXConnectorFactory.connect(serviceUrl, null);
                connection = jmxConnector.getMBeanServerConnection();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not connect to JMX Server", e);
        }
    }

    @PreDestroy
    public synchronized void close() {
        if (jmxConnector != null) {
            try {
                jmxConnector.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public synchronized Object getAttribute(String mBeanName, String attributeName) {
        try {
            connect();
            return connection.getAttribute(new ObjectName(mBeanName), attributeName);
        } catch (Exception e) {
            log.error("Cannot get attribute: '{}' for mBeanName: '{}'. Returning null...", attributeName, mBeanName);
            return null;
        }
    }

    public synchronized void setAttribute(String mBeanName, String attributeName, Object attributeValue) {
        try {
            connect();
            connection.setAttribute(new ObjectName(mBeanName), new Attribute(attributeName, attributeValue));
        } catch (Exception e) {
            log.error("Cannot set value '{}' for attribute: '{}'. MBeanName: '{}'. Returning null...", attributeValue, attributeName, mBeanName);
        }
    }

    public synchronized Object invokeOperation(String mBeanName, String operationName, Object[] params, String[] signature) {
        try {
            connect();
            return connection.invoke(new ObjectName(mBeanName), operationName, params, signature);
        } catch (Exception e) {
            log.error("Cannot invoke operation: '{}' for mBeanName: '{}', params: '{}' and signature: '{}'. Returning null...", operationName, mBeanName, params, signature);
            return null;
        }
    }

    public synchronized MBeanInfo getMBeanInfo(String mBeanName) {
        try {
            connect();
            return connection.getMBeanInfo(new ObjectName(mBeanName));
        } catch (Exception e) {
            log.error("Cannot get MBeanInfo for mBeanName: '{}'. Returning null...", mBeanName);
            return null;
        }
    }

    public synchronized <T> T getMBeanProxy(String mBeanName, Class<T> mBeanInterface) {
        try {
            connect();
            return JMX.newMBeanProxy(connection, new ObjectName(mBeanName), mBeanInterface);
        } catch (Exception e) {
            log.error("Cannot create proxy for mBeanName: '{}'. Returning null...", mBeanName);
            return null;
        }
    }

}
