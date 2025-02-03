package com.makurohashami.jmxserver;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import java.util.concurrent.CountDownLatch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;

@EnableMBeanExport
@SpringBootApplication
public class JmxServerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(JmxServerApplication.class, args);
        new CountDownLatch(1).await();
    }

    @Bean
    @ConditionalOnBean(JmxConfig.class)
    public JmxMeterRegistry jmxMeterRegistry(JmxConfig config) {
        return new JmxMeterRegistry(config, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT);
    }

}
