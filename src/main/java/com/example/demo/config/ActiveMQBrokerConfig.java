package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.JournalType;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQBrokerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQBrokerConfig.class);

    private EmbeddedActiveMQ broker;

    @PostConstruct
    public void startBroker() throws Exception {
        ConfigurationImpl config = new ConfigurationImpl();
        config.addAcceptorConfiguration("default", "vm://0");
        config.setPersistenceEnabled(false);
        config.setSecurityEnabled(false);
        config.setJournalType(JournalType.NIO);

        broker = new EmbeddedActiveMQ();
        broker.setConfiguration(config);
        broker.start();
        LOG.info("Embedded ActiveMQ broker started.");
    }

    @PreDestroy
    public void stopBroker() throws Exception {
        if (broker != null) {
            broker.stop();
            LOG.info("Embedded ActiveMQ broker stopped.");
        }
    }
}
