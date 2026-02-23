# 修改root密码

```bash
# 进入容器
docker-compose exec gitlab bash

# 启动控制台
gitlab-rails console -e production

# 在控制台中
user = User.find_by_username('root')
user.password = 'MyNewStrongPassword123!'
user.password_confirmation = 'MyNewStrongPassword123!'
user.save!
exit

# 退出容器
exit
```