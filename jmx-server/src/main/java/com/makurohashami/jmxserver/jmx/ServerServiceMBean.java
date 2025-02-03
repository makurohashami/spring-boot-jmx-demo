package com.makurohashami.jmxserver.jmx;

import com.makurohashami.jmxserver.service.ServerService;
import java.util.Map;
import javax.management.Notification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(description = "Server settings MBean")
@RequiredArgsConstructor
public class ServerServiceMBean implements NotificationPublisherAware {

    private final ServerService serverService;
    private NotificationPublisher publisher;

    @Override
    public void setNotificationPublisher(@NonNull NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }

    @ManagedAttribute(description = "Get server url")
    public String getUrl() {
        return serverService.getUrl();
    }

    @ManagedAttribute(description = "Set server url")
    public void setUrl(String serverName) {
        serverService.setUrl(serverName);
    }

    @ManagedAttribute(description = "Get connection timeout")
    public long getConnectionTimeout() {
        return serverService.getConnectionTimeout();
    }

    @ManagedAttribute(description = "Set connection timeout")
    public void setConnectionTimeout(long connectionTimeout) {
        if (connectionTimeout < 0) {
            throw new IllegalArgumentException("connectionTimeout cannot be negative");
        }
        if (connectionTimeout > 15_000) {
            publisher.sendNotification(new Notification("setConnectionTimeout", this, 0, "Connection timeout is too long: " + connectionTimeout));
        }
        serverService.setConnectionTimeout(connectionTimeout);
    }

    @ManagedAttribute(description = "Is use SSL")
    public boolean isUseSsl() {
        return serverService.isUseSSL();
    }

    @ManagedAttribute(description = "Set use SSL")
    public void setUseSsl(boolean useSsl) {
        serverService.setUseSSL(useSsl);
    }

    @ManagedAttribute(description = "Get server config")
    public Map<String, String> getConfig() {
        return serverService.getConfig();
    }

    @ManagedOperation(description = "Get sever config value by key")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key")
    })
    public String getConfigValue(String key) {
        return serverService.getConfigValue(key);
    }

    @ManagedOperation(description = "Put new config property")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key"),
            @ManagedOperationParameter(name = "value", description = "Config value")
    })
    public String putConfigValue(String key, String value) {
        return serverService.putConfigValue(key, value);
    }

    @ManagedOperation(description = "Remove value from server config")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key")
    })
    public String removeConfigValue(String key) {
        return serverService.removeConfigValue(key);
    }

    @ManagedOperation(description = "Reset connection timeout to 5000")
    public void resetConnectionTimeout() {
        serverService.resetConnectionTimeout();
    }

}
