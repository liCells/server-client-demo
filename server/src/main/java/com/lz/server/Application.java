package com.lz.server;

public class Application {
    private String ip;
    private String port;
    private String name;
    private String type;
    private long expireDate;
    private long registerDate;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(long registerDate) {
        this.registerDate = registerDate;
    }

    public String getService() {
        return this.ip + ':' + this.port + '/' + this.name;
    }

    public String getServiceId() {
        return String.valueOf(getService().hashCode());
    }

    public String getServiceId(String service) {
        return String.valueOf(service.hashCode());
    }
}
