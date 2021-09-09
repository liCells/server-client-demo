package com.lz.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Heartbeat {

    @Value("${service.server.url}")
    private String url;

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
                long start = System.currentTimeMillis();
                HttpUtil.doPost(url, application);
                long end = System.currentTimeMillis();
                System.out.println(end - start);
            }
        }, 3000, 3000);
    }

    private Application getApplication() {
        String ip;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            ip = "127.0.0.1";
        }
        final String name = environment.getProperty("spring.application.name");
        final String port = environment.getProperty("server.port");
        return new Application(ip,
                port == null ? "8080" : port,
                name == null ? "UNDEFINED" : name);
    }

}
