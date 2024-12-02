# docker-gitlab

## dockerfile
```
version: '3.7'

services:
  gitlab:
    image: 'gitlab/gitlab-ce:latest'
    container_name: gitlab
    restart: always
    hostname: 'gitlab'
    environment:
      GITLAB_OMNIBUS_CONFIG: |
        external_url 'http://自己的IP或者域名'
    ports:
      - '9080:80'
      - '9443:443'
      - '9022:22'
    volumes:
      - './gitlab/config:/etc/gitlab'
      - './gitlab/data:/var/opt/gitlab'
      - './gitlab/logs:/var/log/gitlab'
    networks:
      - gitlab-net

networks:
  gitlab-net:
    driver: bridge

```

## 启动
```
docker-compose up --build -d
```

## 默认账号密码

默认账号: root
默认密码:

* 进入容器 docker exec -it gitlab /bin/bash
* grep ‘Password:’ /etc/gitlab/initial_root_password
