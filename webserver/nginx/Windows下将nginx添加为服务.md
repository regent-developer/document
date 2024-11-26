# Windows下将nginx添加为服务

```bash
sc create Nginx binPath= "C:\nginx\nginx.exe -g \"daemon off;\"" DisplayName= "Nginx" start= auto
```

* Nginx 是服务的名称，C:\nginx\nginx.exe -g “daemon off;” 是 Nginx 可执行文件的完整路径和启动参数，Nginx 是服务的显示名称，start= auto 表示在系统启动时自动启动服务。