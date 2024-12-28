# .gitlab-ci.yml语法说明

## 1. 简介

GitLab CI/CD 是 GitLab 提供的持续集成、持续交付和持续部署功能。通过 .gitlab-ci.yml 文件，可以定义项目的 CI/CD 流程。

## 2. 文件位置

.gitlab-ci.yml 文件应该放在项目的根目录下。
* 文件使用 YAML 语法编写，需要特别注意缩进格式，通常使用空格进行缩进，而不是制表符（tabs）。

## 3. 语法说明

### 3.1 关键字

- `image`: 指定运行 CI/CD 任务的基础镜像。
- `services`: 指定运行 CI/CD 任务的服务镜像。
- `stages`: 指定 CI/CD 任务的阶段。
- `before_script`: 指定在所有任务之前运行的脚本。
- `after_script`: 指定在所有任务之后运行的脚本。
- `script`: 指定 CI/CD 任务的具体脚本。
- `only`: 指定 CI/CD 任务只在特定条件下运行。
- `except`: 指定 CI/CD 任务在特定条件下不运行。
- `tags`: 指定 CI/CD 任务运行的标签。
- `variables`: 指定 CI/CD 任务的环境变量。
- `cache`: 指定 CI/CD 任务缓存的文件或目录。
- `artifacts`: 指定 CI/CD 任务生成的文件或目录。
- `job`: 定义了流水线中的一个作业。
- `environment`: 用于配置部署阶段的环境信息。
- `when`: 用于控制在什么情况下执行作业。

### 3.2 示例

```yaml
# 定义Docker镜像为最新版本的Node.js
image: node:latest

# 定义构建、测试和部署三个阶段
stages:
  - build
  - test
  - deploy

# 在脚本执行前，先安装npm依赖
before_script:
  - npm install

# 构建阶段
build:
  stage: build
  script:
    - npm run build

# 测试阶段
test:
  stage: test
  script:
    - npm run test

# 部署阶段
deploy:
  stage: deploy
  script:
    - npm run deploy
  # 只有在master分支上执行
  only:
    - master
  # 除了develop分支外，其他分支都不执行
  except:
    - develop
  # 标签为production
  tags:
    - production
  # 定义环境变量
  variables:
    DEPLOY_ENV: production
  # 缓存node_modules目录
  cache:
    paths:
      - node_modules/
  # 生成构建产物
  artifacts:
    paths:
      - dist//
```