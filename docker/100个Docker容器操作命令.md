# 100个Docker容器操作命令

1. 运行容器：docker run -d --name web nginx:latest 后台启动Nginx容器
2. 启动已停止容器：docker start web
3. 停止运行中容器：docker stop web（发送SIGTERM信号）
4. 强制停止容器：docker kill web（发送SIGKILL信号）
5. 重启容器：docker restart web
6. 暂停容器进程：docker pause web（冻结进程树）
7. 恢复暂停容器：docker unpause web
8. 删除停止的容器：docker rm web
9. 强制删除运行中容器：docker rm -f web（生产环境慎用）
10. 批量删除停止容器：docker container prune
11. 创建容器不启动：docker create --name temp alpine sleep 3600
12. 容器重命名：docker rename old_name new_name
13. 更新运行参数：docker update --memory 512m web 动态调整内存限制
14. 设置重启策略：docker run --restart=always nginx 崩溃后自动重启
15. 容器自愈检测：docker run --health-cmd="curl -f http://localhost || exit 1" --health-interval=30s nginx
16. 查看健康状态：docker inspect --format='{{.State.Health.Status}}' web
17. 资源限制（CPU）：docker run --cpus=1.5 app 限制使用1.5核CPU
18. 资源限制（内存）：docker run -m 2g --memory-swap=3g app 内存+Swap限制
19. 进入交互终端：docker exec -it web /bin/bash
20. 后台执行命令：docker exec -d web touch /tmp/test.log
21. 查看实时日志：docker logs -f --tail 100 web（追踪最后100行）
22. 查看进程列表：docker top web
23. 容器详情查看：docker inspect web | jq .（配合jq解析JSON）
24. 端口映射检查：docker port web
25. 文件复制到容器：docker cp app.conf web:/etc/nginx/conf.d/
26. 从容器复制文件：docker cp web:/var/log/nginx/error.log ./
27. 查看资源统计：docker stats --no-stream（显示当前状态）
28. 实时监控资源：docker stats web mysql（监控特定容器）
29. 容器差异检查：docker diff web（查看文件系统变更）
30. 容器提交为镜像：docker commit -c 'CMD ["nginx"]' web nginx:v2（慎用，推荐Dockerfile）
31. 容器导出快照：docker export web > web.tar
32. 挂载调试工具：docker run --cap-add SYS_PTRACE --security-opt seccomp=unconfined debug_tool
33. 使用nsenter调试：nsenter --target $(docker inspect -f {{.State.Pid}} web) --mount --uts --ipc --net --pid
34. 拉取镜像：docker pull alpine:3.18（指定版本避免latest风险）35. 查看本地镜像：docker images --filter "dangling=false"（过滤悬空镜像）36. 删除镜像：docker rmi alpine:3.1737. 强制删除镜像：docker rmi -f redis（被容器使用时需加-f）38. 清理悬空镜像：docker image prune39. 导出镜像存档：docker save -o nginx.tar nginx:latest40. 导入镜像存档：docker load -i nginx.tar41. 查看镜像历史：docker history nginx42. 镜像标签管理：docker tag nginx:latest myreg.com/nginx:v143. 推送镜像到仓库：docker push myreg.com/nginx:v144. 构建镜像：docker build -t myapp:v1 --build-arg ENV=prod .45. 多阶段构建：Dockerfile中：COPY --from=builder /app/bin /usr/bin
46. 创建匿名卷：docker run -v /data mysql（自动创建）47. 绑定主机目录：docker run -v /host/path:/container/path nginx48. 创建命名卷：docker volume create app_data49. 使用命名卷：docker run -v app_data:/var/lib/mysql mysql50. 查看卷详情：docker volume inspect app_data51. 清理未使用卷：docker volume prune52. 查看挂载点：docker inspect -f '{{ .Mounts }}' web53. 临时文件系统：docker run --tmpfs /tmp:size=100m app54. 只读挂载：docker run -v /conf:/etc/nginx:ro nginx（生产安全必备）55. 共享存储卷：docker run --volumes-from db_storage backup_tool
56. 查看网络列表：docker network ls57. 创建自定义网络：docker network create --driver bridge my_net58. 指定容器网络：docker run --network=my_net web59. 容器别名访问：docker run --network=my_net --name web --network-alias website nginx60. 查看网络详情：docker network inspect my_net61. 连接运行中容器：docker network connect my_net existing_container62. 断开容器网络：docker network disconnect my_net container63. 端口随机映射：docker run -P nginx（自动绑定随机主机端口）64. 指定端口映射：docker run -p 8080:80 -p 443:443 nginx65. 主机模式网络：docker run --network=host nginx（共享主机网络栈）66. 容器间直连：docker run --link redis:db app（传统方式，推荐使用自定义网络）67. DNS配置覆盖：docker run --dns 8.8.8.8 --dns-search example.com alpine
68. 日志驱动设置：docker run --log-driver=json-file --log-opt max-size=10m nginx69. 日志标签添加：docker run --log-opt tag="{{.Name}}/{{.ID}}" nginx70. 环境变量注入：docker run -e TZ=Asia/Shanghai -e APP_ENV=prod app71. 时区同步配置：docker run -v /etc/localtime:/etc/localtime:ro app72. 容器自启策略：docker run --restart=on-failure:5 app（失败时最多重启5次）73. 内核参数调整：docker run --sysctl net.core.somaxconn=1024 app74. 容器资源限制：docker run --cpus=".5" -m 500m --blkio-weight=500 app75. OOM优先级设置：docker run --oom-score-adj=500 app（值越高越易被kill）76. 容器用户隔离：docker run --user 1000:1000 app（避免root运行）77. 只读文件系统：docker run --read-only app（需配合tmpfs使用）78. 能力控制：docker run --cap-add NET_ADMIN --cap-drop SYS_ADMIN app79. 安全加固：docker run --security-opt no-new-privileges app（禁止提权）80. AppArmor配置：docker run --security-opt apparmor=my_profile app81. SELinux标签：docker run --security-opt label=type:container_t app82. 设备访问授权：docker run --device=/dev/snd:/dev/snd:rw audio_app
83. 初始化Swarm：docker swarm init --advertise-addr 192.168.1.10084. 加入Swarm集群：docker swarm join --token SWMTKN... 192.168.1.100:237785. 部署Stack服务：docker stack deploy -c docker-compose.yml myapp86. 查看服务列表：docker service ls87. 服务伸缩操作：docker service scale web=588. 滚动更新配置：docker service update --image nginx:1.23 web89. 回滚服务版本：docker service rollback web90. 查看服务日志：docker service logs -f web91. 节点状态检查：docker node ls92. 服务约束部署：docker service create --constraint 'node.role==worker' nginx
93. 查看Docker版本：docker version94. 系统全局信息：docker info95. 磁盘空间清理：docker system prune -af（慎用！清理所有未用资源）96. 事件实时监控：docker events --filter 'event=die'（捕获容器退出事件）97. 构建缓存清理：docker builder prune98. 检查容器配置：docker config ls（Swarm模式）99. 容器漏洞扫描：docker scan nginx:latest（需登录Docker Hub）100. 资源使用报告：docker system df -v（详细磁盘占用分析）

https://mp.weixin.qq.com/s/MndEWuek02DWTNqnQH87XA