package com.lz.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    Logger log = LoggerFactory.getLogger(RestController.class);

    private final Services services;

    public RegisterController(Services services) {
        this.services = services;
    }

    @PostMapping("register")
    public void register(@RequestBody Application application) {
        long now = System.currentTimeMillis();
        String service = application.getService();
        String serviceId = application.getServiceId(service);

        if (Services.SERVICES.add(serviceId)) {
            application.setRegisterDate(now);
            Services.APPLICATIONS.put(serviceId, application);
            log.info("Join -> {}", service);
        } else {
            long expireDate = now + (1000 * 10);
            Services.APPLICATIONS.get(serviceId).setExpireDate(expireDate);
            log.info("Renewal -> {}", service);
        }
    }

    @GetMapping("get/applications")
    public Map<?, ?> get() {
        return Services.APPLICATIONS;
    }

    @PostMapping("offline")
    public String offline(String serviceId) {
        if (serviceId == null) {
            return "serviceId is empty";
        }
        return services.offline(serviceId) ? "Success." : "serviceId does not exist.";
    }

}
