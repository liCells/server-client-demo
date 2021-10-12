package com.lz.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Heartbeat {

    Logger log = LoggerFactory.getLogger(Heartbeat.class);

    @Value("${service.server.url}")
    private String url;

    @Value("${service.server.delay:30000}")
    private Integer delay;

    private final Environment environment;

    public Heartbeat(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public void sendHeartbeat() {
        final Application application = getApplication();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HttpResponse<?> response = HttpUtil.doPost(url, application);
                if (response == null || response.statusCode() != 200) {
                    log.warn("Register -> {}", response);
                }
            }
        }, delay);
    }

    private Application getApplication() {
        String ip;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            ip = "127.0.0.1";
        }
        // 处理对应的应用名及端口
        final String name = environment.getProperty("spring.application.name");
        final String port = environment.getProperty("server.port");
        final String type = environment.getProperty("service.client.type");
        return new Application(ip,
                port == null ? "8080" : port,
                name == null ? "UNDEFINED" : name,
                type
        );
    }

}
