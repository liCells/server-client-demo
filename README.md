## 简单的服务注册与发现

在应用启动后, 向server发送心跳, 默认为30秒, 如果在几个心跳的周期中没有收到某节点的心跳, 就将其从注册表中删除.

```
配置provider/.../application.yml文件即可模拟多个client启动场景

访问server的rest接口(/get/applications)可获取到当前注册上的client
```

