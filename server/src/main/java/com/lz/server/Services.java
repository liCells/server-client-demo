package com.lz.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Services {

    Logger log = LoggerFactory.getLogger(Services.class);

    public static final Map<String, Application> APPLICATIONS = new ConcurrentHashMap<>();

    public static final Set<String> SERVICES = new HashSet<>();

    public void offline() {
        Collection<Application> values = Services.APPLICATIONS.values();
        values.forEach(item -> {
            if (System.currentTimeMillis() > item.getExpireDate()) {
                Services.APPLICATIONS.remove(item.getServiceId(), item);
                log.info("Remove -> {}", item.getService());
            }
        });
    }

    public boolean offline(String serviceId) {
        Application removed = Services.APPLICATIONS.remove(serviceId);
        if (removed == null) {
            return false;
        }
        log.info("Offline -> {}", removed.getService());
        return true;
    }

    @Bean
    private void remove() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                offline();
            }
        }, 10000, 10000);
    }
}
